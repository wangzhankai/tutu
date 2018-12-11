package com.superpeer.tutuyoudian.frament.normalorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.superpeer.base_libs.base.BaseFragment;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.NormalRefreshViewHolder;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.order.detail.OrderDetailActivity;
import com.superpeer.tutuyoudian.activity.verify.VerifyActivity;
import com.superpeer.tutuyoudian.adapter.NormalOrderAdapter;
import com.superpeer.tutuyoudian.adapter.VerifyAdapter;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnCancelListener;
import com.superpeer.tutuyoudian.listener.OnCompleteListener;
import com.superpeer.tutuyoudian.listener.OnDeleteListener;
import com.superpeer.tutuyoudian.listener.OnGetListener;
import com.superpeer.tutuyoudian.listener.OnItemListener;
import com.superpeer.tutuyoudian.listener.OnVerifyListener;

import rx.functions.Action1;

/**
 * Created by Administrator on 2018/9/6 0006.
 */

public class NormalOrderFragment extends BaseFragment<NormalOrderPresenter, NormalOrderModel> implements NormalOrderContract.View, RefreshLayout.RefreshLayoutDelegate, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TYPE = "TYPE";
    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private NormalOrderAdapter adapter;
    private String type = "";

    private int PAGE = 1;
    private BaseBeanResult result;
    private int cancelPos;
    private int getPos;
    private int delPos;
    private int completePos;
    private boolean isViewCreate;

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
                mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), PAGE+"", "10", "TYPE".equals(type)?"4":type, "TYPE".equals(type)?"1":("4".equals(type)?"2":""));
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        try{
            if(!hidden){
                PAGE = 1;
                mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), PAGE+"", "10", "TYPE".equals(type)?"4":type, "TYPE".equals(type)?"1":("4".equals(type)?"2":""));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    @Override
    protected int getLayoutResource() {
        return R.layout.include_refresh;
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

        mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), PAGE+"", "10", "TYPE".equals(type)?"4":type, "TYPE".equals(type)?"1":("4".equals(type)?"2":""));
    }

    private void initRxBus() {
        mRxManager.on("order", new Action1<String>() {
            @Override
            public void call(String s) {
                PAGE = 1;
                mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), PAGE+"", "10", "TYPE".equals(type)?"4":type, "TYPE".equals(type)?"1":("4".equals(type)?"2":""));
            }
        });
    }

    private void initRecyclerView(View rootView) {
        rvContent = (RecyclerView) rootView.findViewById(R.id.rv_content);
        refresh = (RefreshLayout) rootView.findViewById(R.id.refresh);
        adapter = new NormalOrderAdapter(R.layout.item_normal_order, null);
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
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("orderId", ((BaseList)adapter.getItem(position)).getOrderId());
                startActivity(intent);
            }
        });

        //取消订单
        adapter.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(int position) {
                cancelPos = position;
                mPresenter.cancelOrder(((BaseList)adapter.getItem(position)).getOrderId());
            }
        });
        //接单
        adapter.setOnGetListener(new OnGetListener() {
            @Override
            public void onGet(int position) {
                getPos = position;
                mPresenter.getOrder(((BaseList)adapter.getItem(position)).getOrderId());
            }
        });

        //提货验证
        adapter.setOnVerifyListener(new OnVerifyListener() {
            @Override
            public void onVerify(int position) {
                startActivity(VerifyActivity.class);
            }
        });

        //删除订单
        adapter.setOnDeleteListener(new OnDeleteListener() {
            @Override
            public void onDeleteListener(int position) {
                delPos = position;
                mPresenter.delOrder(((BaseList)adapter.getItem(position)).getOrderId());
            }
        });
        //订单送达
        adapter.setOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(int position) {
                completePos = position;
                mPresenter.confirmOrder(((BaseList)adapter.getItem(position)).getOrderId());
            }
        });

    }

    public static Fragment newInstance(String type) {
        NormalOrderFragment fragment = new NormalOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
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
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        PAGE = 1;
        mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), PAGE+"", "10", "TYPE".equals(type)?"4":type, "TYPE".equals(type)?"1":("4".equals(type)?"2":""));
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (result != null) {
            if(null!=result.getData()&&null!=result.getData().getTotal()) {
                if (PAGE + 1 <= (Integer.parseInt(result.getData().getTotal())%10>0?Integer.parseInt(result.getData().getTotal())/10+1:Integer.parseInt(result.getData().getTotal())/10)) {
                    PAGE++;
                    mPresenter.getOrderList(PreferencesUtils.getString(getActivity(), Constants.SHOP_ID), PAGE+"", "10", "TYPE".equals(type)?"4":type, "TYPE".equals(type)?"1":("4".equals(type)?"2":""));
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
    public void onLoadMoreRequested() {

    }

    @Override
    public void showList(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                result = baseBeanResult;
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                        if(PAGE == 1){
                            adapter.setNewData(baseBeanResult.getData().getList());
                        }else{
                            adapter.addData(baseBeanResult.getData().getList());
                        }
                    }else{
                        adapter.getData().clear();
                        adapter.notifyDataSetChanged();
                    }
                    adapter.loadComplete();
                    refresh.endRefreshing();
                    refresh.endLoadingMore();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showCancelResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    adapter.getData().remove(cancelPos);
                    adapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showGetResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    adapter.getData().remove(getPos);
                    adapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showDeleteResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    adapter.getData().remove(delPos);
                    adapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showConfirmResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    adapter.getData().remove(completePos);
                    adapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
