package com.superpeer.tutuyoudian.activity.driver.identify;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nanchen.compresshelper.CompressHelper;
import com.superpeer.base_libs.utils.ConstantsUtils;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.utils.DateUtils;
import com.superpeer.tutuyoudian.utils.DownLoadImgUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class DriverIdentifyActivity extends BaseActivity<DriverIdentifyPresenter, DriverIdentifyModel> implements DriverIdentifyContract.View {

    private int maxNum = 1;
    private ArrayList<String> mSelectPath;
    //启动Activity的请求码
    private static final int REQUEST_IMAGE = 101;
    private ImageView ivPhotoFont;
    private ImageView ivPhotoBackground;
    private TextView tvNext;
    private int type;
    private String image1 = "";
    private String image2 = "";
    private EditText etName;
    private EditText etIdentifyNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_identify;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("身份认证");

        ivPhotoFont = (ImageView) findViewById(R.id.ivPhotoFont);
        ivPhotoBackground = (ImageView) findViewById(R.id.ivPhotoBackground);

        tvNext = (TextView) findViewById(R.id.tvNext);

        etName = (EditText) findViewById(R.id.etName);
        etIdentifyNum = (EditText) findViewById(R.id.etIdentifyNum);

        BaseObject userInfo = getUserInfo();

        if(null!=userInfo){
            initData(userInfo);
        }

        initListener();
    }

    private void initData(BaseObject userInfo) {
        if(null!=userInfo.getUserName()){
            etName.setText(userInfo.getUserName());
        }
        if(null!=userInfo.getIdentityCard()){
            etIdentifyNum.setText(userInfo.getIdentityCard());
        }
        if(null!=userInfo.getFrontIdentity()){
            try {
                String fileName = DateUtils.getUUIDFileName()+".png";
                String filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/tutu/"+fileName;
                DownLoadImgUtils.savePicture(mContext, fileName, Url.IP+userInfo.getFrontIdentity());
                image1 = filePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Glide.with(mContext).load(Url.IP+userInfo.getFrontIdentity()).centerCrop().into(ivPhotoFont);
        }

        if(null!=userInfo.getReverseIdentity()){
            try {
                String fileName = DateUtils.getUUIDFileName()+".png";
                String filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/tutu/"+fileName;
                DownLoadImgUtils.savePicture(mContext, fileName, Url.IP+userInfo.getReverseIdentity());
                image2 = filePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Glide.with(mContext).load(Url.IP+userInfo.getReverseIdentity()).centerCrop().into(ivPhotoBackground);
        }
    }

    private void initListener() {
        //身份证正面
        ivPhotoFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPermissionUtils.requestPermissionsResult((Activity) mContext, 1, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        type = 0;
                        //选择照片
                        selectPhoto();
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(mContext);
                    }
                });
            }
        });
        //身份证背面
        ivPhotoBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPermissionUtils.requestPermissionsResult((Activity) mContext, 1, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        type = 1;
                        //选择照片
                        selectPhoto();
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(mContext);
                    }
                });
            }
        });
        //提交
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String identifyNum = etIdentifyNum.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    showShortToast("请输入您的姓名");
                    return;
                }
                if(TextUtils.isEmpty(identifyNum)){
                    showShortToast("请输入您的身份证号");
                    return;
                }
                if(!ConstantsUtils.isIdentifyNum(identifyNum)){
                    showShortToast("请输入正确的身份证号");
                    return;
                }
                if(TextUtils.isEmpty(image1)){
                    showShortToast("请上传您的身份证正面照");
                    return;
                }
                if(TextUtils.isEmpty(image2)){
                    showShortToast("请输入您的身份证反面照");
                    return;
                }
                List<String> list = new ArrayList<>();
                list.add(image1);
                list.add(image2);
                mPresenter.addInfos(PreferencesUtils.getString(mContext, Constants.SHOP_ID), list, name, identifyNum);
            }
        });
    }

    /**
     * 选择图片
     */
    private void selectPhoto() {
        Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
        //是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        //最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        //选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        //默认选择
        if(mSelectPath!=null&&mSelectPath.size()>0){
            mSelectPath.clear();
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_IMAGE){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                File newFile = CompressHelper.getDefault(mContext).compressToFile(new File(mSelectPath.get(0)));
                mSelectPath.clear();
                mSelectPath.add(newFile.getPath());
                if(mSelectPath.size()>maxNum){
                    for(int i=maxNum;i<mSelectPath.size();i++){
                        mSelectPath.remove(i);
                    }
                }
                if (mSelectPath.size() > 0) {
                    if(type ==0){
                        image1 = mSelectPath.get(0);
                        Glide.with(mContext).load("file://"+mSelectPath.get(0)).centerCrop().into(ivPhotoFont);
                    }else if(type == 1){
                        image2 = mSelectPath.get(0);
                        Glide.with(mContext).load("file://"+mSelectPath.get(0)).centerCrop().into(ivPhotoBackground);
                    }
                }
            }
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
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData().getObject()){
                        PreferencesUtils.putString(mContext, Constants.USER_INFO, new Gson().toJson(baseBeanResult.getData().getObject()));
                    }
                    finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
