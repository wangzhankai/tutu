package com.superpeer.tutuyoudian.activity.collageorder;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.adapter.MFragmentStatePagerAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.frament.collageorder.CollageOrderFragment;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class CollageOrderActivity extends BaseActivity<CollageOrderPresenter, CollageOrderModel> implements CollageOrderContract.View {

    private XTabLayout mTab;
    private ViewPager viewPager;
    private String[] titles;
    private List<Fragment> fragments = new ArrayList<>();
    private int type = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_collage_order;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("拼团订单");

        mTab = (XTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        mPresenter.getOrderList(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "1", "10", "", "");

        mRxManager.on("collageChange", new Action1<String>() {
            @Override
            public void call(String s) {
                type = 1;
                mPresenter.getOrderList(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "1", "10", "", "");
            }
        });
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
        String title1= "全部", title2 = "待成团", title3 = "待提货", title4 = "已完成",
                title5 = "已取消";
        try{
            if("1".equals(baseBeanResult.getCode())){
                if(null!=baseBeanResult.getData()){
                    if(null!=baseBeanResult.getData().getObject()){
                        BaseObject obj = baseBeanResult.getData().getObject();
                        if(null!=obj.getTotalNum())
                            title1 = "全部("+obj.getTotalNum()+")";
                        if(null!=obj.getThreeNum())
                            title2 = "待成团("+obj.getThreeNum()+")";
                        if(null!=obj.getFourSelfNum())
                            title3 = "待提货("+obj.getFourSelfNum()+")";
                        if(null!=obj.getFiveNum())
                            title4 = "已完成("+obj.getFiveNum()+")";
                        if(null!=obj.getSixNum())
                            title5 = "配送中("+obj.getSixNum()+")";
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(type == 0) {
            mTab.addTab(mTab.newTab(), true);
            mTab.addTab(mTab.newTab(), false);
            mTab.addTab(mTab.newTab(), false);
            mTab.addTab(mTab.newTab(), false);
            mTab.addTab(mTab.newTab(), false);
            fragments.add(CollageOrderFragment.newInstance(""));
            fragments.add(CollageOrderFragment.newInstance("3"));
            fragments.add(CollageOrderFragment.newInstance("4"));
//        fragments.add(CollageOrderFragment.newInstance("TYPE"));
            fragments.add(CollageOrderFragment.newInstance("5"));
            fragments.add(CollageOrderFragment.newInstance("6,7"));
            titles = new String[]{title1, title2, title3, /*"送货中",*/ title4, title5};

            viewPager.setAdapter(new MFragmentStatePagerAdapter(getSupportFragmentManager(), fragments, titles));
            viewPager.setOffscreenPageLimit(fragments.size());
            mTab.setupWithViewPager(viewPager);
            mTab.setTabMode(XTabLayout.MODE_SCROLLABLE);
        }
    }
}
