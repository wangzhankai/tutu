package com.superpeer.tutuyoudian.activity.order;

import android.support.design.widget.TabLayout;
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
import com.superpeer.tutuyoudian.frament.normalorder.NormalOrderFragment;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class OrderActivity extends BaseActivity<OrderPresenter, OrderModel> implements OrderContract.View {

    private XTabLayout mTab;
    private ViewPager viewPager;
    private String[] titles;
    private List<Fragment> fragments = new ArrayList<>();
    private BaseObject obj;
    private int type = 0;

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

        mPresenter.getOrderList(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "1", "10", "", "");

        mRxManager.on("change", new Action1<String>() {
            @Override
            public void call(String s) {
                type = 1;
                mPresenter.getOrderList(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "1", "10", "", "");
                /*switch (s){
                    case "":
                        mTab.getTabAt(0).setText(obj.getTotalNum());
                        break;
                    case "1":
                        obj.setOneNum(Integer.parseInt(obj.getOneNum())+1+"");
                        mTab.getTabAt(1).setText(obj.getOneNum());
                        break;
                    case "3":
                        obj.setThreeNum(Integer.parseInt(obj.getThreeNum())-1+"");
                        mTab.getTabAt(2).setText(obj.getThreeNum());
                        break;
                    case "4":
                        obj.setFourSelfNum(Integer.parseInt(obj.getFourSelfNum())-1+"");
                        mTab.getTabAt(3).setText(obj.getFourSelfNum());
                        break;
                    case "TYPE":
                        obj.setFourOtherNum(Integer.parseInt(obj.getFourOtherNum())-1+"");
                        mTab.getTabAt(4).setText(obj.getFourOtherNum());
                        break;
                    case "5":
                        break;
                    case "6,7":
                        obj.setSixNum(Integer.parseInt(obj.getSixNum())+1+"");
                        mTab.getTabAt(6).setText(obj.getSixNum());
                        break;
                }*/
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
    public void showList(BaseBeanResult baseBeanResult) {
        String title1 = "全部", title2 = "待付款", title3 = "待接单", title4 = "待提货",
                title5 = "配送中", title6 = "已完成", title7 = "已取消";
        try {
            if ("1".equals(baseBeanResult.getCode())) {
                if (null != baseBeanResult.getData()) {
                    if (null != baseBeanResult.getData().getObject()) {
                        obj = baseBeanResult.getData().getObject();
                        if (null != obj.getTotalNum())
                            title1 = "全部(" + obj.getTotalNum() + ")";
                        if (null != obj.getOneNum())
                            title2 = "待付款(" + obj.getOneNum() + ")";
                        if (null != obj.getThreeNum())
                            title3 = "待接单(" + obj.getThreeNum() + ")";
                        if (null != obj.getFourSelfNum())
                            title4 = "待提货(" + obj.getFourSelfNum() + ")";
                        if (null != obj.getFourOtherNum())
                            title5 = "配送中(" + obj.getFourOtherNum() + ")";
                        if (null != obj.getFiveNum())
                            title6 = "已完成(" + obj.getFiveNum() + ")";
                        if (null != obj.getSixNum())
                            title7 = "已取消(" + obj.getSixNum() + ")";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(type==0) {
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
            fragments.add(NormalOrderFragment.newInstance("6,7"));
            titles = new String[]{title1, title2, title3, title4, title5, title6, title7};

            viewPager.setAdapter(new MFragmentStatePagerAdapter(getSupportFragmentManager(), fragments, titles));
            viewPager.setOffscreenPageLimit(fragments.size());
            mTab.setupWithViewPager(viewPager);
            mTab.setTabMode(XTabLayout.MODE_SCROLLABLE);
        }
    }
}
