package com.superpeer.tutuyoudian.activity.store;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
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
import com.google.gson.Gson;
import com.nanchen.compresshelper.CompressHelper;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.paytype.PayTypeActivity;
import com.superpeer.tutuyoudian.activity.position.PositionActivity;
import com.superpeer.tutuyoudian.activity.storesendset.StoreSendSetActivity;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.bean.BaseSearchResult;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.ImageDownLoadCallBack;
import com.superpeer.tutuyoudian.utils.DateUtils;
import com.superpeer.tutuyoudian.utils.DownLoadImgUtils;
import com.tencent.map.geolocation.TencentPoi;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import rx.functions.Action1;

public class StoreApplyActivity extends BaseActivity<StoreApplyPresenter, StoreApplyModel> implements StoreApplyContract.View {

    private int maxNum = 1;
    private ArrayList<String> mSelectPath = new ArrayList<>();
    //启动Activity的请求码
    private static final int REQUEST_IMAGE = 101;
    private ImageView ivPhoto;

    private TextView tvStoreType;
    private View popupWindowView;
    private PopupWindow popWindow;
    private LinearLayout linear;
    private TextView tvAddress;
    private List<BaseList> categoryList;
    private TextView tvCity;
    private EditText etStoreName;
    private List<BaseList> addressList;
    private String address = "";
    private EditText etRange;
    private EditText etUserName;
    private EditText etPhone;
    private TextView tvPayType;

    private String typeCode = "";
    private String province = "";
    private String city = "";
    private String district = "";
    private String longitude = "";
    private String latitude = "";
    private String code = "";
    private String type = "";
    private EditText etDetailAddress;

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        type = getIntent().getStringExtra("type");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_store_apply;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("店铺基础信息");

