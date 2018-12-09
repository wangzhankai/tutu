package com.superpeer.tutuyoudian.activity.shopedit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nanchen.compresshelper.CompressHelper;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.utils.TvUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class ShopEditActivity extends BaseActivity<ShopEditPresenter, ShopEditModel> implements ShopEditContract.View{

    private int maxNum = 1;
    private ArrayList<String> mSelectPath;
    //启动Activity的请求码
    private static final int REQUEST_IMAGE = 101;
    private ImageView ivPhoto;
    private EditText etShopName;
    private TextView tvCategory;
    private TextView tvShopType;
    private TextView etRule;
    private TextView etUnit;
    private EditText etPrice;
    private EditText etRest;
    private EditText etCoupon;
    private LinearLayout linearCategory;
    private View popupWindowView;
    private PopupWindow popWindow;
    private List<BaseList> categoryList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shop_edit;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("编辑商品");

        linearCategory = (LinearLayout) findViewById(R.id.linearCategory);

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);

        etShopName = (EditText) findViewById(R.id.etShopName);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvShopType = (TextView) findViewById(R.id.tvShopType);
        etRule = (EditText) findViewById(R.id.etRule);
        etUnit = (EditText) findViewById(R.id.etUnit);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etRest = (EditText) findViewById(R.id.etRest);
        etCoupon = (EditText) findViewById(R.id.etCoupon);

        TvUtils.setTwoDecimal(etPrice);

        initListener();

    }

    private void initListener() {
        linearCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getShopCategory(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
            }
        });
        //上传图片
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPermissionUtils.requestPermissionsResult((Activity) mContext, 1, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        //选择照片
                        selectPhoto();
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(mContext);
                    }
                });
            }
        });
    }

    /**
     * 弹出PopupWindow
     *
     * @param categoryList
     */
    private void showPopupWindow(List<BaseList> categoryList) {
        if (null != popWindow && popWindow.isShowing()) {
            popWindow.dismiss();
        } else {
            initPopupWindow(categoryList);
            backgroundAlpha(0.8f);
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void initPopupWindow(final List<BaseList> categoryList) {
        popupWindowView = getLayoutInflater().inflate(R.layout.pop_category, null, false);
        popWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setFocusable(true);
        popupWindowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popWindow.showAtLocation(linearCategory, Gravity.BOTTOM, 0, 0);

        List<String> list = new ArrayList<>();
        for(int i=0; i<categoryList.size(); i++){
            list.add(categoryList.get(i).getName());
        }

        final WheelView wheelView = popupWindowView.findViewById(R.id.wheelview);
        wheelView.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
        wheelView.setWheelSize(3); // 设置滚轮个数
        wheelView.setWheelData(list);  // 数据集合

        TextView tvCancel = (TextView) popupWindowView.findViewById(R.id.tvCancel);
        TextView tvSure = (TextView) popupWindowView.findViewById(R.id.tvSure);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCategory.setText(categoryList.get(wheelView.getCurrentPosition()).getName());
                popWindow.dismiss();
            }
        });

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (popWindow != null && popWindow.isShowing()) {
                    popWindow.dismiss();
                    popWindow = null;
                }
                return false;
            }
        });
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 选择图片
     */
    private void selectPhoto() {
        Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
        //是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        //最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        //选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        //默认选择
        if(mSelectPath!=null&&mSelectPath.size()>0){
            mSelectPath.clear();
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_IMAGE){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                File newFile = CompressHelper.getDefault(mContext).compressToFile(new File(mSelectPath.get(0)));
                mSelectPath.clear();
                mSelectPath.add(newFile.getPath());
                if(mSelectPath.size()>maxNum){
                    for(int i=maxNum;i<mSelectPath.size();i++){
                        mSelectPath.remove(i);
                    }
                }
                if (mSelectPath.size() > 0) {
                    Glide.with(mContext).load("file://"+mSelectPath.get(0)).centerCrop().into(ivPhoto);
                }
            }
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void showCategory(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData()){
                        if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                            categoryList = baseBeanResult.getData().getList();
                            showPopupWindow(categoryList);
                        }else{
                            showShortToast("暂无分类");
                        }
                    }else{
                        showShortToast("获取店铺分类失败");
                    }
                }else{
                    showShortToast("获取店铺分类失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
