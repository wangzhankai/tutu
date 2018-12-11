package com.superpeer.tutuyoudian.frament.driver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.base.BaseFragment;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.NormalRefreshViewHolder;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.driver.orderdetail.DriverOrderDetailActivity;
import com.superpeer.tutuyoudian.adapter.DriverOrderAdapter;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnCancelListener;
import com.superpeer.tutuyoudian.listener.OnCompleteListener;
import com.superpeer.tutuyoudian.listener.OnGetListener;
import com.superpeer.tutuyoudian.listener.OnItemListener;

import rx.functions.Action1;

public class DriverOrderFragment extends BaseFragment<DriverOrderPresenter, DriverOrderModel> implements DriverOrderContract.View, BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {

    private static final String TYPE = "TYPE";
    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private DriverOrderAdapter adapter;
    private int delPos;
    private int PAGE = 1;
    private BaseBeanResult result;
    private boolean isViewCreate;
    private String type = "";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreate = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            if(isViewCreate){
                PAGE = 1;
                mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), type, PAGE+"", "10");
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_driver_order;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView(View rootView) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString(TYPE);
        }

        initRecyclerView(rootView);

        initRxBus();
        mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), type, PAGE+"", "10");
    }

    private void initRxBus() {
        mRxManager.on("drivermain", new Action1<String>() {
            @Override
            public void call(String s) {
                PAGE = 1;
                mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), type, PAGE+"", "10");
            }
        });
    }

    private void initRecyclerView(View rootView) {
        rvContent = (RecyclerView) rootView.findViewById(R.id.rv_content);
        refresh = (RefreshLayout) rootView.findViewById(R.id.refresh);
        adapter = new DriverOrderAdapter(R.layout.item_driver_main, null);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);
        //设置适配器可以上拉加载
        adapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
        refresh.setDelegate(this);
        refresh.setRefreshViewHolder(new NormalRefreshViewHolder(getActivity(), true));

        adapter.setOnItemListener(new OnItemListener() {
            @Override
            public void onItem(int position) {
                Intent intent = new Intent(getActivity(), DriverOrderDetailActivity.class);
                intent.putExtra("orderId", ((BaseList)adapter.getItem(position)).getOrderId());
                startActivity(intent);
            }
        });
        adapter.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(int position) {
                showGiveUpDialog(position);
            }
        });
        adapter.setOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(int position) {
                mPresenter.confirmOrder(((BaseList)adapter.getItem(position)).getOrderId());
            }
        });
        adapter.setOnGetListener(new OnGetListener() {
            @Override
            public void onGet(int position) {
                mPresenter.grabOrder(((BaseList)adapter.getItem(position)).getOrderId(), PreferencesUtils.getString(getActivity(), Constants.SHOP_ID));
            }
        });
    }

    private void showGiveUpDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_order_giveup, null);

        ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
        TextView tvSure = (TextView) view.findViewById(R.id.tvSure);
        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);

        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(view);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //放弃配送
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delPos = position;
                mPresenter.giveUpOrder(((BaseList)adapter.getItem(position)).getOrderId());
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

    public static Fragment newInstance(String type) {
        DriverOrderFragment fragment = new DriverOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        PAGE = 1;
        mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), type, PAGE+"", "10");
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (result != null) {
            if (null != result.getData() && null != result.getData().getTotal()) {
                if (PAGE + 1 <= (Integer.parseInt(result.getData().getTotal())%10>0?Integer.parseInt(result.getData().getTotal())/10+1:Integer.parseInt(result.getData().getTotal())/10)) {
                    PAGE++;
                    mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), type, PAGE+"", "10");
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void showResult(BaseBeanResult baseBeanResult) {
        try {
            if (null != baseBeanResult) {
                result = baseBeanResult;
                if ("1".equals(baseBeanResult.getCode())) {
                    if(null!=baseBeanResult.getData()) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showGradResult(BaseBeanResult baseBeanResult) {
        try{
            PAGE = 1;
            mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), type, PAGE+"", "10");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showGiveUpResult(BaseBeanResult baseBeanResult) {
        try{
            adapter.getData().remove(delPos);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showConfirmResult(BaseBeanResult baseBeanResult) {
        try{
            PAGE = 1;
            mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), type, PAGE+"", "10");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
