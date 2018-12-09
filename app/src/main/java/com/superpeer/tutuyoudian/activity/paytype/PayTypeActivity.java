package com.superpeer.tutuyoudian.activity.paytype;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.addaccount.AddAccountActivity;
import com.superpeer.tutuyoudian.adapter.PayAdapter;
import com.superpeer.tutuyoudian.adapter.PayTypeAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.widget.NoScrollRecyclerView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

public class PayTypeActivity extends BaseActivity<PayTypePresenter, PayTypeModel> implements PayTypeContract.View {

    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private PayTypeAdapter adapter;
    private View popupWindowView;
    private PopupWindow popWindow;
    private LinearLayout linear;
    private UMShareAPI mShareAPI;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_type;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("选择收款方式");

        mShareAPI = UMShareAPI.get(this);

        linear = (LinearLayout) findViewById(R.id.linear);
        initRecyclerView();

        setToolBarViewStubImageRes(R.mipmap.iv_collage_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        initRxBus();

        mPresenter.getPayType(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
    }

    private void initRxBus() {
        mRxManager.on("savePayType", new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.getPayType(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
            }
        });
    }

    /**
     * 选择收款方式
     */
    /*private void showTypeWindow() {
        try{
            List<String> list = new ArrayList<>();
            list.add("微信");
            list.add("银行卡");
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_category, null);

            NoScrollRecyclerView recyclerCategory = (NoScrollRecyclerView) view.findViewById(R.id.recyclerCategory);
            recyclerCategory.setLayoutManager(new LinearLayoutManager(mContext));
            final PayAdapter payTypeAdapter = new PayAdapter(R.layout.dialog_category_item, list);
            recyclerCategory.setAdapter(payTypeAdapter);
            recyclerCategory.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                    payTypeAdapter.setSelectPos(position);
                    payTypeAdapter.notifyDataSetChanged();
                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if("0".equals(payTypeAdapter.getSelectPos())){

                    }else {
                        Intent intent = new Intent(mContext, AddAccountActivity.class);
                        startActivity(intent);
                    }
                    dialog.dismiss();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setTitle("选择收款方式");
            dialog.setView(view);
            dialog.show();
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void showPopupWindow() {
        if (null != popWindow && popWindow.isShowing()) {
            popWindow.dismiss();
        } else {
            showTypeWindow();
            backgroundAlpha(0.8f);
        }
    }

    private void showTypeWindow(){
        popupWindowView = getLayoutInflater().inflate(R.layout.dialog_pay_type, null, false);
        popWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setFocusable(true);
        popupWindowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popWindow.showAtLocation(linear, Gravity.BOTTOM, 0, 0);

        TextView tvBank = (TextView) popupWindowView.findViewById(R.id.tvBank);
        TextView tvWx = (TextView) popupWindowView.findViewById(R.id.tvWx);
        TextView tvCancel = (TextView) popupWindowView.findViewById(R.id.tvCancel);

        final List dataList = adapter.getData();

        tvBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                if(null!=dataList&&dataList.size()>0){
                    for(int i=0;i<dataList.size();i++){
                        if("1".equals(((BaseList)dataList.get(i)).getAccountType())){
                            showTipDialog();
                            return;
                        }
                    }
                }
                Intent intent = new Intent(mContext, AddAccountActivity.class);
                startActivity(intent);
            }
        });

        tvWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WXLogin();
                //微信登录
                mShareAPI.doOauthVerify(PayTypeActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                popWindow.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
     * 登录微信
     */
    private void WXLogin() {
        IWXAPI wxapi= WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        wxapi.registerApp(Constants.APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        wxapi.sendReq(req);
    }

    private void showTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("是否添加银行卡");
        builder.setMessage("添加银行卡会替换掉之前的银行卡，是否继续？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, AddAccountActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initRecyclerView() {

        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new PayTypeAdapter(R.layout.item_pay_type, null);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);

        rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter myAdapter, View view, int position) {
                adapter.setSelectPos(position);
                adapter.notifyDataSetChanged();
                mRxManager.post("selectAccount", ((BaseList)adapter.getItem(position)));
                finish();
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
    public void showPayType(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                    adapter.setNewData(baseBeanResult.getData().getList());
                }
                adapter.loadComplete();
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
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @param platform 平台名称
         * @desc 授权开始的回调
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.i("base", "start");
        }

        /**
         * @param platform 平台名称
         * @param action   行为序号，开发者用不上
         * @param data     用户资料返回
         * @desc 授权成功的回调
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //获取平台信息
            mShareAPI.getPlatformInfo((Activity) mContext, platform, new UMAuthListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {

                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int status, Map<String, String> map) {
                    //status为登录状态,info为登录信息
                    if (status == 2 && map != null) {
                        if("0".equals(PreferencesUtils.getString(mContext, Constants.USER_TYPE))) {
                            mPresenter.saveAccount("0", PreferencesUtils.getString(mContext, Constants.SHOP_ID), map.get("openid"), map.get("unionid"), map.get("name"));
                        }else{
                            mPresenter.saveAccountRunner(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "1", map.get("openid"), map.get("unionid"), map.get("name"));
                        }

                    } else {
                        Log.d("base", "发生错误：" + status);
                    }
                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    Toast.makeText(mContext, "授权失败", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    Toast.makeText(mContext, "授权取消", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Log.i("base", "error");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Log.i("base", "cancel");
        }
    };
}
