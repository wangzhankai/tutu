package com.superpeer.tutuyoudian.activity.verify;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.verify.successorfail.VerifySuccessOrFailActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.widget.PasswordView;

import java.io.Serializable;

import cloudist.cc.library.view.PasswordInputView;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;

public class VerifyActivity extends BaseActivity<VerifyPresenter, VerifyModel> implements VerifyContract.View{

    private TextView tvVerify;
    private PasswordView password_inputview;
    private ImageView ivScan;

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("提货验证");

        ivScan = (ImageView) findViewById(R.id.ivScan);
        tvVerify = (TextView) findViewById(R.id.tvVerify);
        password_inputview = (PasswordView) findViewById(R.id.password_inputview);
        password_inputview.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.colorAccent, R.color.colorAccent,20);
        password_inputview.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        password_inputview.setOnTextFinishListener(new PasswordView.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
            }
        });

//        password_inputview.bindKeyBoard(getSupportFragmentManager(), "");

        initListener();
    }

    private void initListener() {
        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = password_inputview.getPwdText().toString().trim();
                if(TextUtils.isEmpty(password)){
                    showShortToast("请输入或扫描取货码");
                    return;
                }
                mPresenter.checkPickGoods(password, PreferencesUtils.getString(mContext, Constants.SHOP_ID));
            }
        });

        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQRCode();
            }
        });
    }

    private void initQRCode() {
        try {
            QrConfig qrConfig = new QrConfig.Builder()
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
                    .setTitleText("提货验证")//设置Tilte文字
                    .setTitleBackgroudColor(ContextCompat.getColor(mContext, R.color.colorPrimary))//设置状态栏颜色
                    .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                    .create();
            QrManager.getInstance().init(qrConfig).startScan(VerifyActivity.this, new QrManager.OnScanResultCallback() {
                @Override
                public void onScanSuccess(String result) {
                    mPresenter.checkPickGoods(result, PreferencesUtils.getString(mContext, Constants.SHOP_ID));
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
    public void showResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    Intent intent = new Intent(mContext, VerifySuccessOrFailActivity.class);
                    intent.putExtra("list", (Serializable) baseBeanResult.getData().getList());
                    intent.putExtra("orderType", baseBeanResult.getData().getObject().getOrderType());
                    intent.putExtra("type", "1");
                    startActivity(intent);
                    mRxManager.post("order", "");
                    mRxManager.post("collage", "");
                }else{
                    Intent intent = new Intent(mContext, VerifySuccessOrFailActivity.class);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
