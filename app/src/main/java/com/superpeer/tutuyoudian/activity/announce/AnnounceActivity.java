package com.superpeer.tutuyoudian.activity.announce;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.NormalRefreshViewHolder;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.announce.detail.AnnounceDetailActivity;
import com.superpeer.tutuyoudian.adapter.AnnounceAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;

import rx.functions.Action1;

public class AnnounceActivity extends BaseActivity<AnnouncePresenter, AnnounceModel> implements AnnounceContract.View, BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {

    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private AnnounceAdapter adapter;
    private int PAGE = 1;
    private BaseBeanResult result;

    @Override
    public int getLayoutId() {
        return R.layout.activity_announce;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("公告");
        initRecyclerView();

        mPresenter.getNotice(PreferencesUtils.getString(mContext, Constants.SHOP_ID),PAGE+"", "10");

        initRxBus();
    }

    private void initRxBus() {
        mRxManager.on("notice", new Action1<String>() {
            @Override
            public void call(String s) {
                PAGE = 1;
                mPresenter.getNotice(PreferencesUtils.getString(mContext, Constants.SHOP_ID),PAGE+"", "10");
            }
        });
    }

    private void initRecyclerView() {

        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new AnnounceAdapter(R.layout.item_announce, null);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);
        //设置适配器可以上拉加载
        adapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
        refresh.setDelegate(this);
        refresh.setRefreshViewHolder(new NormalRefreshViewHolder(mContext, true));

        rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, AnnounceDetailActivity.class);
                intent.putExtra("noticeId", ((BaseList)adapter.getItem(position)).getNoticeId());
                startActivity(intent);
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
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        PAGE = 1;
        mPresenter.getNotice(PreferencesUtils.getString(mContext, Constants.SHOP_ID),PAGE+"", "10");
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (result != null) {
            if(null!=result.getData()&&null!=result.getData().getTotal()) {
                if (PAGE + 1 <= (Integer.parseInt(result.getData().getTotal())%10>0?Integer.parseInt(result.getData().getTotal())/10+1:Integer.parseInt(result.getData().getTotal())/10)) {
                    PAGE++;
                    mPresenter.getNotice(PreferencesUtils.getString(mContext, Constants.SHOP_ID),PAGE + "", "10");
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
            result = baseBeanResult;
            if ("1".equals(baseBeanResult.getCode())) {
                if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                    if (PAGE == 1) {
                        adapter.setNewData(baseBeanResult.getData().getList());
                    } else {
                        adapter.addData(baseBeanResult.getData().getList());
                    }
                }else{
                    adapter.getData().clear();
                    adapter.notifyDataSetChanged();
                }
            }
            refresh.endRefreshing();
            refresh.endLoadingMore();
            adapter.loadComplete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
