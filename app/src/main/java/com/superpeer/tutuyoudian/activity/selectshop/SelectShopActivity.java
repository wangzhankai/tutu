package com.superpeer.tutuyoudian.activity.selectshop;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.NormalRefreshViewHolder;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.search.SearchActivity;
import com.superpeer.tutuyoudian.activity.selectshopsearch.SelectShopSearchActivity;
import com.superpeer.tutuyoudian.adapter.CategoryAdapter;
import com.superpeer.tutuyoudian.adapter.ShopAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;

import java.util.List;

public class SelectShopActivity extends BaseActivity<SelectShopPresenter, SelectShopModel> implements SelectShopContract.View, BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {

    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private ShopAdapter adapter;
    private RecyclerView recyclerCategory;
    private List<BaseList> categoryList;
    private int PAGE = 1;
    private String typeId = "";
    private BaseBeanResult result;

    private String selectId = "";
    private CategoryAdapter categoryAdapter;
    private TextView tvSearch;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_shop;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("选择商品");

        tvSearch = (TextView) findViewById(R.id.tvSearch);

        initRecyclerView();

        mPresenter.getType(PreferencesUtils.getString(mContext, Constants.SHOP_ID));

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SelectShopSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {

        recyclerCategory = (RecyclerView) findViewById(R.id.recyclerCategory);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(mContext));

        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new ShopAdapter(R.layout.item_shop_select, null);
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
            public void SimpleOnItemClick(BaseQuickAdapter myadapter, View view, int position) {
                adapter.setSelectPos(position);
                adapter.notifyDataSetChanged();
                mRxManager.post("selectShop", ((BaseList)adapter.getItem(position)));
                finish();
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
    public void showTypeResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData()){
                        if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                            categoryList = baseBeanResult.getData().getList();
                            categoryAdapter = new CategoryAdapter(R.layout.item_category, categoryList);
                            recyclerCategory.setAdapter(categoryAdapter);

                            typeId = categoryList.get(0).getGoodsTypeId();
                            mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, "", "", PAGE+"", "10", "");
                            recyclerCategory.addOnItemTouchListener(new OnItemClickListener() {
                                @Override
                                public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    PAGE = 1;
                                    categoryAdapter.setSelectPos(position);
                                    categoryAdapter.notifyDataSetChanged();
                                    typeId = categoryList.get(position).getGoodsTypeId();
                                    mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, "", "", PAGE+"", "10", "");
                                }
                            });
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showGoodsResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                result = baseBeanResult;
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData()&&null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
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
        mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, "", "", PAGE+"", "10", "");
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (result != null) {
            if(null!=result.getData()&&null!=result.getData().getTotal()) {
                if (PAGE + 1 <= (Integer.parseInt(result.getData().getTotal())%10>0?Integer.parseInt(result.getData().getTotal())/10+1:Integer.parseInt(result.getData().getTotal())/10)) {
                    PAGE++;
                    mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, "", "", PAGE+"", "10", "");
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
