package com.superpeer.tutuyoudian.activity.driver.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.yzq.zxinglibrary.encode.CodeCreator;

public class QrCodeActivity extends BaseActivity {

    private ImageView ivQrCode;
    private TextView tvUserName;
    private TextView tvPhone;
    private TextView tvIdentify;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qr_code;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setHeadTitle("跑腿二维码");
        //3、获取屏幕的默认分辨率
        DisplayMetrics dm = getResources().getDisplayMetrics();

        ivQrCode = (ImageView) findViewById(R.id.ivQrCode);
        try {
            /*
             * contentEtString：字符串内容
             * w：图片的宽
             * h：图片的高
             * logo：不需要logo的话直接传null
             * */

            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            Bitmap qrCode = CodeCreator.createQRCode(PreferencesUtils.getString(mContext, Constants.SHOP_ID), dm.widthPixels/2, dm.widthPixels/2, logo);
            ivQrCode.setImageBitmap(qrCode);
        } catch (Exception e) {
            e.printStackTrace();
        }


        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvIdentify = (TextView) findViewById(R.id.tvIdentify);

        BaseObject bean = getUserInfo();

        if(null!=bean){
            if(null!=bean.getUserName()){
                tvUserName.setText(bean.getUserName());
            }
            if(null!=bean.getPhone()){
                tvPhone.setText(bean.getPhone());
            }
            if(null!=bean.getIdentityCard()){
                tvIdentify.setText(bean.getIdentityCard());
            }
        }
    }
}
