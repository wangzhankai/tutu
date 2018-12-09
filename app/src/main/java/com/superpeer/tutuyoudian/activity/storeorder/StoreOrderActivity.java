package com.superpeer.tutuyoudian.activity.storeorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.adapter.DriverListAdapter;
import com.superpeer.tutuyoudian.adapter.StoreOrderAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;

public class StoreOrderActivity extends BaseActivity<StoreOrderPresenter, StroreOrderModel> implements StoreOrderContract.View{

    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private StoreOrderAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_store_order;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("配送订单");

        initRecyclerView();
    }

    private void initRecyclerView() {
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new StoreOrderAdapter(R.layout.item_store_order, null);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);

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
