package com.superpeer.tutuyoudian.activity.collageorder;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.adapter.MFragmentStatePagerAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.frament.collageorder.CollageOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class CollageOrderActivity extends BaseActivity<CollageOrderPresenter, CollageOrderModel> implements CollageOrderContract.View {

    private XTabLayout mTab;
    private ViewPager viewPager;
    private String[] titles;
    private List<Fragment> fragments = new ArrayList<>();

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
        fragments.add(CollageOrderFragment.newInstance("6"));
        titles = new String[]{"全部", "待成团", "待提货", /*"送货中",*/ "已完成", "已取消"};

        viewPager.setAdapter(new MFragmentStatePagerAdapter(getSupportFragmentManager(), fragments, titles));
        viewPager.setOffscreenPageLimit(fragments.size());
        mTab.setupWithViewPager(viewPager);
        mTab.setTabMode(XTabLayout.MODE_SCROLLABLE);
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

    }
}
