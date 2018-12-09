package com.superpeer.tutuyoudian.activity.paypwd;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;

public class SuccessActivity extends BaseActivity {

    private ImageView ivStatus;
    private TextView tvStatus;
    private String desc = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        desc = getIntent().getStringExtra("desc");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_success;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {

        ivStatus = (ImageView) findViewById(R.id.ivStatus);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        if(!TextUtils.isEmpty(desc)){
            setHeadTitle(desc);
            tvStatus.setText(desc);
        }

    }
}
