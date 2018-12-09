package com.superpeer.base_libs.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.R;
import com.superpeer.base_libs.baserx.RxManager;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.TUtil;
import com.superpeer.base_libs.utils.ToastUitl;
import com.superpeer.base_libs.view.LoadingDialog;

/**
 * Created by wangzhankai on 2018/2/22.
 */

public abstract  class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    protected View rootView;
    public T mPresenter;
    public E mModel;
    public RxManager mRxManager;
    private ViewStub mViewStub;
    private TextView mTvTitle;
    protected ImageView mIvLeft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        mRxManager=new RxManager();
        mPresenter = TUtil.getT(this, 0);
        mModel= TUtil.getT(this,1);
        if(mPresenter!=null){
            mPresenter.mContext=this.getActivity();
        }

        if(hasHeadTitle()){
            mTvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            mIvLeft = (ImageView) rootView.findViewById(R.id.ivLeft);
            mViewStub = (ViewStub) rootView.findViewById(R.id.viewStub);
        }
        initPresenter();
        initView(rootView);
        return rootView;
    }

    protected void setHeadTitle(String title) {
        if (hasHeadTitle()) {
            mTvTitle.setText(title);
        }
    }

    /**
     * 是否显示标题栏
     * @return
     */
    protected boolean hasHeadTitle() {
        return true;
    }

    /**
     * 设置标题栏右侧文字
     * @param text
     * @return
     */
    protected TextView setToolBarViewStubText(String text) {
        if (hasHeadTitle()) {
            mViewStub.setLayoutResource(R.layout.toolbar_text);
            TextView mViewStubToolbarText = (TextView) mViewStub.inflate();
            mViewStubToolbarText.setText(text);
            return mViewStubToolbarText;
        }

        return null;
    }

    /**
     * 设置标题栏右侧图标
     * @param drawableRes
     * @return
     */
    protected ImageView setToolBarViewStubImageRes(@DrawableRes int drawableRes) {
        if (hasHeadTitle()) {
            mViewStub.setLayoutResource(R.layout.toolbar_img);
            ImageView mViewStubToolbarImage = (ImageView) mViewStub.inflate();
            mViewStubToolbarImage.setImageResource(drawableRes);
            return mViewStubToolbarImage;
        }
        return null;
    }

    //获取布局文件
    protected abstract int getLayoutResource();
    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();
    //初始化view
    protected abstract void initView(View rootView);


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
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }



    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(getActivity(), msg, true);
    }

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }


    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(getActivity(),text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUitl.showShort(getActivity(),resId);
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUitl.showLong(getActivity(),resId);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUitl.showLong(getActivity(),text);
    }



//    /**
//     * 网络访问错误提醒
//     */
//    public void showNetErrorTip() {
//        ToastUitl.showToastWithImg(getText(R.string.net_error).toString(),R.drawable.ic_wifi_off);
//    }
//
//    public void showNetErrorTip(String error) {
//        ToastUitl.showToastWithImg(error,R.drawable.ic_wifi_off);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // ButterKnife.unbind(this);
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
