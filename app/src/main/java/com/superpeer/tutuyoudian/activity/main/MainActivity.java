package com.superpeer.tutuyoudian.activity.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superpeer.base_libs.base.AppManager;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.MediaManager;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.addshop.AddShopActivity;
import com.superpeer.tutuyoudian.activity.announce.AnnounceActivity;
import com.superpeer.tutuyoudian.activity.cash.withdraw.CashWithDrawActivity;
import com.superpeer.tutuyoudian.activity.collageorder.CollageOrderActivity;
import com.superpeer.tutuyoudian.activity.collageset.CollageSetActivity;
import com.superpeer.tutuyoudian.activity.datacount.CountActivity;
import com.superpeer.tutuyoudian.activity.order.OrderActivity;
import com.superpeer.tutuyoudian.activity.order.detail.OrderDetailActivity;
import com.superpeer.tutuyoudian.activity.setting.StoreSettingActivity;
import com.superpeer.tutuyoudian.activity.shoplibrary.ShopLibraryActivity;
import com.superpeer.tutuyoudian.activity.shopmanager.ShopManagerActivity;
import com.superpeer.tutuyoudian.activity.store.StoreApplyActivity;
import com.superpeer.tutuyoudian.activity.verify.VerifyActivity;
import com.superpeer.tutuyoudian.adapter.DialogCategoryAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.bean.PushBean;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.widget.NoScrollRecyclerView;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.functions.Action1;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {

    /*//极光推送
    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.lxkj.video.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";*/
    private static final int REQUEST_CODE_SCAN = 888;
    private RelativeLayout linearNotice;
    private TextView tvNum;
    private String barCode = "";
    private ImageView ivSummon;
    private TextView tvTitle;
    private PushBean bean;
//    private TextView tvCode;
    private ImageView ivCode;

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
                    SystemTTS systemTTS = SystemTTS.getInstance(mContext);
                    systemTTS.playText(message);

                    bean = new Gson().fromJson(extras, PushBean.class);

                    if(null!=bean&&null!=bean.getOrderType()&&"1".equals(bean.getOrderType())){
                        mRxManager.post("jpush", bean);
                    }
                }
            } catch (Exception e){
            }
        }
    }*/

    private LinearLayout linearVisit;
    private TextView tvNormalOrder;
    private BaseObject userInfo;
    private LinearLayout linearOnline;
    private LinearLayout linearOffline;

    private boolean isOnline;
    private TextView tvOnline;
    private TextView tvOffline;

    private TextView tvCollageSet;
    private TextView tvDataCount;
    private TextView tvCashWithDraw;
    private TextView tvStoreSet;
    private TextView tvStatus;
    private TextView tvCollageOrder;
    private TextView tvGetGoods;
    private TextView tvAddGoods;
    private TextView tvGoodsManager;
    private View popupWindowView;
    private PopupWindow popWindow;
    private ImageView ivAuto;
    private TextView tvTodayMoney;
    private TextView tvYesterdayMoney;
    private TextView tvTodayNum;
    private TextView tvYesterdayNum;
    private TextView tvYesterdayScan;
    private TextView tvTodayScan;
    private TextView tvTips;

    private String status = "1";
    private String callStatus = "1";
    private List<BaseList> categoryList;

    private final static int RESULT_CODE = 100;

    //权限
    public String[] permissions = {
            //定位需要
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,

    };

    @Override
    protected boolean hasHeadTitle() {
        return false;
    }

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        linearNotice = (RelativeLayout) findViewById(R.id.linearNotice);

        linearNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(AnnounceActivity.class, RESULT_CODE);
            }
        });

        /*registerMessageReceiver();*/

        userInfo = getUserInfo();

        linearVisit = (LinearLayout) findViewById(R.id.linearVisit);
        linearOnline = (LinearLayout) findViewById(R.id.linearOnline);
        linearOffline = (LinearLayout) findViewById(R.id.linearOffline);

