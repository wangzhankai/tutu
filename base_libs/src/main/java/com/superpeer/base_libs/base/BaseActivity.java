package com.superpeer.base_libs.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.R;
import com.superpeer.base_libs.baserx.RxManager;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.StatusBarUtil;
import com.superpeer.base_libs.utils.TUtil;
import com.superpeer.base_libs.utils.ToastUitl;
import com.zhy.autolayout.AutoLayoutActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by wangzhankai on 2018/2/8.
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AutoLayoutActivity {
    public T mPresenter;
    public E mModel;
    public Context mContext;
    public RxManager mRxManager;
    protected TextView mTvTitle;
    protected ImageView mIvLeft;
    protected ViewStub mViewStub;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        setBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        if (hasHeadTitle()) {
            mTvTitle = (TextView) findViewById(R.id.tvTitle);
            mIvLeft = (ImageView) findViewById(R.id.ivLeft);
            mViewStub = (ViewStub) findViewById(R.id.viewStub);

            mIvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        /*if(hasStatus()){
            StatusBarCompat.compat(this, R.color.colorPrimary);
        }*/

        this.initPresenter();
        this.initView();

    }


    protected boolean hasStatus() {
        return true;
    }

    /**
     * 设置标题栏标题
     *
     * @param title 标题
     */
    protected void setHeadTitle(String title) {
        if (hasHeadTitle()) {
            mTvTitle.setText(title);
        }
    }

    /**
     * 设置标题栏左侧图标
     */
    protected void setLeftIcon(@DrawableRes int drawableRes) {
        if (hasHeadTitle()) {
            mIvLeft.setImageResource(drawableRes);
        }
    }

    /**
     * 默认带title
     *
     * @return
     */
    protected boolean hasHeadTitle() {
        return true;
    }

    /**
     * 设置标题栏右侧为文字
     */
    protected TextView setToolBarViewStubText(String text) {
        return setToolBarViewStubText(text, R.color.white);
    }

    /**
     * 设置标题栏右侧为文字
     */
    protected TextView setToolBarViewStubText(String text, int color) {
        if (hasHeadTitle()) {
            mViewStub.setLayoutResource(R.layout.toolbar_text);
            TextView mTvRight = (TextView) mViewStub.inflate();
            mTvRight.setTextColor(ContextCompat.getColor(this, color));
            mTvRight.setText(text);
            return mTvRight;
        }
        return null;
    }


    /**
     * 设置标题栏右侧图标
     */
    protected ImageView setToolBarViewStubImageRes(@DrawableRes int drawableRes) {
        if (hasHeadTitle()) {
            mViewStub.setLayoutResource(R.layout.toolbar_img);
            ImageView mIvRight = (ImageView) mViewStub.inflate();
            mIvRight.setImageResource(drawableRes);
            return mIvRight;
        }
        return null;
    }


    /**
     * 设置layout前配置
     */
    protected void doBeforeSetcontentView() {

        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(this, text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUitl.showShort(this, resId);
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUitl.showLong(this, resId);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUitl.showLong(this, text);
    }

    /**
     * //     * 网络访问错误提醒
     * //
     */
//    public void showNetErrorTip() {
//        ToastUitl.showToastWithImg(getText(R.string.net_error).toString(),R.drawable.ic_wifi_off);
//    }
//    public void showNetErrorTip(String error) {
//        ToastUitl.showToastWithImg(error,R.drawable.ic_wifi_off);
//    }
    @Override
    protected void onResume() {
        super.onResume();
        //防统计之类的
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void setBarColor(int colorPrimary) {
        StatusBarUtil.setColor(this, colorPrimary);
    }
}
