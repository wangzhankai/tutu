package com.superpeer.tutuyoudian.activity.order;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.adapter.MFragmentStatePagerAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.frament.normalorder.NormalOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity<OrderPresenter, OrderModel> implements OrderContract.View {

    private XTabLayout mTab;
    private ViewPager viewPager;
    private String[] titles;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("普通订单");

        mTab = (XTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        mTab.addTab(mTab.newTab(), true);
        mTab.addTab(mTab.newTab(), false);
        mTab.addTab(mTab.newTab(), false);
        mTab.addTab(mTab.newTab(), false);
        mTab.addTab(mTab.newTab(), false);
        mTab.addTab(mTab.newTab(), false);
        mTab.addTab(mTab.newTab(), false);
        fragments.add(NormalOrderFragment.newInstance(""));
        fragments.add(NormalOrderFragment.newInstance("1"));
        fragments.add(NormalOrderFragment.newInstance("3"));
        fragments.add(NormalOrderFragment.newInstance("4"));
        fragments.add(NormalOrderFragment.newInstance("TYPE"));
        fragments.add(NormalOrderFragment.newInstance("5"));
        fragments.add(NormalOrderFragment.newInstance("6"));
        titles = new String[]{"全部", "待付款", "待接单", "待提货", "配送中", "已完成", "已取消"};

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
}
