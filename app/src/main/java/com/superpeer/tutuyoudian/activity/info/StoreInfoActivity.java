package com.superpeer.tutuyoudian.activity.info;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nanchen.compresshelper.CompressHelper;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.LoadingDialog;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.storeuse.StoreUseActivity;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.ImageDownLoadCallBack;
import com.superpeer.tutuyoudian.utils.DateUtils;
import com.superpeer.tutuyoudian.utils.DownLoadImgUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class StoreInfoActivity extends BaseActivity<StoreInfoPresenter, StoreInfoModel> implements StoreInfoContract.View {

    private int maxNum = 1;
    private ArrayList<String> mSelectPath;
    //启动Activity的请求码
    private static final int REQUEST_IMAGE = 101;
    private TextView tvNext;
    private ImageView ivIdentifyPhoto;
    private ImageView ivBusinessLicence;
    private ImageView ivAllow;
    private int type;
    private String image1 = "";
    private String image2 = "";
    private String image3 = "";
    private String flag = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        flag = getIntent().getStringExtra("type");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_store_info;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("店铺入驻");

        tvNext = (TextView) findViewById(R.id.tvNext);
        ivIdentifyPhoto = (ImageView) findViewById(R.id.ivIdentifyPhoto);
        ivBusinessLicence = (ImageView) findViewById(R.id.ivBusinessLicence);
        ivAllow = (ImageView) findViewById(R.id.ivAllow);

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                if (TextUtils.isEmpty(image1)) {
                    showShortToast("请上传身份证");
                    return;
                }
                if (TextUtils.isEmpty(image2)) {
                    showShortToast("请上传营业执照");
                    return;
                }
                if (TextUtils.isEmpty(image3)) {
                    showNormalDialog();
                } else {
                    list.add(image1);
                    list.add(image2);
                    list.add(image3);
                    mPresenter.upload(PreferencesUtils.getString(mContext, Constants.SHOP_ID), list);
                }
            }
        });

        BaseObject userInfo = getUserInfo();

        if(null!=userInfo){
            initData(userInfo);
        }

        initListener();
    }

    private void showNormalDialog(){
        //创建dialog构造器
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        //设置title
        normalDialog.setTitle("提示");
        //设置icon
        normalDialog.setIcon(R.mipmap.ic_launcher);
        //设置内容
        normalDialog.setMessage("您还未上传食品许可证");
        //设置按钮
        normalDialog.setPositiveButton("继续上传"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<String> list = new ArrayList<>();
                        list.add(image1);
                        list.add(image2);

//                        list.add(image3);
                        mPresenter.upload(PreferencesUtils.getString(mContext, Constants.SHOP_ID), list);
                        dialog.dismiss();
                    }
                });
        normalDialog.setNegativeButton("暂不上传"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
        });
        //创建并显示
        normalDialog.create().show();
    }

    /**
     * 初始化数据
     * @param userInfo
     */
    private void initData(BaseObject userInfo) {
        if(null!=userInfo.getIdCardImg()){
            try {
                String fileName = DateUtils.getUUIDFileName()+".png";
                String filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/tutu/"+fileName;
                DownLoadImgUtils.savePicture(mContext, fileName, Url.IP+userInfo.getIdCardImg());
                image1 = filePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Glide.with(mContext).load(Url.IP+userInfo.getIdCardImg()).centerCrop().into(ivIdentifyPhoto);
        }
        if(null!=userInfo.getBusinessLicense()){
            try {
                String fileName = DateUtils.getUUIDFileName()+".png";
                String filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/tutu/"+fileName;
                DownLoadImgUtils.savePicture(mContext, fileName, Url.IP+userInfo.getBusinessLicense());
                image2 = filePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Glide.with(mContext).load(Url.IP+userInfo.getBusinessLicense()).centerCrop().into(ivBusinessLicence);
        }
        if(null!=userInfo.getFoodBusinessLicense()){
            try {
                String fileName = DateUtils.getUUIDFileName()+".png";
                String filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/tutu/"+fileName;
                DownLoadImgUtils.savePicture(mContext, fileName, Url.IP+userInfo.getFoodBusinessLicense());
                image3 = filePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Glide.with(mContext).load(Url.IP+userInfo.getFoodBusinessLicense()).centerCrop().into(ivAllow);
        }
    }

    private void initListener() {
        //上传图片
        ivIdentifyPhoto.setOnClickListener(new View.OnClickListener() {
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

        //上传图片
        ivBusinessLicence.setOnClickListener(new View.OnClickListener() {
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

        //上传图片
        ivAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPermissionUtils.requestPermissionsResult((Activity) mContext, 1, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        type = 2;
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
                        Glide.with(mContext).load("file://"+mSelectPath.get(0)).centerCrop().into(ivIdentifyPhoto);
                    }else if(type == 1){
                        image2 = mSelectPath.get(0);
                        Glide.with(mContext).load("file://"+mSelectPath.get(0)).centerCrop().into(ivBusinessLicence);
                    }else if(type == 2){
                        image3 = mSelectPath.get(0);
                        Glide.with(mContext).load("file://"+mSelectPath.get(0)).centerCrop().into(ivAllow);
                    }
                }
            }
        }
    }

    @Override
    public void showLoading(String title) {
        LoadingDialog.showDialogForLoading((Activity) mContext, title, true);
    }

    @Override
    public void stopLoading() {
        LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void showErrorTip(String msg) {
        LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void showResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    PreferencesUtils.putString(mContext, Constants.USER_INFO, new Gson().toJson(baseBeanResult.getData().getObject()));
                    if("1".equals(flag)){
                        Intent intent = new Intent(mContext, StoreUseActivity.class);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }else{
                        finish();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
