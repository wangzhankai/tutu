package com.superpeer.tutuyoudian.activity.addshop;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.superpeer.tutuyoudian.activity.shopmanager.ShopManagerActivity;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.utils.DateUtils;
import com.superpeer.tutuyoudian.utils.DownLoadImgUtils;
import com.superpeer.tutuyoudian.utils.TvUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class AddShopActivity extends BaseActivity<AddShopPresenter, AddShopModel> implements AddShopContract.View {

    private static final int REQUEST_CODE_SCAN = 888;
    private int maxNum = 1;
    private ArrayList<String> mSelectPath = new ArrayList<>();
    //启动Activity的请求码
    private static final int REQUEST_IMAGE = 101;
    private ImageView ivPhoto;
    private EditText etShopName;
    private TextView tvCategory;
    private TextView etShopType;
    private TextView etRule;
//    private TextView etUnit;
    private EditText etPrice;
    private EditText etRest;
    private EditText etCoupon;
    private LinearLayout linearCategory;
    private View popupWindowView;
    private PopupWindow popWindow;
    private List<BaseList> categoryList;
    private TextView tvPublish;
    private String categoryId = "";
    private String type = "1";
    private TextView tvUp;
    private TextView tvDown;
    private LinearLayout linearDown;
    private LinearLayout linearUp;
    private ImageView ivUp;
    private ImageView ivDown;
    private BaseObject bean;

    //扫码上传
    private String bankId = "";
    private String manufacturer = "";
    private String barCode = "";
    private BaseList shopManager;
    private String goodsId = "";
    private TextView tvPreview;
    private ImageView ivQrCode;
    private EditText etCode;
    private String flag = "";
    private String publicType = "";
    private TextView tvSave;
    private String categoryName = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        flag = getIntent().getStringExtra("type");
        bean = (BaseObject) getIntent().getSerializableExtra("bean");
        barCode = getIntent().getStringExtra("barCode");
        if(null==barCode){
            barCode = "";
        }
        shopManager = (BaseList) getIntent().getSerializableExtra("shopManager");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_shop;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        if("0".equals(flag)){
            setHeadTitle("本地上传");
        }else{
            setHeadTitle("编辑商品");
        }

        linearCategory = (LinearLayout) findViewById(R.id.linearCategory);

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        ivQrCode = (ImageView) findViewById(R.id.ivQrCode);

        tvPreview = (TextView) findViewById(R.id.tvPreview);
        etCode = (EditText) findViewById(R.id.etCode);
        etShopName = (EditText) findViewById(R.id.etShopName);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        etShopType = (TextView) findViewById(R.id.etShopType);
        etRule = (EditText) findViewById(R.id.etRule);
//        etUnit = (EditText) findViewById(R.id.etUnit);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etRest = (EditText) findViewById(R.id.etRest);
        etCoupon = (EditText) findViewById(R.id.etCoupon);
        tvPublish = (TextView) findViewById(R.id.tvPublish);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvUp = (TextView) findViewById(R.id.tvUp);
        tvDown = (TextView) findViewById(R.id.tvDown);
        linearDown = (LinearLayout) findViewById(R.id.linearDown);
        linearUp = (LinearLayout) findViewById(R.id.linearUp);
        ivUp = (ImageView) findViewById(R.id.ivUp);
        ivDown = (ImageView) findViewById(R.id.ivDown);

        TvUtils.setTwoDecimal(etPrice);

        if(null!=bean){
            initData();
        }
        if(null!=shopManager){
            initShopManagerData();
        }
        if(!TextUtils.isEmpty(barCode)){
            etCode.setText(barCode);
        }

        initListener();
    }

    private void initShopManagerData() {
        if(null!=shopManager.getGoodsId()){
            goodsId = shopManager.getGoodsId();
        }
        if(null!=shopManager.getBankId()){
            bankId = shopManager.getBankId();
            /*etShopType.setFocusable(false);
            etShopType.setFocusableInTouchMode(false);
            etShopName.setFocusable(false);
            etShopName.setFocusableInTouchMode(false);*/
            etCode.setFocusable(false);
            /*etCode.setFocusableInTouchMode(false);
            etRule.setFocusable(false);
            etRule.setFocusableInTouchMode(false);*/
        }
        if(null!=shopManager.getManufacturer()){
            manufacturer = shopManager.getManufacturer();
        }
        if(null!=shopManager.getType()){
            categoryId = shopManager.getType();
        }
        if(null!=shopManager.getName()){
            etShopName.setText(shopManager.getName());
            tvPreview.setText(shopManager.getName());
        }
        if(null!=shopManager.getImagePath()){
            try {
                String fileName = DateUtils.getUUIDFileName()+".png";
                String filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/tutu/"+fileName;
                DownLoadImgUtils.savePicture(mContext, fileName, Url.IP+shopManager.getImagePath());
                mSelectPath.add(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Glide.with(mContext).load(Url.IP+shopManager.getImagePath()).centerCrop().into(ivPhoto);
        }
        if(null!=shopManager.getTypeName()){
            categoryName = shopManager.getTypeName();
            tvCategory.setText(shopManager.getTypeName());
        }
        if(null!=shopManager.getBrand()){
            etShopType.setText(shopManager.getBrand());
        }
        if(null!=shopManager.getSpecifications()){
            etRule.setText(shopManager.getSpecifications());
            tvPreview.setText(tvPreview.getText().toString().trim()+"-"+shopManager.getSpecifications());
        }
        /*if(null!=shopManager.getUnit()){
            etUnit.setText(shopManager.getUnit());
        }*/
        if(null!=shopManager.getPrice()){
            etPrice.setText(shopManager.getPrice());
        }
        if(null!=shopManager.getStock()){
            etRest.setText(shopManager.getStock());
        }
        if(null!=shopManager.getVipPrice()){
            etCoupon.setText(shopManager.getVipPrice());
        }
    }

    private void initData() {
        if(null!=bean.getGoodsId()){
            goodsId = bean.getGoodsId();
        }
        if(null!=bean.getBankId()){
            bankId = bean.getBankId();
            /*etShopType.setFocusable(false);
            etShopType.setFocusableInTouchMode(false);
            etShopName.setFocusable(false);
            etShopName.setFocusableInTouchMode(false);*/
            etCode.setFocusable(false);
            etCode.setFocusableInTouchMode(false);
            /*etRule.setFocusable(false);
            etRule.setFocusableInTouchMode(false);*/
        }
        if(null!=bean.getManufacturer()){
            manufacturer = bean.getManufacturer();
        }
        if(null!=bean.getType()){
            categoryId = bean.getType();
        }
        if(null!=bean.getName()){
            etShopName.setText(bean.getName());
            tvPreview.setText(bean.getName());
        }
        if(null!=bean.getImagePath()){
            try {
                String fileName = DateUtils.getUUIDFileName()+".png";
                String filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/tutu/"+fileName;
                DownLoadImgUtils.savePicture(mContext, fileName, Url.IP+bean.getImagePath());
                mSelectPath.add(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Glide.with(mContext).load(Url.IP+bean.getImagePath()).centerCrop().into(ivPhoto);
        }
        if(null!=bean.getTypeName()){
            categoryName = bean.getTypeName();
            tvCategory.setText(bean.getTypeName());
        }
        if(null!=bean.getBrand()){
            etShopType.setText(bean.getBrand());
        }
        if(null!=bean.getSpecifications()){
            etRule.setText(bean.getSpecifications());
            tvPreview.setText(tvPreview.getText().toString().trim()+"-"+bean.getName());
        }
        /*if(null!=bean.getUnit()){
            etUnit.setText(bean.getUnit());
        }*/
        if(null!=bean.getPrice()){
            etPrice.setText(bean.getPrice());
        }
        if(null!=bean.getStock()){
            etRest.setText(bean.getStock());
        }
        if(null!=bean.getPreferentialPrice()){
            etCoupon.setText(bean.getPreferentialPrice());
        }

    }

    private void initListener() {
        //扫码
        ivQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQrCode();
            }
        });

        etShopName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String preview = etRule.getText().toString().trim();
                if(!TextUtils.isEmpty(preview)){
                    tvPreview.setText(s.toString()+"-"+preview);
                }else{
                    tvPreview.setText(s.toString());
                }
            }
        });
        //规格
        etRule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String preview = etShopName.getText().toString().trim();
                if(!TextUtils.isEmpty(preview)){
                    tvPreview.setText(preview+"-"+s.toString());
                }else{
                    tvPreview.setText(s.toString());
                }
            }
        });

        linearUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "1";
                ivUp.setImageResource(R.mipmap.iv_select);
                ivDown.setImageResource(R.mipmap.iv_noselect);
            }
        });

        linearDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "0";
                ivUp.setImageResource(R.mipmap.iv_noselect);
                ivDown.setImageResource(R.mipmap.iv_select);
            }
        });

        linearCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(bankId)){
                    return;
                }
                /*if(null!=shopManager&&!TextUtils.isEmpty(bankId)){
                    showShortToast("商品分类不可修改");
                    return;
                }*/
                mPresenter.getShopCategory(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
            }
        });
        //上传图片
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(!TextUtils.isEmpty(bankId)){
                    return;
                }*/
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
        //发布并保存
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicType = "1";
                String title = etShopName.getText().toString().trim();
                String brand = etShopType.getText().toString().trim();
                String specifications = etRule.getText().toString().trim();
                String code = etCode.getText().toString().trim();
                String price = etPrice.getText().toString().trim();
                String stock = etRest.getText().toString().trim();
                String vipPrice = etCoupon.getText().toString().trim();

                if(TextUtils.isEmpty(title)){
                    showShortToast("请输入商品标题");
                    return;
                }
                if(TextUtils.isEmpty(categoryId)){
                    showShortToast("请选择商品分类");
                    return;
                }
                /*if(TextUtils.isEmpty(brand)){
                    showShortToast("请输入商品品牌");
                    return;
                }*/
                /*if(TextUtils.isEmpty(specifications)){
                    showShortToast("请输入商品规格");
                    return;
                }*/
                if(TextUtils.isEmpty(price)){
                    showShortToast("请输入价格");
                    return;
                }
                /*if(TextUtils.isEmpty(stock)){
                    showShortToast("请输入库存");
                    return;
                }*/
                if(null == mSelectPath||!(mSelectPath.size()>0)){
                    showShortToast("请选择图片");
                    return;
                }
                mPresenter.upload(PreferencesUtils.getString(mContext, Constants.SHOP_ID), goodsId, title, bankId, manufacturer, code,
                        mSelectPath.get(0), categoryId, brand, specifications, price, stock, vipPrice, type);
            }
        });

        //发布
        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicType = "0";
                String title = etShopName.getText().toString().trim();
                String brand = etShopType.getText().toString().trim();
                String specifications = etRule.getText().toString().trim();
                String code = etCode.getText().toString().trim();
                String price = etPrice.getText().toString().trim();
                String stock = etRest.getText().toString().trim();
                String vipPrice = etCoupon.getText().toString().trim();

                if(TextUtils.isEmpty(title)){
                    showShortToast("请输入商品标题");
                    return;
                }
                if(TextUtils.isEmpty(categoryId)){
                    showShortToast("请选择商品分类");
                    return;
                }
                /*if(TextUtils.isEmpty(brand)){
                    showShortToast("请输入商品品牌");
                    return;
                }*/
                /*if(TextUtils.isEmpty(specifications)){
                    showShortToast("请输入商品规格");
                    return;
                }*/
                if(TextUtils.isEmpty(price)){
                    showShortToast("请输入价格");
                    return;
                }
                /*if(TextUtils.isEmpty(stock)){
                    showShortToast("请输入库存");
                    return;
                }*/
                if(null == mSelectPath||!(mSelectPath.size()>0)){
                    showShortToast("请选择图片");
                    return;
                }
                mPresenter.upload(PreferencesUtils.getString(mContext, Constants.SHOP_ID), goodsId, title, bankId, manufacturer, code,
                        mSelectPath.get(0), categoryId, brand, specifications, price, stock, vipPrice, type);
            }
        });
    }

    private void initQrCode() {
        try {
            /*QrConfig qrConfig = new QrConfig.Builder()
                    .setDesText("(识别二维码或条形码)")//扫描框下文字
                    .setShowDes(false)//是否显示扫描框下面文字
                    .setShowLight(true)//显示手电筒按钮
                    .setShowTitle(true)//显示Title
                    .setShowAlbum(true)//显示从相册选择按钮
                    .setCornerColor(Color.WHITE)//设置扫描框颜色
                    .setLineColor(Color.WHITE)//设置扫描线颜色
                    .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                    .setScanType(QrConfig.TYPE_ALL)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                    .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                    .setCustombarcodeformat(QrConfig.BARCODE_I25)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                    .setPlaySound(true)//是否扫描成功后bi~的声音
//                .setDingPath(R.raw.test)//设置提示音(不设置为默认的Ding~)
                    .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                    .setTitleText("扫描二维码或条形码")//设置Tilte文字
                    .setTitleBackgroudColor(ContextCompat.getColor(mContext, R.color.colorPrimary))//设置状态栏颜色
                    .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                    .create();
            QrManager.getInstance().init(qrConfig).startScan(AddShopActivity.this, new QrManager.OnScanResultCallback() {
                @Override
                public void onScanSuccess(String result) {
                    etCode.setText(result);
                }
            });*/
            MPermissionUtils.requestPermissionsResult((Activity) mContext, 1, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new MPermissionUtils.OnPermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Intent intent = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                }

                @Override
                public void onPermissionDenied() {
                    MPermissionUtils.showTipsDialog(mContext);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
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
                categoryName = categoryList.get(wheelView.getCurrentPosition()).getGoodsName();
                categoryId = categoryList.get(wheelView.getCurrentPosition()).getGoodsTypeId();
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
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                etCode.setText(content);
            }
        }else if(resultCode == RESULT_OK){
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

    @Override
    public void showUploadResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData()&&null!=baseBeanResult.getData().getObject()){
                        mRxManager.post("library", baseBeanResult.getData().getObject());
                    }
                    if("0".equals(flag)) {
                        if ("0".equals(publicType)) {
                            Intent intent = new Intent(mContext, ShopManagerActivity.class);
                            intent.putExtra("type", categoryId);
                            intent.putExtra("typeName", categoryName);
                            startActivity(intent);
                            finish();
                        }else{
                            clear();
                        }
                    }else{
                        if ("0".equals(publicType)) {
                            Intent intent = new Intent(mContext, ShopManagerActivity.class);
                            intent.putExtra("type", categoryId);
                            intent.putExtra("typeName", categoryName);
                            startActivity(intent);
                            finish();
                        }else{
                            clear();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clear(){
        bankId = "";
        etCoupon.setText("");
        etCoupon.setFocusable(true);
        etCoupon.setFocusableInTouchMode(true);
        etRest.setText("");
        etRest.setFocusable(true);
        etRest.setFocusableInTouchMode(true);
        etPrice.setText("");
        etPrice.setFocusable(true);
        etPrice.setFocusableInTouchMode(true);
        etRule.setText("");
        etRule.setFocusable(true);
        etRule.setFocusableInTouchMode(true);
        etShopType.setText("");
        etShopType.setFocusable(true);
        etShopType.setFocusableInTouchMode(true);
        tvCategory.setText("");
        tvCategory.setFocusable(true);
        tvCategory.setFocusableInTouchMode(true);
        etCode.setText("");
        etCode.setFocusable(true);
        etCode.setFocusableInTouchMode(true);
        etShopName.setText("");
        etShopName.setFocusable(true);
        etShopName.setFocusableInTouchMode(true);
    }

}
