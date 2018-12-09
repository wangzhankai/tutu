package com.superpeer.tutuyoudian.activity.announce.detail;

import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

public class AnnounceDetailActivity extends BaseActivity<AnnounceDetailPresenter, AnnounceDetailModel> implements AnnounceDetailContract.View {

    private WebView mWeb;
    private String noticeId = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        noticeId = getIntent().getStringExtra("noticeId");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_announce_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("公告详情");

        mPresenter.getNoticeDetail(noticeId, PreferencesUtils.getString(mContext, Constants.SHOP_ID));
    }

    /**
     * 初始化webview
     * @param content
     */
    private void initWvSetting(String content) {

        mWeb = (WebView) findViewById(R.id.webView);
        WebSettings settings = mWeb.getSettings();
        //设置允许和js交互
        settings.setJavaScriptEnabled(true);

        if(!TextUtils.isEmpty(content)){
            mWeb.loadDataWithBaseURL(null,content, "text/html" , "utf-8", null);
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
    public void showDetail(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData().getObject()){
                        if(null!=baseBeanResult.getData().getObject().getTitle()){
                            initWvSetting(baseBeanResult.getData().getObject().getContent());
                            mRxManager.post("notice", "");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