//        tvCode = (TextView) findViewById(R.id.tvCode);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvNum = (TextView) findViewById(R.id.tvNum);
        tvNormalOrder = (TextView) findViewById(R.id.tvNormalOrder);
        tvCollageOrder = (TextView) findViewById(R.id.tvCollageOrder);
        tvGetGoods = (TextView) findViewById(R.id.tvGetGoods);
        tvAddGoods = (TextView) findViewById(R.id.tvAddGoods);
        tvGoodsManager = (TextView) findViewById(R.id.tvGoodsManager);

        tvOnline = (TextView) findViewById(R.id.tvOnline);
        tvOffline = (TextView) findViewById(R.id.tvOffline);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        tvCollageSet = (TextView) findViewById(R.id.tvCollageSet);
        tvDataCount = (TextView) findViewById(R.id.tvDataCount);
        tvCashWithDraw = (TextView) findViewById(R.id.tvCashWithDraw);
        tvStoreSet = (TextView) findViewById(R.id.tvStoreSet);

        tvTodayMoney = (TextView) findViewById(R.id.tvTodayMoney);
        tvYesterdayMoney = (TextView) findViewById(R.id.tvYesterdayMoney);
        tvTodayNum = (TextView) findViewById(R.id.tvTodayNum);
        tvYesterdayNum = (TextView) findViewById(R.id.tvYesterdayNum);
        tvTodayScan = (TextView) findViewById(R.id.tvTodayScan);
        tvYesterdayScan = (TextView) findViewById(R.id.tvYesterdayScan);
        tvTips = (TextView) findViewById(R.id.tvTips);

        ivSummon = (ImageView) findViewById(R.id.ivSummon);
        ivAuto = (ImageView) findViewById(R.id.ivAuto);
        ivCode = (ImageView) findViewById(R.id.ivCode);

        initPermission();

        initData();
        initListener();

        setTagAndAlias();

        initRxBus();

        //获取首页数据
        mPresenter.getMainData(PreferencesUtils.getString(mContext, Constants.SHOP_ID));

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

    private void initRxBus() {
        mRxManager.on("notice", new Action1<String>() {
            @Override
            public void call(String s) {
                //获取首页数据
                mPresenter.getMainData(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
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

    private void initData() {
        try{
            if(null!=userInfo){
                if(null!=userInfo.getName()){
                    tvTitle.setText(userInfo.getName());
                }
                if(null!=userInfo.getSendStatus()){
                    if("1".equals(userInfo.getSendStatus())){
                        callStatus = "0";
                        ivSummon.setImageResource(R.mipmap.iv_switch_on);
                    }else{
                        callStatus = "1";
                        ivSummon.setImageResource(R.mipmap.iv_switch_off);
                    }
                }
                if(null!=userInfo.getOperatingStatus()){
                    if("1".equals(userInfo.getOperatingStatus())){
                        isOnline = true;
                        status = "0";
                        ivAuto.setImageResource(R.mipmap.iv_switch_on);
                    }else{
                        status = "1";
                        ivAuto.setImageResource(R.mipmap.iv_switch_off);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initListener() {
        //链接小程序
        ivCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appId = Constants.APP_ID; // 填应用AppId
                IWXAPI api = WXAPIFactory.createWXAPI(MainActivity.this, appId);

                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = Constants.WEIXIN_XIAOCHENGXU_ID; // 填小程序原始id
                req.path = "/pages/index/index?shopId="+PreferencesUtils.getString(mContext, Constants.SHOP_ID);                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
//                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
                api.sendReq(req);
            }
        });
        /*//商家邀请码
        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ShopCodeActivity.class);
            }
        });*/
        //数据统计
        tvDataCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(CountActivity.class, RESULT_CODE);
            }
        });
        //普通订单
        tvNormalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(OrderActivity.class, RESULT_CODE);
            }
        });
        //拼团订单
        tvCollageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(CollageOrderActivity.class, RESULT_CODE);
            }
        });
        //提货验证
        tvGetGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(VerifyActivity.class, RESULT_CODE);
            }
        });
        //添加商品
        tvAddGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=PreferencesUtils.getString(mContext, Constants.IS_IDENTIFY)&&!"0".equals(PreferencesUtils.getString(mContext, Constants.IS_IDENTIFY))
                &&!"".equals(PreferencesUtils.getString(mContext, Constants.IS_IDENTIFY))){
                    showPopupWindow();
                }else{
                    startActivityForResult(StoreApplyActivity.class, RESULT_CODE);
                }
            }
        });

        //商品管理
        tvGoodsManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(ShopManagerActivity.class, RESULT_CODE);
            }
        });

        //营业
        linearOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOnline){
                    mPresenter.changeStatus(userInfo.getShopId(), "1");
                }else{
                    showShortToast("正在营业中");
                }
            }
        });
        //休息
        linearOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline){
                    mPresenter.changeStatus(userInfo.getShopId(), "0");
                }else{
                    showShortToast("正在休息中");
                }
            }
        });
        //资金提现
        tvCashWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(CashWithDrawActivity.class, RESULT_CODE);
            }
        });
        //店铺设置
        tvStoreSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(StoreApplyActivity.class);
                startActivityForResult(StoreSettingActivity.class, RESULT_CODE);
            }
        });
        //自动接单
        ivAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline){
                    mPresenter.changeStatus(userInfo.getShopId(), "0");
                }else{
                    mPresenter.changeStatus(userInfo.getShopId(), "1");
                }
