package com.superpeer.tutuyoudian.activity.stocksearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.NormalRefreshViewHolder;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.addshop.AddShopActivity;
import com.superpeer.tutuyoudian.activity.image.ImageActivity;
import com.superpeer.tutuyoudian.adapter.ShopManagerAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnEditListener;
import com.superpeer.tutuyoudian.listener.OnImgListener;
import com.superpeer.tutuyoudian.listener.OnUpOrDownListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

public class StockSearchActivity extends BaseActivity<StockSearchPresenter, StockSearchModel> implements StockSearchContract.View, BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {

    private int windowWidth;
    private EditText etSearch;
    private SwipeMenuRecyclerView rvContent;
    private String name = "";
    private int PAGE = 1;
    private RefreshLayout refresh;
    private ShopManagerAdapter adapter;
    private BaseBeanResult result;

    private int updatePos;
    private String price = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_stock_search;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("待补库存搜索");

        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        windowWidth = wm.getDefaultDisplay().getWidth();

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
                    mPresenter.getStockSearch(PreferencesUtils.getString(mContext, Constants.SHOP_ID), name, PAGE+"", "10");
                }
                return false;
            }
        });
    }

    private void initRecyclerView() {
        rvContent = (SwipeMenuRecyclerView) findViewById(R.id.rv_content);
        rvContent.setSwipeMenuCreator(swipeMenuCreator);
        rvContent.setSwipeMenuItemClickListener(mMenuItemClickListener);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new ShopManagerAdapter(R.layout.item_shop_manager, null, false);
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

        //编辑
        adapter.setOnEditListener(new OnEditListener() {
            @Override
            public void OnEditListener(int position) {
                Intent intent = new Intent(mContext, AddShopActivity.class);
                intent.putExtra("shopManager", ((BaseList)adapter.getItem(adapterPosition)));
                intent.putExtra("barCode", ((BaseList) adapter.getItem(position)).getBarCode());
                startActivity(intent);
            }
        });
        //上下架
        adapter.setOnUpOrDownListener(new OnUpOrDownListener() {
            @Override
            public void onUpOrDownListener(int position, String type) {
                mPresenter.modifySaleState(((BaseList)adapter.getItem(adapterPosition)).getGoodsId(), ("1".equals(type)||"".equals(type))?"0":"1");
            }
        });

    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator()
    {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType)
        {
            int width = windowWidth/6;

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 根据ViewType来决定哪一个item该如何添加菜单。
            // 这里模拟业务，实际开发根据自己的业务计算。
           /* SwipeMenuItem addItem1;
            if("0".equals(type)){
                addItem1 = new SwipeMenuItem(mContext).setBackgroundColor(getResources().getColor(R.color.orange)).setImage(R.mipmap.iv_shop_up).setText("上架").setTextColor(Color.WHITE)
                        .setWidth(width).setHeight(height);
            }else{
                addItem1 = new SwipeMenuItem(mContext).setBackgroundColor(getResources().getColor(R.color.orange)).setImage(R.mipmap.iv_shop_down).setText("下架").setTextColor(Color.WHITE)
                        .setWidth(width).setHeight(height);
            }
            SwipeMenuItem addItem2 = new SwipeMenuItem(mContext).setBackgroundColor(getResources().getColor(R.color.oper_orange)).setImage(R.mipmap.iv_shop_edit).setText("编辑").setTextColor(Color.WHITE)
                    .setWidth(width).setHeight(height);*/
            SwipeMenuItem addItem3 = new SwipeMenuItem(mContext).setBackgroundColor(getResources().getColor(R.color.oper_grey)).setImage(R.mipmap.iv_shop_delete).setText("删除").setTextColor(Color.WHITE)
                    .setWidth(width).setHeight(height);
//            swipeRightMenu.addMenuItem(addItem1); // 添加菜单到右侧。
//            swipeRightMenu.addMenuItem(addItem2); // 添加菜单到右侧。
            swipeRightMenu.addMenuItem(addItem3); // 添加菜单到右侧。
        }
    };

    //删除的位置
    private int adapterPosition;
    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener()
    {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge)
        {
            try {
                menuBridge.closeMenu();

                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                    Log.i("base", "base");
//                    if(menuPosition == 0){
//                        //下架
//                        mPresenter.modifySaleState(((BaseList)adapter.getItem(adapterPosition)).getGoodsId(), "1".equals(type)?"0":"1");
//                    }else if(menuPosition == 1){
//                        //编辑
//                        Intent intent = new Intent(mContext, AddShopActivity.class);
//                        intent.putExtra("shopManager", ((BaseList)adapter.getItem(adapterPosition)));
//                        startActivity(intent);
//                    }else if(menuPosition == 2){
                    //删除
                    mPresenter.delMyGoods(((BaseList)adapter.getItem(adapterPosition)).getGoodsId());
//                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

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
    public void showStockResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                result = baseBeanResult;
                if(null!=baseBeanResult.getData()){
                    if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                        adapter.setType("1");
                        if(PAGE == 1){
                            adapter.setNewData(baseBeanResult.getData().getList());
                        }else{
                            adapter.addData(baseBeanResult.getData().getList());
                        }
                    }else{
                        adapter.getData().clear();
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    adapter.getData().clear();
                    adapter.notifyDataSetChanged();
                }
            }
            adapter.loadComplete();
            refresh.endRefreshing();
            refresh.endLoadingMore();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showModifyResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    adapter.getData().remove(adapterPosition);
                    adapter.notifyDataSetChanged();
                    mRxManager.post("stocksearch", "");
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
                    adapter.getData().remove(adapterPosition);
                    adapter.notifyDataSetChanged();
                    mRxManager.post("delGoods", "");
                    mRxManager.post("stocksearch", "");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showUpdate(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())) {
                    ((BaseList)adapter.getItem(updatePos)).setPrice(price);
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
        mPresenter.getStockSearch(PreferencesUtils.getString(mContext, Constants.SHOP_ID), name, PAGE+"", "10");
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (result != null) {
            if (null != result.getData() && null != result.getData().getTotal()) {
                if (PAGE + 1 <= (Integer.parseInt(result.getData().getTotal()) % 10 > 0 ? Integer.parseInt(result.getData().getTotal()) / 10 + 1 : Integer.parseInt(result.getData().getTotal()) / 10)) {
                    PAGE++;
                    mPresenter.getStockSearch(PreferencesUtils.getString(mContext, Constants.SHOP_ID), name, PAGE+"", "10");
                }
            }else {
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
}
