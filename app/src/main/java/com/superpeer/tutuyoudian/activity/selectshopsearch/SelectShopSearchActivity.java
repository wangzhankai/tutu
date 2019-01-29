package com.superpeer.tutuyoudian.activity.selectshopsearch;

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
import com.superpeer.tutuyoudian.activity.image.ImageActivity;
import com.superpeer.tutuyoudian.adapter.ShopAdapter;
import com.superpeer.tutuyoudian.adapter.ShopLibraryAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnImgListener;
import com.superpeer.tutuyoudian.listener.OnItemListener;

public class SelectShopSearchActivity extends BaseActivity<SelectShopSearchPresenter, SelectShopSearchModel> implements SelectShopSearchContract.View, BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {

    private EditText etSearch;
    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private String name = "";
    private int PAGE = 1;
    private ShopAdapter adapter;
    private int selectPos;
    private BaseBeanResult result;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_shop_search;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("商品搜索");

        etSearch = (EditText) findViewById(R.id.etSearch);

        initRecyclerView();

        initListener();
    }

    private void initListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    name = v.getText().toString().trim();
                    mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "", "", "", PAGE+"", "10", name);
                }
                return false;
            }
        });
    }

    private void initRecyclerView() {

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

        //图片放大
        adapter.setOnImgListener(new OnImgListener() {
            @Override
            public void onImgListener(int position) {
                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putExtra("url", ((BaseList) adapter.getItem(position)).getImagePath());
                startActivity(intent);
            }
        });

        adapter.setOnItemListener(new OnItemListener() {
            @Override
            public void onItem(int position) {
                adapter.setSelectPos(position);
                adapter.notifyDataSetChanged();
                mRxManager.post("selectShop", ((BaseList)adapter.getItem(position)));
                mRxManager.post("selectCollage", "");
                finish();
            }
        });

        /*rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter myadapter, View view, int position) {
                adapter.setSelectPos(position);
                adapter.notifyDataSetChanged();
                mRxManager.post("selectShop", ((BaseList)adapter.getItem(position)));
                finish();
            }
        });*/

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
                    adapter.loadComplete();
                    refresh.endLoadingMore();
                    refresh.endRefreshing();
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
        mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "", "", "", PAGE+"", "10", name);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (result != null) {
            if(null!=result.getData()&&null!=result.getData().getTotal()) {
                if (PAGE + 1 <= (Integer.parseInt(result.getData().getTotal())%10>0?Integer.parseInt(result.getData().getTotal())/10+1:Integer.parseInt(result.getData().getTotal())/10)) {
                    PAGE++;
                    mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "", "", "", PAGE+"", "10", name);
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
