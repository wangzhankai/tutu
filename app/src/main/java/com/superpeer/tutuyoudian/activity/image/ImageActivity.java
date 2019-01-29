package com.superpeer.tutuyoudian.activity.image;

import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.base.BaseActivity;

import java.util.ArrayList;

public class ImageActivity extends BaseActivity {
    private String url = "";

    @Override
    protected boolean hasHeadTitle() {
        return false;
    }

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        url = getIntent().getStringExtra("url");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ImageView ivImg = (ImageView) findViewById(R.id.ivImg);
        Glide.with(mContext).load(Url.IP+url).fitCenter().into(ivImg);

        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
