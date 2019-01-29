package com.superpeer.tutuyoudian.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.superpeer.base_libs.base.AppManager;
import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.baserx.RxManager;
import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.base_libs.utils.ConstantsUtils;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.utils.TUtil;
import com.superpeer.base_libs.utils.ToastUitl;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.driver.orderdetail.DriverOrderDetailActivity;
import com.superpeer.tutuyoudian.activity.main.MainActivity;
import com.superpeer.tutuyoudian.activity.order.detail.OrderDetailActivity;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.bean.PushBean;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.utils.SystemTTS;
import com.zhy.autolayout.AutoLayoutActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by wangzhankai on 2018/2/8.
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AutoLayoutActivity {

    //极光推送
    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.lxkj.video.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public T mPresenter;
    public E mModel;
    public Context mContext;
    public RxManager mRxManager;
    protected TextView mTvTitle;
    protected ImageView mIvLeft;
    protected ViewStub mViewStub;
    public Format numberFormat = new DecimalFormat("0.00");
    private AlertDialog dialog;

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String message = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + message + "\n");
                    if (!ConstantsUtils.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }

                    if("0".equals(PreferencesUtils.getString(context, Constants.USER_TYPE))){       //商家
                        PushBean bean = new Gson().fromJson(extras, PushBean.class);

                        if(null!=bean&&null!=bean.getOrderType()&&"1".equals(bean.getOrderType())){
                            if(null!=dialog){
                                if(!dialog.isShowing()){
                                    if(null!=bean.getSound()){
                                        playVoice(bean.getSound());
                                    }
                                    showOrderDialog(bean);
                                }
                            }else{
                                if(null!=bean.getSound()){
                                    playVoice(bean.getSound());
                                }
                                showOrderDialog(bean);
                            }
                        }
                    }else{
                        if(null!=dialog){
                            if(!dialog.isShowing()){
                                showOrderDialog(extras, message);
                            }
                        }else{
                            showOrderDialog(extras, message);
                        }
                        mRxManager.post("drivermain", "");
                    }
                }
            } catch (Exception e){
            }
        }
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public void showOrderDialog(String extra, String msg) {
        try {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_driver_order, null);

        ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
        TextView tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        TextView tvGetOrder = (TextView) view.findViewById(R.id.tvGetOrder);
        TextView tvLater = (TextView) view.findViewById(R.id.tvLater);

        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(view);

        try {
            final PushBean bean = new Gson().fromJson(extra, PushBean.class);
            if(null!=bean.getSound()) {
                playVoice(bean.getSound());
            }
            if (null != bean) {
                if (null != bean.getShopName()) {
                    tvDesc.setText(bean.getShopName()+"有新订单了，兔兔跑腿，快抢单吧");
                }
            }
            //接单
            tvGetOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!=bean.getOrderId()){
                        dialog.dismiss();
                        Observable<BaseBeanResult>  model = Api.getInstance().service.grabOrder(bean.getOrderId(), PreferencesUtils.getString(mContext, Constants.SHOP_ID)).map(new Func1<BaseBeanResult, BaseBeanResult>() {
                            @Override
                            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                                return baseBeanResult;
                            }
                        }).compose(RxSchedulers.<BaseBeanResult>io_main());
                        mRxManager.add(model.subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
                            @Override
                            protected void _onNext(BaseBeanResult baseBeanResult) {
                                try{
                                    if(null!=baseBeanResult){
                                        if(null!=baseBeanResult.getMsg()){
                                            showShortToast(baseBeanResult.getMsg());
                                        }
                                        if("1".equals(baseBeanResult.getCode())){
                                            Intent intent = new Intent(mContext, DriverOrderDetailActivity.class);
                                            intent.putExtra("orderId", bean.getOrderId());
                                            startActivity(intent);
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            protected void _onError(String message) {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            }
                        }));
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void showOrderDialog(final PushBean bean) {
        try {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_order, null);

        ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
        TextView tvNum = (TextView) view.findViewById(R.id.tvNum);
        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        TextView tvGetOrder = (TextView) view.findViewById(R.id.tvGetOrder);
        TextView tvLater = (TextView) view.findViewById(R.id.tvLater);

        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(view);

        try {
            if (null != bean.getNum()) {
                tvNum.setText(bean.getNum());
            }
            if(null!=bean.getTotalPrice()){
                tvPrice.setText("￥"+bean.getTotalPrice());
            }
            tvGetOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!=bean.getOrderId()){
                        dialog.dismiss();
                        Observable<BaseBeanResult>   model = Api.getInstance().service.receiptOrder(bean.getOrderId()).map(new Func1<BaseBeanResult, BaseBeanResult>() {
                            @Override
                            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                                return baseBeanResult;
                            }
                        }).compose(RxSchedulers.<BaseBeanResult>io_main());
                        mRxManager.add(model.subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
                            @Override
                            protected void _onNext(BaseBeanResult baseBeanResult) {
                                try{
                                    if(null!=baseBeanResult){
                                        if(null!=baseBeanResult.getMsg()){
                                            showShortToast(baseBeanResult.getMsg());
                                        }
                                        if("1".equals(baseBeanResult.getCode())){
                                            Intent intent = new Intent(mContext, OrderDetailActivity.class);
                                            intent.putExtra("orderId", bean.getOrderId());
                                            startActivity(intent);
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            protected void _onError(String message) {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            }
                        }));
                    }
                }
            });
            tvLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRxManager=new RxManager();
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel= TUtil.getT(this,1);
        if(mPresenter!=null){
            mPresenter.mContext=this;
        }
        if(hasHeadTitle()){
            mTvTitle = (TextView) findViewById(com.superpeer.base_libs.R.id.tvTitle);
            mIvLeft = (ImageView) findViewById(com.superpeer.base_libs.R.id.ivLeft);
            mViewStub = (ViewStub) findViewById(com.superpeer.base_libs.R.id.viewStub);

            mIvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        /*if(hasStatus()){
            StatusBarCompat.compat(this, R.color.colorPrimary);
        }*/

        this.initPresenter();
        this.initView();

        registerMessageReceiver();

        /*mRxManager.on("jpush", new Action1<PushBean>() {
            @Override
            public void call(PushBean pushBean) {
                showOrderDialog(pushBean);
            }
        });
        mRxManager.on("jpushDriver", new Action1<String>() {
            @Override
            public void call(String pushBean) {
                showOrderDialog(pushBean);
            }
        });*/

    }

    protected BaseObject getUserInfo(){
        try {
            String userInfoStr = PreferencesUtils.getString(mContext, Constants.USER_INFO);
            BaseObject userInfo = new Gson().fromJson(userInfoStr, BaseObject.class);
            return userInfo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    protected boolean hasStatus() {
        return true;
    }

    /**
     * 设置标题栏标题
     *
     * @param title 标题
     */
    protected void setHeadTitle(String title) {
        if (hasHeadTitle()) {
            mTvTitle.setText(title);
        }
    }

    /**
     * 设置标题栏左侧图标
     */
    protected void setLeftIcon(@DrawableRes int drawableRes){
        if(hasHeadTitle()){
            mIvLeft.setImageResource(drawableRes);
        }
    }

    /**
     * 默认带title
     * @return
     */
    protected boolean hasHeadTitle() {
        return true;
    }

    /**
     * 设置标题栏右侧为文字
     */
    protected TextView setToolBarViewStubText(String text){
        if(hasHeadTitle()){
            mViewStub.setLayoutResource(com.superpeer.base_libs.R.layout.toolbar_text);
            TextView mTvRight = (TextView) mViewStub.inflate();
            mTvRight.setText(text);
            return mTvRight;
        }
        return null;
    }

    /**
     * 设置标题栏右侧图标
     */
    protected ImageView setToolBarViewStubImageRes(@DrawableRes int drawableRes){
        if(hasHeadTitle()){
            mViewStub.setLayoutResource(com.superpeer.base_libs.R.layout.toolbar_img);
            ImageView mIvRight = (ImageView) mViewStub.inflate();
            mIvRight.setImageResource(drawableRes);
            return mIvRight;
        }
        return null;
    }


    /**
     * 设置layout前配置
     */
    protected void doBeforeSetcontentView() {

        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();
    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();
    //初始化view
    public abstract void initView();

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(this,text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUitl.showShort(this,resId);
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUitl.showLong(this,resId);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUitl.showLong(this,text);
    }
    /**
     //     * 网络访问错误提醒
     //     */
//    public void showNetErrorTip() {
//        ToastUitl.showToastWithImg(getText(R.string.net_error).toString(),R.drawable.ic_wifi_off);
//    }
//    public void showNetErrorTip(String error) {
//        ToastUitl.showToastWithImg(error,R.drawable.ic_wifi_off);
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
        AppManager.getAppManager().finishActivity(this);
    }

    public void playVoice(String voiceName){
        try {
            int rawId = getResources().getIdentifier(voiceName.substring(0, voiceName.indexOf(".")), "raw", "com.superpeer.tutuyoudian");
            final MediaPlayer mp = MediaPlayer.create(mContext, rawId);//重新设置要播放的音频

            mp.start();//开始播放

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer arg0) {
                    if (null != mp) {
                        mp.stop();
                        mp.release();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
