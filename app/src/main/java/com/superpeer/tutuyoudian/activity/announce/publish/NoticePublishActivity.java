package com.superpeer.tutuyoudian.activity.announce.publish;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

public class NoticePublishActivity extends BaseActivity<NoticePublishPresenter, NoticePublishModel> implements NoticePublishContract.View {

    private EditText etContent;
    private TextView tvSure;

    @Override
    public int getLayoutId() {
        return R.layout.activity_notice_publish;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("店铺公告设置");

        etContent = (EditText) findViewById(R.id.etContent);
        tvSure = (TextView) findViewById(R.id.tvSure);

        if(null!=PreferencesUtils.getString(mContext, Constants.SHOP_NOTICE)){
            etContent.setText(PreferencesUtils.getString(mContext, Constants.SHOP_NOTICE));
        }

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString().trim();
                if(TextUtils.isEmpty(content)){
                    showShortToast("请输入公告内容");
                    return;
                }
                mPresenter.publish(PreferencesUtils.getString(mContext, Constants.SHOP_ID), content);
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

                if("1".equals(baseBeanResult.getCode())) {
                    finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