//                mPresenter.autoChange(PreferencesUtils.getString(mContext, Constants.SHOP_ID), status);
            }
        });
        //召唤跑腿
        ivSummon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.callRunner(PreferencesUtils.getString(mContext, Constants.SHOP_ID), callStatus);
            }
        });
        //拼团设置
        tvCollageSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(CollageSetActivity.class, RESULT_CODE);
            }
        });
    }

    /**
     * 添加商品  弹出PopupWindow
     */
    private void showPopupWindow() {
        if (null != popWindow && popWindow.isShowing()) {
            popWindow.dismiss();
        } else {
            initPopupWindow();
            backgroundAlpha(0.8f);
        }
    }

    private void initPopupWindow() {

        popupWindowView = getLayoutInflater().inflate(R.layout.pop_add, null, false);
        popWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setFocusable(true);
        popupWindowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        TextView tvCancel = (TextView) popupWindowView.findViewById(R.id.tvCancel);
        TextView tvShopSelect = (TextView) popupWindowView.findViewById(R.id.tvShopSelect);
        TextView tvScan = (TextView) popupWindowView.findViewById(R.id.tvScan);
        TextView tvUpload = (TextView) popupWindowView.findViewById(R.id.tvUpload);

        //商品库上传
        tvShopSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (popWindow != null && popWindow.isShowing()) {
                    popWindow.dismiss();
                    popWindow = null;
                }*/
//                mPresenter.getCategory(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
                startActivityForResult(ShopLibraryActivity.class, RESULT_CODE);
            }
        });
        //扫码上传
        tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (popWindow != null && popWindow.isShowing()) {
                    popWindow.dismiss();
                    popWindow = null;
                }*/
                initQRCode();
            }
        });
        //本地上传
        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (popWindow != null && popWindow.isShowing()) {
                    popWindow.dismiss();
                    popWindow = null;
                }*/
                Intent intent = new Intent(mContext, AddShopActivity.class);
                intent.putExtra("type", "0");
                startActivityForResult(intent, RESULT_CODE);
            }
        });
        //取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow != null && popWindow.isShowing()) {
                    popWindow.dismiss();
                    popWindow = null;
                }
            }
        });

        popWindow.showAtLocation(tvAddGoods, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

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
     * 选择店铺分类
     * @param categoryList
     */
    private void showCategoryWindow(final List<BaseList> categoryList) {
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    List<BaseList> list = new ArrayList<>();
                    for(int i=0; i<categoryList.size();i++){
                        if(categoryList.get(i).isCheck()){
                            list.add(categoryList.get(i));
                        }
                    }
                    Intent intent = new Intent(mContext, ShopLibraryActivity.class);
                    intent.putExtra("categoryList", (Serializable) list);
                    startActivityForResult(intent, RESULT_CODE);
                    dialog.dismiss();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setTitle("选择分类");
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_category, null);

            NoScrollRecyclerView recyclerCategory = (NoScrollRecyclerView) view.findViewById(R.id.recyclerCategory);
            recyclerCategory.setLayoutManager(new LinearLayoutManager(mContext));
            final DialogCategoryAdapter categoryAdapter = new DialogCategoryAdapter(R.layout.dialog_category_item, categoryList);
            recyclerCategory.setAdapter(categoryAdapter);
            recyclerCategory.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                    BaseList bean = (BaseList) categoryAdapter.getItem(position);
                    bean.setCheck(!bean.isCheck());
                    adapter.notifyDataSetChanged();
                }
            });
            dialog.setView(view);
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void initQRCode() {
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
            QrManager.getInstance().init(qrConfig).startScan(MainActivity.this, new QrManager.OnScanResultCallback() {
                @Override
                public void onScanSuccess(String result) {
                    barCode = result;
                    mPresenter.codeUpload(result, PreferencesUtils.getString(mContext, Constants.SHOP_ID));
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
    public void showNoticeResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData()){
                        if(null!=baseBeanResult.getData().getObject()){
                            showNoticeDialog(baseBeanResult.getData().getObject());
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showNoticeDialog(BaseObject object) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_notice, null);

        ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
//        TextView tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        WebView mWeb = (WebView) view.findViewById(R.id.webView);


        if(null!=object&&null!=object.getContent()){
            initWvSetting(mWeb, object.getContent());
//            tvDesc.setText(object.getContent());
        }

        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(view);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 初始化webview
     * @param mWeb
     * @param content
     */
    private void initWvSetting(WebView mWeb, String content) {
        WebSettings settings = mWeb.getSettings();
        //设置允许和js交互
        settings.setJavaScriptEnabled(true);

        if(!TextUtils.isEmpty(content)){
            mWeb.loadDataWithBaseURL(null,content, "text/html" , "utf-8", null);
        }

    }

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
                        ivAuto.setImageResource(R.mipmap.iv_switch_on);
//                        tvOnline.setBackgroundResource(R.drawable.bg_orange_circle);
//                        tvOffline.setBackgroundResource(R.drawable.bg_grey);
//                        tvStatus.setText("营业中");
                    }else{
                        ivAuto.setImageResource(R.mipmap.iv_switch_off);
//                        tvOnline.setBackgroundResource(R.drawable.bg_grey);
//                        tvOffline.setBackgroundResource(R.drawable.bg_orange_circle);
//                        tvStatus.setText("休息中");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showMainData(BaseBeanResult baseBeanResult) {
        try {
            //获取公告弹窗
            if (null != userInfo && null != userInfo.getShopId())
                mPresenter.getLoginNotice(userInfo.getShopId());
            if("1".equals(baseBeanResult.getCode())){
                if(null!=baseBeanResult.getData()){
                    if(null!=baseBeanResult.getData().getObject())
                        initMainData(baseBeanResult.getData().getObject());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showUpload(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    Intent intent = new Intent(mContext, AddShopActivity.class);
                    if(null!=baseBeanResult.getData()&&null!=baseBeanResult.getData().getObject()){
                        intent.putExtra("bean", baseBeanResult.getData().getObject());
                    }
                    intent.putExtra("barCode", barCode);
                    startActivityForResult(intent, RESULT_CODE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showAutoResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    if(status.equals("1")){
                        status = "0";
                        ivAuto.setImageResource(R.mipmap.iv_switch_on);
                    }else{
                        status = "1";
                        ivAuto.setImageResource(R.mipmap.iv_switch_off);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showCategoryResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if ("1".equals(baseBeanResult.getCode())) {
                    if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                        categoryList = baseBeanResult.getData().getList();
                        showCategoryWindow(categoryList);
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
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.putExtra("orderId", "orderId");
                    startActivity(intent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showCallResult(BaseBeanResult baseBeanResult) {
        if(null!=baseBeanResult){
            if(null!=baseBeanResult.getMsg()){
                showShortToast(baseBeanResult.getMsg());
            }
            if("1".equals(baseBeanResult.getCode())){
                if(callStatus.equals("1")){
                    callStatus = "0";
                    ivSummon.setImageResource(R.mipmap.iv_switch_on);
                }else{
                    callStatus = "1";
                    ivSummon.setImageResource(R.mipmap.iv_switch_off);
                }
            }
        }
    }

    private void initMainData(BaseObject object) {
        try{
            if(null!=object.getTodayMoney()){
                tvTodayMoney.setText(numberFormat.format(new BigDecimal(object.getTodayMoney())));
            }
            if(null!=object.getYesterdayMoney()){
                tvYesterdayMoney.setText("昨日"+numberFormat.format(new BigDecimal(object.getYesterdayMoney())));
            }
            if(null!=object.getTodayOrderNum()){
                tvTodayNum.setText(object.getTodayOrderNum());
            }
            if(null!=object.getYesterdayOrderNum()){
                tvYesterdayNum.setText("昨日"+object.getYesterdayOrderNum());
            }
            if(null!=object.getTodayBrowseNum()){
                tvTodayScan.setText(object.getTodayBrowseNum());
            }
            if(null!=object.getYesterdayBrowseNum()){
                tvYesterdayScan.setText("昨日"+object.getYesterdayBrowseNum());
            }
            if(null!=object.getNoRead()&&!"".equals(object.getNoRead())&&!"0".equals(object.getNoRead())){
                tvNum.setVisibility(View.VISIBLE);
                tvNum.setText(object.getNoRead());
            }
            if(null!=object.getValidityPeriod()&&!"".equals(object.getValidityPeriod())){
                PreferencesUtils.putString(mContext, Constants.VALIDITYPERIOD, object.getValidityPeriod());
                mRxManager.post("validityPeriod", object.getValidityPeriod());
            }
            if(null!=object.getActivationStatus()){
                if("0".equals(object.getActivationStatus())){
//                    ivAuto.setImageResource(R.mipmap.iv_switch_off);
                    tvTips.setVisibility(View.VISIBLE);
                    tvTips.setSelected(true);
//                    tvTips.setText("您的店铺已到期，请重新续费");
                }else{
//                    ivAuto.setImageResource(R.mipmap.iv_switch_on);
                    tvTips.setVisibility(View.GONE);
                }
            }
            if(null!=object.getName()){
                tvTitle.setText(object.getName());
            }
            if(null!=object.getSendStatus()){
                if("1".equals(object.getSendStatus())){
                    callStatus = "0";
                    ivSummon.setImageResource(R.mipmap.iv_switch_on);
                }else{
                    callStatus = "1";
                    ivSummon.setImageResource(R.mipmap.iv_switch_off);
                }
            }
            if(null!=object.getOperatingStatus()){
                if("1".equals(object.getOperatingStatus())){
                    isOnline = true;
                    status = "0";
                    ivAuto.setImageResource(R.mipmap.iv_switch_on);
                }else{
                    status = "1";
                    ivAuto.setImageResource(R.mipmap.iv_switch_off);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

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
        super.onDestroy();validityPeriod
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            // 扫描二维码/条码回传
            if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
                if (data != null) {
                    String content = data.getStringExtra(Constant.CODED_CONTENT);
                    barCode = content;
                    mPresenter.codeUpload(content, PreferencesUtils.getString(mContext, Constants.SHOP_ID));
                }
            }else {
                //获取首页数据
                mPresenter.getMainData(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void playVoice(){
        final MediaPlayer mp = MediaPlayer.create(mContext, R.raw.money);//重新设置要播放的音频

        mp.start();//开始播放

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                if(null!=mp){
                    mp.stop();
                    mp.release();
                }
            }
        });
    }
}
