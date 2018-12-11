package com.superpeer.tutuyoudian.activity.shoplibrary;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
import com.superpeer.tutuyoudian.activity.addshop.AddShopActivity;
import com.superpeer.tutuyoudian.activity.goodssearch.GoodsSearchActivity;
import com.superpeer.tutuyoudian.activity.search.SearchActivity;
import com.superpeer.tutuyoudian.activity.shopmanager.ShopManagerActivity;
import com.superpeer.tutuyoudian.adapter.CategoryAdapter;
import com.superpeer.tutuyoudian.adapter.ShopLibraryAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnEditListener;
import com.superpeer.tutuyoudian.listener.OnOperListener;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class ShopLibraryActivity extends BaseActivity<ShopLibraryPresenter, ShopLibraryModel> implements ShopLibraryContract.View, BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {

    private RecyclerView recyclerCategory;
    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private ShopLibraryAdapter adapter;
//    private List<BaseList> categoryList;
    private String typeId = "";
    private int PAGE = 1;
    private BaseBeanResult result;

    private int selectPos = -1;
    private CategoryAdapter categoryAdapter;
    private List<BaseList> categoryList = new ArrayList<>();
    private TextView tvSearch;
    private int editPos = -1;
    private TextView tvRight;

    /*@Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        categoryList = (List<BaseList>) getIntent().getSerializableExtra("categoryList");
    }*/

    @Override
    public int getLayoutId() {
        return R.layout.activity_shop_library;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("商品库上传");

        tvRight = setToolBarViewStubText("确认");
        tvRight.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ShopManagerActivity.class);
                finish();
            }
        });

        tvSearch = (TextView) findViewById(R.id.tvSearch);

        initRecyclerView();

        mPresenter.getGoodsType(PreferencesUtils.getString(mContext, Constants.SHOP_ID));

        categoryAdapter = new CategoryAdapter(R.layout.item_category, categoryList);
        recyclerCategory.setAdapter(categoryAdapter);
        if(categoryList.size()>0){
            typeId = categoryList.get(0).getGoodsTypeId();
            mPresenter.getGoods(typeId, PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE + "", "10", "");
        }
        recyclerCategory.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                categoryAdapter.setSelectPos(position);
                categoryAdapter.notifyDataSetChanged();
                PAGE = 1;
                typeId = categoryList.get(position).getGoodsTypeId();
                mPresenter.getGoods(typeId, PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE + "", "10", "");
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsSearchActivity.class);
                startActivity(intent);
            }
        });

        initRxBus();
    }

    private void initRxBus() {
        mRxManager.on("delGoods", new Action1<String>() {
            @Override
            public void call(String s) {
                PAGE = 1;
                mPresenter.getGoods(typeId, PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE + "", "10", "");
            }
        });
        mRxManager.on("library", new Action1<BaseObject>() {
            @Override
            public void call(BaseObject s) {
                try {
                    if (editPos != -1) {
                        BaseList bean = ((BaseList) adapter.getItem(editPos));
                        bean.setChecked(true);
                        bean.setGoodsId(s.getGoodsId());
                        bean.setPrice(s.getPrice());
                        bean.setStock(s.getStock());
                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void initRecyclerView() {

        recyclerCategory = (RecyclerView) findViewById(R.id.recyclerCategory);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(mContext));

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

       /* rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectPos = position;
                mPresenter.saveGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), ((BaseList)adapter.getItem(position)).getBankId());
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
    public void showTypeResult(BaseBeanResult baseBeanResult) {
        try {
            if (null != baseBeanResult) {
                if ("1".equals(baseBeanResult.getCode())) {
                    if (null != baseBeanResult.getData()) {
                        if (null != baseBeanResult.getData().getList() && baseBeanResult.getData().getList().size() > 0) {
                            categoryList = baseBeanResult.getData().getList();
                            categoryAdapter = new CategoryAdapter(R.layout.item_category, categoryList);
                            recyclerCategory.setAdapter(categoryAdapter);
                            typeId = categoryList.get(0).getGoodsTypeId();
                            mPresenter.getGoods(typeId, PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE + "", "10", "");
                            /*recyclerCategory.addOnItemTouchListener(new OnItemClickListener() {
                                @Override
                                public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    typeId = categoryList.get(position).getGoodsTypeId();
                                    mPresenter.getGoods(typeId, PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE + "", "10", "");
                                }
                            });*/
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    if(null!=baseBeanResult.getData()&&null!=baseBeanResult.getData().getObject()){
                        bean.setGoodsId(baseBeanResult.getData().getObject().getGoodsId());
                        bean.setPrice(baseBeanResult.getData().getObject().getPrice());
                        bean.setStock(baseBeanResult.getData().getObject().getStock());
                        if(null!=baseBeanResult.getData().getObject().getVipPrice()){
                            bean.setVipPrice(baseBeanResult.getData().getObject().getVipPrice());
                        }else{
                            bean.setVipPrice("");
                        }
                    }else{
                        bean.setGoodsId("");
                    }
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
        mPresenter.getGoods(typeId, PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE + "", "10", "");
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (result != null) {
            if (null != result.getData() && null != result.getData().getTotal()) {
                if (PAGE + 1 <= (Integer.parseInt(result.getData().getTotal())%10>0?Integer.parseInt(result.getData().getTotal())/10+1:Integer.parseInt(result.getData().getTotal())/10)) {
                    PAGE++;
                    mPresenter.getGoods(typeId, PreferencesUtils.getString(mContext, Constants.SHOP_ID), PAGE + "", "10", "");
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}