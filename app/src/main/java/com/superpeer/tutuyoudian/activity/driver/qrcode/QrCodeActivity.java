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

import cn.bertsir.zbar.utils.QRUtils;

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
        Bitmap qrCode = QRUtils.getInstance().createQRCodeAddLogo(PreferencesUtils.getString(mContext, Constants.SHOP_ID),
                dm.widthPixels/2,
                dm.heightPixels/2,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        ivQrCode.setImageBitmap(qrCode);

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
