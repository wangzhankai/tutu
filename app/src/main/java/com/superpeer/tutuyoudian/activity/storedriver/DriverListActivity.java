package com.superpeer.tutuyoudian.activity.storedriver;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.adddriver.AddDriverActivity;
import com.superpeer.tutuyoudian.activity.main.MainActivity;
import com.superpeer.tutuyoudian.adapter.DriverListAdapter;
import com.superpeer.tutuyoudian.adapter.PayTypeAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import rx.functions.Action1;

public class DriverListActivity extends BaseActivity<DriverListPresenter, DriverListModel> implements DriverListContract.View{

    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private DriverListAdapter adapter;
    private static final int REQUEST_CODE_SCAN = 888;

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("店铺跑腿");

        setToolBarViewStubText("添加跑腿").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQRCode();
            }
        });

        initRecyclerView();

        mPresenter.queryRunners(PreferencesUtils.getString(mContext, Constants.SHOP_ID));

        initRxBus();
    }

    private void initRxBus() {
        mRxManager.on("driverList", new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.queryRunners(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
            }
        });
    }

    private void initRecyclerView() {
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new DriverListAdapter(R.layout.item_driver, null);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);

        rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, AddDriverActivity.class);
                intent.putExtra("result", ((BaseList)adapter.getItem(position)).getId());
                intent.putExtra("type", "1");
                intent.putExtra("runnerType", ((BaseList)adapter.getItem(position)).getRunnerType());
                startActivity(intent);
            }
        });

    }

    private void initQRCode() {
        try {
            /*QrConfig qrConfig = new QrConfig.Builder()
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
                    .setTitleText("添加骑士")//设置Title文字
                    .setTitleBackgroudColor(ContextCompat.getColor(mContext, R.color.colorPrimary))//设置状态栏颜色
                    .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                    .create();
            QrManager.getInstance().init(qrConfig).startScan(DriverListActivity.this, new QrManager.OnScanResultCallback() {
                @Override
                public void onScanSuccess(String result) {
                    Intent intent = new Intent(mContext, AddDriverActivity.class);
                    intent.putExtra("result", result);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                }
            });*/
            MPermissionUtils.requestPermissionsResult((Activity) mContext, 1, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new MPermissionUtils.OnPermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Intent intent = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                }

                @Override
                public void onPermissionDenied() {
                    MPermissionUtils.showTipsDialog(mContext);
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
    public void showRunnersResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData()) {
                        if (null != baseBeanResult.getData().getList() && baseBeanResult.getData().getList().size() > 0) {
                            adapter.setNewData(baseBeanResult.getData().getList());
                        }
                    }
                }
                adapter.loadComplete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            // 扫描二维码/条码回传
            if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
                if (data != null) {
                    String content = data.getStringExtra(Constant.CODED_CONTENT);
                    Intent intent = new Intent(mContext, AddDriverActivity.class);
                    intent.putExtra("result", content);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
