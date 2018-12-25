package com.superpeer.tutuyoudian.activity.storesendset;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.info.StoreInfoActivity;
import com.superpeer.tutuyoudian.activity.selectDay.SelectDayActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.utils.TvUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class StoreSendSetActivity extends BaseActivity<StoreSendSetPresenter, StoreSendSetModel> implements StoreSendSetContract.View {

    private EditText etRange;
    private EditText etStartMoney;
    private EditText etPackageFee;
    private EditText etSendFee;
    private EditText etSendTime;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvStoreDay;
    private TextView tvSure;

    private int type;
    private View popupWindowView;
    private PopupWindow popWindow;
    private LinearLayout linear;
    private String days = "";
    private BaseObject bean;
    private String flag = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        flag = getIntent().getStringExtra("type");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_store_send_set;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("配送信息设置");

        linear = (LinearLayout) findViewById(R.id.linear);

        etRange = (EditText) findViewById(R.id.etRange);
        etStartMoney = (EditText) findViewById(R.id.etStartMoney);
        etPackageFee = (EditText) findViewById(R.id.etPackageFee);
        etSendFee = (EditText) findViewById(R.id.etSendFee);
        etSendTime = (EditText) findViewById(R.id.etSendTime);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvStoreDay = (TextView) findViewById(R.id.tvStoreDay);

        tvSure = (TextView) findViewById(R.id.tvSure);

        TvUtils.setTwoDecimal(etStartMoney);
        TvUtils.setTwoDecimal(etPackageFee);
        TvUtils.setTwoDecimal(etSendFee);

        initListener();

        bean = getUserInfo();
        if(null!=bean){
            initData(bean);
        }

        initRxBus();
    }

    /**
     * 初始化数据
     * @param bean
     */
    private void initData(BaseObject bean) {
        if(null!=bean.getDeliveryRange()){
            etRange.setText(bean.getDeliveryRange());
        }
        if(null!=bean.getMinMonery()){
            etStartMoney.setText(bean.getMinMonery());
        }
        if(null!=bean.getPackingFee()){
            etPackageFee.setText(bean.getPackingFee());
        }
        if(null!=bean.getDeliverFee()){
            etSendFee.setText(bean.getDeliverFee());
        }
        if(null!=bean.getDeliveryTime()){
            etSendTime.setText(bean.getDeliveryTime());
        }
        if(null!=bean.getOpeningTime()){
            tvStartTime.setText(bean.getOpeningTime());
        }
        if(null!=bean.getClosingTime()){
            tvEndTime.setText(bean.getClosingTime());
        }
        if(null!=bean.getShopDayOff()){
            days = bean.getShopDayOff();
            String shopdayOff = bean.getShopDayOff();
            if(bean.getShopDayOff().contains("1"))  shopdayOff =shopdayOff.replace("1", "周一");
            if(bean.getShopDayOff().contains("2"))  shopdayOff =shopdayOff.replace("2", "周二");
            if(bean.getShopDayOff().contains("3"))  shopdayOff =shopdayOff.replace("3", "周三");
            if(bean.getShopDayOff().contains("4"))  shopdayOff =shopdayOff.replace("4", "周四");
            if(bean.getShopDayOff().contains("5"))  shopdayOff =shopdayOff.replace("5", "周五");
            if(bean.getShopDayOff().contains("6"))  shopdayOff =shopdayOff.replace("6", "周六");
            if(bean.getShopDayOff().contains("7"))  shopdayOff =shopdayOff.replace("7", "周日");
            if(bean.getShopDayOff().contains("0"))  shopdayOff =shopdayOff.replace("0", "无");
            if(TextUtils.isEmpty(days)){
                tvStoreDay.setText("无");
            }else{
                tvStoreDay.setText(shopdayOff);
            }
        }else{
            tvStoreDay.setText("无");
        }
    }

    private void initRxBus() {
        mRxManager.on("selectDay", new Action1<String>() {
            @Override
            public void call(String s) {
                tvStoreDay.setText(s);
            }
        });
        mRxManager.on("days", new Action1<String>() {
            @Override
            public void call(String s) {
                days = s;
            }
        });
    }

    private void initListener() {

        /*etStartMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                    String str = ((EditText) view).getText().toString().trim();
                    if(!TextUtils.isEmpty(str)) {
                        String sendFee = etSendFee.getText().toString().trim();
                        if (TextUtils.isEmpty(sendFee)) {
                            showShortToast("请输入配送费");
                            return;
                        }
                        BigDecimal strBigDecimal = new BigDecimal(str);
                        BigDecimal sendBigDecimal = new BigDecimal(sendFee);
                        if (!(strBigDecimal.multiply(new BigDecimal("0.9962")).setScale(2, BigDecimal.ROUND_UP).compareTo(sendBigDecimal) >= 0)) {
                            etStartMoney.setText("");
                            showShortToast("满免金额应大于" + sendBigDecimal.divide(new BigDecimal("0.9962"), 2, BigDecimal.ROUND_UP).doubleValue());
                        }
                    }
            }
        });*/

        //店铺营业开始时间
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                showPopupWindow();
            }
        });
        //店铺营业结束时间
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                showPopupWindow();
            }
        });
        tvStoreDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SelectDayActivity.class);
            }
        });
        //保存
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String range = etRange.getText().toString().trim();
                String startMoney = etStartMoney.getText().toString().trim();
                String packageFee = etPackageFee.getText().toString().trim();
                String sendFee = etSendFee.getText().toString().trim();
                String sendTime = etSendTime.getText().toString().trim();
                String startTime = tvStartTime.getText().toString().trim();
                String endTime = tvEndTime.getText().toString().trim();
                String storeTime = tvStoreDay.getText().toString().trim();

                if(TextUtils.isEmpty(range)){
                    showShortToast("请输入配送范围");
                    return;
                }
                if(TextUtils.isEmpty(startMoney)){
                    showShortToast("请输入配送金额");
                    return;
                }
                if(TextUtils.isEmpty(packageFee)){
                    showShortToast("请输入包装费");
                    return;
                }
                if(TextUtils.isEmpty(sendFee)){
                    showShortToast("请输入配送费");
                    return;
                }
                try{
                    BigDecimal start = new BigDecimal(startMoney);
                    BigDecimal send = new BigDecimal(sendFee);
                    if(!(start.multiply(new BigDecimal("0.9962").setScale(2, BigDecimal.ROUND_UP)).compareTo(send) >= 0)){
                        showShortToast("满免金额应大于" + send.divide(new BigDecimal("0.9962"), 0, BigDecimal.ROUND_UP).doubleValue());
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(TextUtils.isEmpty(sendTime)){
                    showShortToast("请输入送达时间");
                    return;
                }
                if(TextUtils.isEmpty(startTime)){
                    showShortToast("请输入开始营业时间");
                    return;
                }
                if(TextUtils.isEmpty(endTime)){
                    showShortToast("请输入结束营业时间");
                    return;
                }
                if(TextUtils.isEmpty(storeTime)){
                    showShortToast("请输入店铺休息日");
                    return;
                }

                mPresenter.saveDistributionInfo(PreferencesUtils.getString(mContext, Constants.SHOP_ID), range, startMoney, packageFee, sendTime, sendFee, startTime, endTime, days);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void showPopupWindow() {
        if (null != popWindow && popWindow.isShowing()) {
            popWindow.dismiss();
        } else {
            initPopupWindow();
            backgroundAlpha(0.8f);
        }
    }

    private void initPopupWindow() {
        List<String> hour = new ArrayList<>();
        final List<String> second = new ArrayList<>();
        for(int i=0; i<60; i++){
            if(i<10){
                hour.add("0"+i);
                second.add("0"+i);
            }else if(i<24){
                hour.add(i+"");
                second.add(i+"");
            }else{
                second.add(i+"");
            }
        }
        popupWindowView = getLayoutInflater().inflate(R.layout.pop_time, null, false);
        popWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setFocusable(true);
        popupWindowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popWindow.showAtLocation(linear, Gravity.BOTTOM, 0, 0);

        TextView tvCancel = (TextView) popupWindowView.findViewById(R.id.tvCancel);
        TextView tvSure = (TextView) popupWindowView.findViewById(R.id.tvSure);

        //时间
        final WheelView wheelviewDay = popupWindowView.findViewById(R.id.wheelviewHour);
        wheelviewDay.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
        wheelviewDay.setSkin(WheelView.Skin.Holo); // common皮肤
        wheelviewDay.setWheelSize(3); // 设置滚轮个数
        wheelviewDay.setWheelData(hour);  // 数据集合

        //市
        final WheelView wheelviewTime = popupWindowView.findViewById(R.id.wheelviewSecond);
        wheelviewTime.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
        wheelviewTime.setSkin(WheelView.Skin.Holo); // common皮肤
        wheelviewTime.setWheelSize(3); // 设置滚轮个数
        wheelviewTime.setWheelData(second);  // 数据集合

        final List<String> hourList = hour;
        final List<String> secondList = second;

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hourStr = hourList.get(wheelviewDay.getCurrentPosition());
                String secondStr = secondList.get(wheelviewTime.getCurrentPosition());

                String startTime = tvStartTime.getText().toString().trim();
                String endTime = tvEndTime.getText().toString().trim();
                if(!TextUtils.isEmpty(startTime)){
                    String hourStart = startTime.substring(0, startTime.indexOf(":"));
                    String secondStart = startTime.substring(startTime.indexOf(":") + 1);
                    if(Integer.parseInt(hourStart)>Integer.parseInt(hourStr)){
                        showShortToast("结束时间应大于开始时间");
                        return;
                    }else if(Integer.parseInt(hourStart)==Integer.parseInt(hourStr)){
                        if(Integer.parseInt(secondStart)>=Integer.parseInt(secondStr)){
                            showShortToast("结束时间应大于开始时间");
                            return;
                        }
                    }
                }else if(!TextUtils.isEmpty(endTime)){
                    String hourEnd = endTime.substring(0, startTime.indexOf(":"));
                    String secondEnd = endTime.substring(startTime.indexOf(":") + 1);
                    if(Integer.parseInt(hourStr)>Integer.parseInt(hourEnd)){
                        showShortToast("结束时间应大于开始时间");
                        return;
                    }else if(Integer.parseInt(hourStr)==Integer.parseInt(hourEnd)){
                        if(Integer.parseInt(secondStr)>=Integer.parseInt(secondEnd)){
                            showShortToast("结束时间应大于开始时间");
                            return;
                        }
                    }
                }
                if(type == 0){
                    tvStartTime.setText(hourStr+":"+secondStr);
                }else{
                    tvEndTime.setText(hourStr+":"+secondStr);
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
    public void showResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    PreferencesUtils.putString(mContext, Constants.USER_INFO, new Gson().toJson(baseBeanResult.getData().getObject()));
                    if("1".equals(flag)){
                        Intent intent = new Intent(mContext, StoreInfoActivity.class);
                        intent.putExtra("type", flag);
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
}