        setToolBarViewStubText("保存").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });

        linear = (LinearLayout) findViewById(R.id.linear);

        tvStoreType = (TextView) findViewById(R.id.tvStoreType);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvCity = (TextView) findViewById(R.id.tvCity);

        etDetailAddress = (EditText) findViewById(R.id.etDetailAddress);
        etStoreName = (EditText) findViewById(R.id.etStoreName);
        etRange = (EditText) findViewById(R.id.etRange);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        tvPayType = (TextView) findViewById(R.id.tvPayType);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);

        initListener();

        initRxBus();

        BaseObject bean = getUserInfo();

        if(null!=bean){
            initData(bean);
        }

    }

    /**
     *  初始化数据
     * @param bean
     */
    private void initData(BaseObject bean) {
        if(null!=bean.getName()){
            etStoreName.setText(bean.getName());
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
            if(null!=bean.getImagePath())
            Glide.with(mContext).load(Url.IP+bean.getImagePath()).centerCrop().into(ivPhoto);
        }
        if(null!=bean.getTypeName()){
            tvStoreType.setText(bean.getTypeName());
        }
        if(null!=bean.getBusinessScope()){
            etRange.setText(bean.getBusinessScope());
        }
        if(null!=bean.getAddress()){
            tvAddress.setText(bean.getAddress());
        }
        if(null!=bean.getDetailedAddress()){
            etDetailAddress.setText(bean.getDetailedAddress());
        }
        if(null!=bean.getPhone()){
            etPhone.setText(bean.getPhone());
        }
        if(null!=bean.getBossName()){
            etUserName.setText(bean.getBossName());
        }
        if(null!=bean.getProvinces()){
            province = bean.getProvinces();
        }
        if(null!=bean.getCity()){
            city = bean.getCity();
        }
        if(null!=bean.getArea()){
            district = bean.getArea();
        }
        if(null!=bean.getLongitude()){
            longitude = bean.getLongitude();
        }
        if(null!=bean.getLatitude()){
            latitude = bean.getLatitude();
        }
        if(null!=bean.getType()){
            typeCode = bean.getType();
        }
        if(null!=bean.getAreaCode()){
            code = bean.getAreaCode();
        }
    }

    private void initRxBus() {
        /*mRxManager.on("selectPos", new Action1<TencentPoi>() {
            @Override
            public void call(TencentPoi pos) {
                longitude = pos.getLongitude()+"";
                latitude = pos.getLatitude()+"";
                tvAddress.setText(pos.getAddress());
            }
        });*/
        mRxManager.on("position", new Action1<BaseSearchResult.SearchData>() {
            @Override
            public void call(BaseSearchResult.SearchData searchData) {
                try {
                    longitude = searchData.getLocation().getLng();
                    latitude = searchData.getLocation().getLat();
                    tvAddress.setText(searchData.getTitle());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        mRxManager.on("posCode", new Action1<String>() {
            @Override
            public void call(String s) {
                code = s;
            }
        });
    }

    /**
     * 保存店铺基础信息
     */
    private void saveInfo() {
        String storeName = etStoreName.getText().toString().trim();
        String category = tvStoreType.getText().toString().trim();
        String range = etRange.getText().toString().trim();
        String username = etUserName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = tvAddress.getText().toString().trim();
        String detail = etDetailAddress.getText().toString().trim();
        if(TextUtils.isEmpty(storeName)){
            showShortToast("请输入店铺名称");
            return;
        }
        if(null == mSelectPath||!(mSelectPath.size()>0)){
            showShortToast("请选择图片");
            return;
        }
        if(TextUtils.isEmpty(category)){
            showShortToast("请选择分类");
            return;
        }
        if(TextUtils.isEmpty(range)){
            showShortToast("请输入范围");
            return;
        }
        if(TextUtils.isEmpty(address)){
            showShortToast("请选择城市");
            return;
        }
        if(TextUtils.isEmpty(username)){
            showShortToast("请选择联系人姓名");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            showShortToast("请输入联系电话");
            return;
        }
        if(TextUtils.isEmpty(address)){
            showShortToast("务必准确填写您的地址");
            return;
        }
        if(TextUtils.isEmpty(detail)){
            showShortToast("请输入店铺详细地址");
            return;
        }

        mPresenter.saveInfo(PreferencesUtils.getString(mContext, Constants.SHOP_ID), PreferencesUtils.getString(mContext, Constants.ACCOUNT_ID), storeName, mSelectPath.get(0), typeCode,
                category, range, code, longitude, latitude, address, username, phone, detail);
    }

    private void initListener() {
        //账户
        tvPayType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PayTypeActivity.class);
            }
        });

        tvStoreType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCategory("SHOP_TYPE");
            }
        });

        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PositionActivity.class);
            }
        });

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=addressList&&addressList.size()>0){
                    showAddPopupWindow(addressList);
                }else{
                    showShortToast("获取地址列表失败");
                }
            }
        });

        //选择照片
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
        popWindow.showAtLocation(linear, Gravity.BOTTOM, 0, 0);

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
                typeCode = categoryList.get(wheelView.getCurrentPosition()).getCode();
                tvStoreType.setText(categoryList.get(wheelView.getCurrentPosition()).getName());
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
    public void showArea(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())) {
                    if (null != baseBeanResult.getData()) {
                        if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                            addressList = baseBeanResult.getData().getList();
                        }else{
                            showShortToast("获取地址失败");
                        }
                    }else{
                        showShortToast("获取地址失败");
                    }
                }else{
                    showShortToast("获取地址失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showSaveResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    //上传成功
                    PreferencesUtils.putString(mContext, Constants.USER_INFO, new Gson().toJson(baseBeanResult.getData().getObject()));
                    if("1".equals(type)){
                        Intent intent = new Intent(mContext, StoreSendSetActivity.class);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }else{
                        finish();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 弹出PopupWindow
     * @param list
     */
    private void showAddPopupWindow(List<BaseList> list) {
        if (null != popWindow && popWindow.isShowing()) {
            popWindow.dismiss();
        } else {
            initAddPopupWindow(list);
            backgroundAlpha(0.8f);
        }
    }

    /**
     * 地址选择
     * @param list
     */
    private void initAddPopupWindow(final List<BaseList> list) {
        try{
            popupWindowView = getLayoutInflater().inflate(R.layout.pop_address, null, false);

            popWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

            popWindow.setFocusable(true);
            popupWindowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            popWindow.showAtLocation(tvCity, Gravity.BOTTOM, 0, 0);
            //省
            final WheelView wheelviewProvince = popupWindowView.findViewById(R.id.wheelviewProvince);
            wheelviewProvince.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
            wheelviewProvince.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelviewProvince.setWheelSize(5); // 设置滚轮个数
            wheelviewProvince.setWheelData(createMainDatas(list));  // 数据集合
            //市
            final WheelView wheelviewCity = popupWindowView.findViewById(R.id.wheelviewCity);
            wheelviewCity.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
            wheelviewCity.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelviewCity.setWheelSize(5); // 设置滚轮个数
            wheelviewCity.setWheelData(createSubDatas(list).get(createMainDatas(list).get(wheelviewProvince.getSelection())));  // 数据集合
            wheelviewProvince.join(wheelviewCity);
            wheelviewProvince.joinDatas(createSubDatas(list));

            //区
            final WheelView wheelviewArea = popupWindowView.findViewById(R.id.wheelviewArea);
            wheelviewArea.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
            wheelviewArea.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelviewArea.setWheelSize(5); // 设置滚轮个数
            wheelviewArea.setWheelData(createChildDatas(list).get(createSubDatas(list).get(createMainDatas(list).get(wheelviewProvince
                    .getSelection())).get(wheelviewCity.getSelection())));  // 数据集合
            wheelviewCity.join(wheelviewArea);
            wheelviewCity.joinDatas(createChildDatas(list));

            //选择
            TextView tvSure = popupWindowView.findViewById(R.id.tvSure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    province = createMainDatas(list).get(wheelviewProvince.getCurrentPosition());
                    city = createSubDatas(list).get(province).get(wheelviewCity.getCurrentPosition());
                    district = createChildDatas(list).get(city).get(wheelviewArea.getCurrentPosition());
                    address = province + "/" + city + "/" + district;
                    tvCity.setText(address);
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 省数据
     *
     * @return
     * @param list
     */
    private List<String> createMainDatas(List<BaseList> list) {
        List<String> province = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            province.add(list.get(i).getName());
        }
        return province;
    }

    /**
     * 市数据
     *
     * @return
     * @param list
     */
    private HashMap<String, List<String>> createSubDatas(List<BaseList> list) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (int i = 0; i < list.size(); i++) {
            List<String> city = new ArrayList<String>();
            for (int j = 0; j < list.get(i).getCities().size(); j++) {
                city.add(list.get(i).getCities().get(j).getName());
            }
            map.put(list.get(i).getName(), city);
        }
        return map;
    }

    /**
     * 县数据
     *
     * @return
     * @param list
     */
    private HashMap<String, List<String>> createChildDatas(List<BaseList> list) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getCities().size(); j++) {
                List<String> district = new ArrayList<String>();
                for (int k = 0; k < list.get(i).getCities().get(j).getAreas().size(); k++) {
                    district.add(list.get(i).getCities().get(j).getAreas().get(k).getName());
                }
                map.put(list.get(i).getCities().get(j).getName(), district);
            }
        }
        return map;
    }

}
