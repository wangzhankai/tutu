package com.superpeer.tutuyoudian.activity.driver.record;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.NormalRefreshViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.adapter.WithDrawRecordAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

public class WithDrawRecordActivity extends BaseActivity<WithDrawRecordPresenter, WithDrawRecordModel> implements WithDrawRecordContract.View, BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {

    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private WithDrawRecordAdapter adapter;

    private int PAGE = 1;
    private BaseBeanResult result;

    @Override
    public int getLayoutId() {
        return R.layout.activity_with_draw_record;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("提现记录");

        initRecyclerView();

        mPresenter.queryWithdrawRecord(PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE+"", "10");
    }

    private void initRecyclerView() {
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new WithDrawRecordAdapter(R.layout.item_withdraw_record, null);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);

        //设置适配器可以上拉加载
        adapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
        refresh.setDelegate(this);
        refresh.setRefreshViewHolder(new NormalRefreshViewHolder(mContext, true));

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
        try {
            if (null != baseBeanResult) {
                result = baseBeanResult;
                if ("1".equals(baseBeanResult.getCode())) {
                    if (null != baseBeanResult.getData().getList() && baseBeanResult.getData().getList().size() > 0) {
                        if (PAGE == 1) {
                            adapter.setNewData(baseBeanResult.getData().getList());
                        } else {
                            adapter.addData(baseBeanResult.getData().getList());
                        }
                    } else {
                        adapter.getData().clear();
                        adapter.notifyDataSetChanged();
                    }
                    adapter.loadComplete();
                    refresh.endLoadingMore();
                    refresh.endRefreshing();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        PAGE = 1;
        mPresenter.queryWithdrawRecord(PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE+"", "10");
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (result != null) {
            if(null!=result.getData()&&null!=result.getData().getTotal()) {
                if (PAGE + 1 <= (Integer.parseInt(result.getData().getTotal())%10>0?Integer.parseInt(result.getData().getTotal())/10+1:Integer.parseInt(result.getData().getTotal())/10)) {
                    PAGE++;
                    mPresenter.queryWithdrawRecord(PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE+"", "10");
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
