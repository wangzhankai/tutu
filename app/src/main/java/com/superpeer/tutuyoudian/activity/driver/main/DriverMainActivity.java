package com.superpeer.tutuyoudian.activity.driver.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.superpeer.base_libs.base.AppManager;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.base_libs.utils.AppUtils;
import com.superpeer.base_libs.utils.ConstantsUtils;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.NormalRefreshViewHolder;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.driver.info.DriverInfoActivity;
import com.superpeer.tutuyoudian.activity.driver.orderdetail.DriverOrderDetailActivity;
import com.superpeer.tutuyoudian.activity.main.MainActivity;
import com.superpeer.tutuyoudian.adapter.DriverOrderAdapter;
import com.superpeer.tutuyoudian.adapter.MFragmentStatePagerAdapter;
import com.superpeer.tutuyoudian.adapter.RecordAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.bean.PushBean;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.frament.driver.DriverOrderFragment;
import com.superpeer.tutuyoudian.frament.normalorder.NormalOrderFragment;
import com.superpeer.tutuyoudian.listener.OnCancelListener;
import com.superpeer.tutuyoudian.listener.OnCompleteListener;
import com.superpeer.tutuyoudian.listener.OnItemListener;
import com.superpeer.tutuyoudian.utils.SystemTTS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class DriverMainActivity extends BaseActivity<DriverMainPresenter, DriverMainModel> implements DriverMainContract.View {

    /*//极光推送
    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.lxkj.video.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";*/
    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private DriverOrderAdapter adapter;

    private LinearLayout linearOnline;
    private LinearLayout linearOffline;

    private boolean isOnline;
    private TextView tvOnline;
    private TextView tvOffline;
    private TextView tvStatus;

    private BaseBeanResult result;
    private int PAGE = 1;

    /**
     * /**
     * TagAliasCallback类是JPush开发包jar中的类，用于
     * 设置别名和标签的回调接口，成功与否都会回调该方法
     * 同时给定回调的代码。如果code=0,说明别名设置成功。
     * /**
     * 6001   无效的设置，tag/alias 不应参数都为 null
     * 6002   设置超时    建议重试
     * 6003   alias 字符串不合法    有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6004   alias超长。最多 40个字节    中文 UTF-8 是 3 个字节
     * 6005   某一个 tag 字符串不合法  有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6006   某一个 tag 超长。一个 tag 最多 40个字节  中文 UTF-8 是 3 个字节
     * 6007   tags 数量超出限制。最多 100个 这是一台设备的限制。一个应用全局的标签数量无限制。
     * 6008   tag/alias 超出总长度限制。总长度最多 1K 字节
     * 6011   10s内设置tag或alias大于3次 短时间内操作过于频繁
     **/
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("TAG", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("TAG", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("TAG", logs);
                    break;
            }
        }
    };
    private int delPos;
    private BaseObject userInfo;

    /*public class MessageReceiver extends BroadcastReceiver {

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
                    Log.i("base", "extras:"+extras);
                    Log.i("base", "message:"+message);
                    SystemTTS systemTTS = SystemTTS.getInstance(mContext);
                    systemTTS.playText(message);
                    mRxManager.post("jpushDriver", extras);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }*/

    //权限
    public String[] permissions = {
            //定位需要
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,

    };

    private XTabLayout mTab;
    private ViewPager viewPager;
    private String[] titles;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("兔兔跑腿");
        mIvLeft.setVisibility(View.GONE);
        setToolBarViewStubText("我的").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DriverInfoActivity.class);
            }
        });

        linearOnline = (LinearLayout) findViewById(R.id.linearOnline);
        linearOffline = (LinearLayout) findViewById(R.id.linearOffline);
        tvOnline = (TextView) findViewById(R.id.tvOnline);
        tvOffline = (TextView) findViewById(R.id.tvOffline);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        mTab = (XTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        mTab.addTab(mTab.newTab(), true);
        mTab.addTab(mTab.newTab(), false);
        mTab.addTab(mTab.newTab(), false);
        fragments.add(DriverOrderFragment.newInstance("3"));
        fragments.add(DriverOrderFragment.newInstance("4"));
        fragments.add(DriverOrderFragment.newInstance("5"));
        titles = new String[]{"待接单", "配送中", "已完成"};

        viewPager.setAdapter(new MFragmentStatePagerAdapter(getSupportFragmentManager(), fragments, titles));
        viewPager.setOffscreenPageLimit(fragments.size());
        mTab.setupWithViewPager(viewPager);
//        mTab.setTabMode(XTabLayout.MODE_SCROLLABLE);

        registerMessageReceiver();
        initPermission();
        initListener();

        setTagAndAlias();

        userInfo = getUserInfo();
        initData();

        mPresenter.update("0");

//        mPresenter.getOrderList(PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE+"", "10");

    }

    private void initData() {
        try{
            if(null!=userInfo){
                if(null!=userInfo.getRunnerStatus()){
                    if("0".equals(userInfo.getRunnerStatus())){
                        tvOnline.setBackgroundResource(R.drawable.bg_grey);
                        tvOffline.setBackgroundResource(R.drawable.bg_orange_circle);
                        tvStatus.setText("休息中");
                    }else{
                        isOnline = true;
                        tvOnline.setBackgroundResource(R.drawable.bg_orange_circle);
                        tvOffline.setBackgroundResource(R.drawable.bg_grey);
                        tvStatus.setText("接单中");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initPermission() {
        MPermissionUtils.requestPermissionsResult((Activity) mContext, 1, permissions, new MPermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied() {
                MPermissionUtils.showTipsDialog(mContext);
            }
        });
    }

    /**
     * 设置标签与别名
     */
    private void setTagAndAlias() {
        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        if (!TextUtils.isEmpty(PreferencesUtils.getString(mContext, Constants.SHOP_ID))){
            tags.add(PreferencesUtils.getString(mContext, Constants.SHOP_ID));//设置tag
        }
        //上下文、别名【Sting行】、标签【Set型】、回调
        JPushInterface.setAliasAndTags(mContext, PreferencesUtils.getString(mContext, Constants.SHOP_ID), tags,
                mAliasCallback);
        // }
    }

    private void initListener() {
        //营业
        linearOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOnline){
                    mPresenter.changeReceiptStatus(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "1");
                }else{
                    showShortToast("正在接单中");
                }
            }
        });
        //休息
        linearOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline){
                    mPresenter.changeReceiptStatus(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "0");
                }else{
                    showShortToast("正在休息中");
                }
            }
        });
    }

    /*public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }*/

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    /*@Override
    public void showResult(BaseBeanResult baseBeanResult) {
        try {
            if (null != baseBeanResult) {
                result = baseBeanResult;
                if ("1".equals(baseBeanResult.getCode())) {
                    if(null!=baseBeanResult.getData()) {
                        if (null != baseBeanResult.getData().getList() && baseBeanResult.getData().getList().size() > 0) {
                            if (PAGE == 1) {
                                adapter.setNewData(baseBeanResult.getData().getList());
                            } else {
                                adapter.addData(baseBeanResult.getData().getList());
                            }
                        } else {
                            adapter.getData().clear();
                            adapter.notifyDataSetChanged();
                        }
                        adapter.loadComplete();
                        refresh.endLoadingMore();
                        refresh.endRefreshing();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void showChangeResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    isOnline = !isOnline;
                    if(isOnline){
                        tvOnline.setBackgroundResource(R.drawable.bg_orange_circle);
                        tvOffline.setBackgroundResource(R.drawable.bg_grey);
                        tvStatus.setText("接单中");
                    }else{
                        tvOnline.setBackgroundResource(R.drawable.bg_grey);
                        tvOffline.setBackgroundResource(R.drawable.bg_orange_circle);
                        tvStatus.setText("休息中");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showGradResult(BaseBeanResult baseBeanResult) {
        try{
//            PAGE = 1;
//            mPresenter.getOrderList(PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE+"", "10");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showUpdate(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    toUpdate(baseBeanResult);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*@Override
    public void showGiveUpResult(BaseBeanResult baseBeanResult) {
        try{
            adapter.getData().remove(delPos);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    //监听返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    //两次返回 退出
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showShortToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().finishAllActivity();
        }
    }

    /*@Override
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }*/

    private void toUpdate(final BaseBeanResult baseBeanResult) {
        try {
            BaseObject object = baseBeanResult.getData().getObject();
            //判断版本号
            if (AppUtils.getLocalVersion(mContext) < Integer.parseInt(object.getVersionName())) {
//                AllenVersionChecker.getInstance().downloadOnly(
//                        UIData.create().setTitle(object.getVersionNumber()).setContent("版本更新")
//                                .setDownloadUrl(object.getVersionSrc())).excuteMission(this);
//                                        .setDownloadUrl("https://imtt.dd.qq.com/16891/D21910E083EA4C497C5BD59A76C5577B.apk?fsname=com.tencent.mm_6.7.3_1360.apk&csr=1bbd")).excuteMission(this);
                DownloadBuilder builder = AllenVersionChecker.getInstance().downloadOnly(
                        UIData.create().setTitle(object.getVersionNumber()).setContent("版本更新")
                                .setDownloadUrl(object.getVersionSrc()));
                builder.excuteMission(this);
                builder.setForceUpdateListener(new ForceUpdateListener() {
                    @Override
                    public void onShouldForceUpdate() {

                    }
                });
            } /*else {
                showShortToast("当前版本为最新版本");
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
