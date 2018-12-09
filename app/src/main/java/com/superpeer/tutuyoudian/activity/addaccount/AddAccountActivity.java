package com.superpeer.tutuyoudian.activity.addaccount;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddAccountActivity extends BaseActivity<AddAccountPresenter, AddAccountModel> implements AddAccountContract.View {

    private EditText etName;
//    private ImageView ivBindWeixin;
    private EditText etCard;
    private EditText etUserName;
    private TextView tvBank;
    private TextView tvType;
    private TextView tvCity;
    private TextView tvCityBank;
    private View popupWindowView;
    private PopupWindow popWindow;
    private RelativeLayout linear;
    private String bankId = "";
    private List<BaseList> bankList;
    private List<BaseList> addressList;
    private String province;
    private String city;
//    private String district;
    private String address = "";
    private String provinceId = "";
    private String cityId = "";
    private String subBankId = "";
    private TextView tvQuit;
    private TextView tvSure;
    private String typeId = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_account;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("结算方式");

        etName = (EditText) findViewById(R.id.etName);
        etCard = (EditText) findViewById(R.id.etCard);
        etUserName = (EditText) findViewById(R.id.etUserName);
        tvBank = (TextView) findViewById(R.id.tvBank);
        tvType = (TextView) findViewById(R.id.tvType);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvCityBank = (TextView) findViewById(R.id.tvCityBank);
        tvQuit = (TextView) findViewById(R.id.tvQuit);
        tvSure = (TextView) findViewById(R.id.tvSure);
        linear = (RelativeLayout) findViewById(R.id.linear);

        initListener();

        mPresenter.getBanks();
    }

    private void initListener() {
        tvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String name = etName.getText().toString().trim();
                String userName = etUserName.getText().toString().trim();
                String card = etCard.getText().toString().trim();
                String bankName = tvBank.getText().toString().trim();
                String subBankName = tvCityBank.getText().toString().trim();

                /*if(TextUtils.isEmpty(name)){
                    showShortToast("请输入营业执照名称");
                    return;
                }*/
                if(TextUtils.isEmpty(userName)){
                    showShortToast("请输入开户名");
                    return;
                }
                if(TextUtils.isEmpty(card)){
                    showShortToast("请输入银行卡号");
                    return;
                }
                if(TextUtils.isEmpty(bankId)){
                    showShortToast("请选择银行");
                    return;
                }
                if(TextUtils.isEmpty(typeId)){
                    showShortToast("请选择账户类型");
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    showShortToast("请选择开户行所在城市");
                    return;
                }
                if(TextUtils.isEmpty(subBankId)){
                    showShortToast("请选择分支行");
                    return;
                }
                if("0".equals(PreferencesUtils.getString(mContext, Constants.USER_TYPE))){      //商家
                    mPresenter.saveAccount("0", PreferencesUtils.getString(mContext, Constants.SHOP_ID), "", "", card,
                            userName, bankId, bankName, typeId, provinceId, province, cityId, city, subBankId, subBankName);
                }else {
                    mPresenter.saveAccountRunner(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "1", "", "", card,
                            userName, bankId, bankName, typeId, provinceId, province, cityId, city, subBankId, subBankName);
                }
            }
        });
        //选择银行
        tvBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=bankList){
                    showBankWindow(bankList, "0");
                }else{
                    showShortToast("获取银行列表失败");
                }
            }
        });
        //选择账户类型
        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getDict("ACCOUNT_TYPE");
            }
        });
        //选择银行所在地
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
        //选择分支行
        tvCityBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(bankId)){
                    showShortToast("请先选择银行");
                    return;
                }
                mPresenter.getSubBank(bankId, provinceId, cityId);
            }
        });
    }

    /**
     * 弹出PopupWindow
     *
     * @param list
     */
    private void showBankWindow(List<BaseList> list, String type) {
        if (null != popWindow && popWindow.isShowing()) {
            popWindow.dismiss();
        } else {
            initPopupWindow(list, type);
            backgroundAlpha(0.8f);
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void initPopupWindow(final List<BaseList> bankList, final String type) {
        popupWindowView = getLayoutInflater().inflate(R.layout.pop_category, null, false);
        popWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setFocusable(true);
        popupWindowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popWindow.showAtLocation(linear, Gravity.BOTTOM, 0, 0);

        List<String> list = new ArrayList<>();
        for(int i=0; i<bankList.size(); i++){
            if ("0".equals(type)) {
                if(null!=bankList.get(i).getName()){
                    list.add(bankList.get(i).getName());
                }
            }else if("1".equals(type)){
                if(null!=bankList.get(i).getSubBranchName()){
                    list.add(bankList.get(i).getSubBranchName());
                }
            }else{
                if(null!=bankList.get(i).getName()){
                    list.add(bankList.get(i).getName());
                }
            }
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
                if("0".equals(type)){
                    bankId = bankList.get(wheelView.getCurrentPosition()).getBankId();
                    tvBank.setText(bankList.get(wheelView.getCurrentPosition()).getName());
                }else if("1".equals(type)){
                    subBankId = bankList.get(wheelView.getCurrentPosition()).getSubBranchId();
                    tvCityBank.setText(bankList.get(wheelView.getCurrentPosition()).getSubBranchName());
                }else{
                    typeId = bankList.get(wheelView.getCurrentPosition()).getCode();
                    tvType.setText(bankList.get(wheelView.getCurrentPosition()).getName());
                }
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
    public void showBank(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                        bankList = baseBeanResult.getData().getList();
                        mPresenter.getAreas();
                    }else{
                        showShortToast("获取银行列表失败");
                    }
                }else{
                    showShortToast("获取银行列表失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showAreas(BaseBeanResult baseBeanResult) {
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
    public void showDictResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    showBankWindow(baseBeanResult.getData().getList(), "2");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showSubBank(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    showBankWindow(baseBeanResult.getData().getList(), "1");
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
                    mRxManager.post("savePayType", "");
                    finish();
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

            /*//区
            final WheelView wheelviewArea = popupWindowView.findViewById(R.id.wheelviewArea);
            wheelviewArea.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
            wheelviewArea.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelviewArea.setWheelSize(5); // 设置滚轮个数
            wheelviewArea.setWheelData(createChildDatas(list).get(createSubDatas(list).get(createMainDatas(list).get(wheelviewProvince
                    .getSelection())).get(wheelviewCity.getSelection())));  // 数据集合
            wheelviewCity.join(wheelviewArea);
            wheelviewCity.joinDatas(createChildDatas(list));*/

            //选择
            TextView tvSure = popupWindowView.findViewById(R.id.tvSure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    province = createMainDatas(list).get(wheelviewProvince.getCurrentPosition());
                    provinceId = list.get(wheelviewProvince.getCurrentPosition()).getCode();
                    city = createSubDatas(list).get(province).get(wheelviewCity.getCurrentPosition());
                    cityId = list.get(wheelviewProvince.getCurrentPosition()).getCities().get(wheelviewCity.getCurrentPosition()).getCode();
//                    cityId = list.get(wheelviewProvince.getCurrentPosition()).getCities().get(wheelviewArea.getCurrentPosition()).getCode();
//                    district = createChildDatas(list).get(city).get(wheelviewArea.getCurrentPosition());
//                    address = province + "/" + city + "/" + district;
                    address = province + "/" + city;
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
