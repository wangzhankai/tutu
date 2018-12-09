package com.superpeer.tutuyoudian.activity.goodssearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.NormalRefreshViewHolder;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.addshop.AddShopActivity;
import com.superpeer.tutuyoudian.adapter.ShopLibraryAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnEditListener;
import com.superpeer.tutuyoudian.listener.OnOperListener;

public class GoodsSearchActivity extends BaseActivity<GoodsSearchPresenter, GoodsSearchModel> implements GoodsSearchContract.View, BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {

    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private ShopLibraryAdapter adapter;
    private int selectPos;
    private EditText etSearch;
    private String name = "";
    private int PAGE = 1;
    private BaseBeanResult result;
    private int editPos;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_search;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("商品库搜索");

        etSearch = (EditText) findViewById(R.id.etSearch);

        initRecyclerView();

        initListener();

//        mPresenter.getGoods("", PreferencesUtils.getString(mContext, Constants.SHOP_ID),  PAGE+"", "10");
    }

    private void initListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    name = v.getText().toString().trim();
                    mPresenter.getGoods("", PreferencesUtils.getString(mContext, Constants.SHOP_ID),  PAGE+"", "10", name);
                }
                return false;
            }
        });
    }

    private void initRecyclerView() {

        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new ShopLibraryAdapter(R.layout.item_shop_select, null);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);
        //设置适配器可以上拉加载
        adapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
        refresh.setDelegate(this);
        refresh.setRefreshViewHolder(new NormalRefreshViewHolder(mContext, true));

        adapter.setOnOperListener(new OnOperListener() {
            @Override
            public void onOperListener(int position) {
                selectPos = position;
                mPresenter.saveGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), ((BaseList)adapter.getItem(position)).getBankId());
            }
        });

        adapter.setOnEditListener(new OnEditListener() {
            @Override
            public void OnEditListener(int position) {
                editPos = position;
                Intent intent = new Intent(mContext, AddShopActivity.class);
                intent.putExtra("shopManager", ((BaseList)adapter.getItem(position)));
                intent.putExtra("barCode", ((BaseList) adapter.getItem(position)).getBarCode());
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
    public void showGoodsResult(BaseBeanResult baseBeanResult) {
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
    public void showSaveResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(selectPos!=-1){
                    BaseList bean = (BaseList) adapter.getItem(selectPos);
                    bean.setChecked(!bean.isChecked());
                    adapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        PAGE = 1;
        mPresenter.getGoods("", PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE + "", "10", name);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (result != null) {
            if (null != result.getData() && null != result.getData().getTotal()) {
                if (PAGE + 1 <= Integer.parseInt(result.getData().getTotal())/10) {
                    PAGE++;
                    mPresenter.getGoods("", PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE + "", "10", name);
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
