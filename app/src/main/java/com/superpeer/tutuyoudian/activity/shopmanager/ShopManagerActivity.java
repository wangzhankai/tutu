package com.superpeer.tutuyoudian.activity.shopmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.NormalRefreshViewHolder;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.addshop.AddShopActivity;
import com.superpeer.tutuyoudian.activity.main.MainActivity;
import com.superpeer.tutuyoudian.activity.search.SearchActivity;
import com.superpeer.tutuyoudian.activity.stocksearch.StockSearchActivity;
import com.superpeer.tutuyoudian.adapter.CategoryAdapter;
import com.superpeer.tutuyoudian.adapter.ShopManagerAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnAddListener;
import com.superpeer.tutuyoudian.listener.OnDownListener;
import com.superpeer.tutuyoudian.listener.OnEditListener;
import com.superpeer.tutuyoudian.listener.OnOperListener;
import com.superpeer.tutuyoudian.listener.OnStockListener;
import com.superpeer.tutuyoudian.listener.OnSubListener;
import com.superpeer.tutuyoudian.listener.OnUpListener;
import com.superpeer.tutuyoudian.listener.OnUpOrDownListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.List;

import rx.functions.Action1;

public class ShopManagerActivity extends BaseActivity<ShopManagerPresenter, ShopManagerModel> implements ShopManagerContract.View, BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {

    private List<BaseList> categoryList;
    private RecyclerView recyclerCategory;
    private SwipeMenuRecyclerView rvContent;
    private RefreshLayout refresh;
    private ShopManagerAdapter adapter;
    private String type = "1";
    private String stock = "";

    private int PAGE = 1;
    private TextView tvUp;
    private TextView tvRest;
    private TextView tvDown;
    private CategoryAdapter categoryAdapter;
    private String typeId = "";
    private int windowWidth;
    private TextView tvRight;
    private boolean isSort;
    private BaseBeanResult result;
    private int sortPos;
    private String flag;
    private int stockPos;
    private String stockNum;
    private TextView tvSearch;
    private LinearLayout linearUpload;
    private ImageView ivScan;
    private ImageView ivUpload;
    private String barCode = "";
    private static final int REQUEST_CODE_SCAN = 888;
    private String typeName = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        typeId = getIntent().getStringExtra("type");
        typeName = getIntent().getStringExtra("typeName");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shop_manager;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("商品管理");
        tvRight = setToolBarViewStubText("排序");
        tvRight.setVisibility(View.GONE);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //排序
                PAGE = 1;
                isSort = !isSort;
                mPresenter.getCategory(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
            }
        });

        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        windowWidth = wm.getDefaultDisplay().getWidth();

        initRecyclerView();

        tvUp = (TextView) findViewById(R.id.tvUp);
        tvRest = (TextView) findViewById(R.id.tvRest);
        tvDown = (TextView) findViewById(R.id.tvDown);
        tvSearch = (TextView) findViewById(R.id.tvSearch);
        linearUpload = (LinearLayout) findViewById(R.id.linearUpload);
        ivScan = (ImageView) findViewById(R.id.ivScan);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);

        if(typeName == null ||"".equals(typeName)){
            linearUpload.setVisibility(View.GONE);
        }else{
            linearUpload.setVisibility(View.VISIBLE);
            ivScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initQRCode();
                }
            });
            ivUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddShopActivity.class);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                }
            });
        }

        tvUp.setSelected(true);

        initListener();

        initRxBus();

        mPresenter.getCategory(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
    }

    private void initRxBus() {
        mRxManager.on("stocksearch", new Action1<String>() {
            @Override
            public void call(String s) {
                PAGE = 1;
                mPresenter.getStock(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, PAGE+"", "10");
            }
        });
        mRxManager.on("shopsearch", new Action1<String>() {
            @Override
            public void call(String s) {
                PAGE = 1;
                mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, type, stock, PAGE+"", "10", "");
            }
        });
    }

    private void initQRCode() {
        try {
            /*QrConfig qrConfig = new QrConfig.Builder()
                    .setDesText("(识别二维码或条形码)")//扫描框下文字
                    .setShowDes(false)//是否显示扫描框下面文字
                    .setShowLight(true)//显示手电筒按钮
                    .setShowTitle(true)//显示Title
                    .setShowAlbum(true)//显示从相册选择按钮
                    .setCornerColor(Color.WHITE)//设置扫描框颜色
                    .setLineColor(Color.WHITE)//设置扫描线颜色
                    .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                    .setScanType(QrConfig.TYPE_ALL)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                    .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
//                    .setCustombarcodeformat(QrConfig.BARCODE_I25)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                    .setPlaySound(true)//是否扫描成功后bi~的声音
//                .setDingPath(R.raw.test)//设置提示音(不设置为默认的Ding~)
                    .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                    .setTitleText("扫描二维码或条形码")//设置Tilte文字
                    .setTitleBackgroudColor(ContextCompat.getColor(mContext, R.color.colorPrimary))//设置状态栏颜色
                    .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                    .create();
            QrManager.getInstance().init(qrConfig).startScan(ShopManagerActivity.this, new QrManager.OnScanResultCallback() {
                @Override
                public void onScanSuccess(String result) {
                    barCode = result;
                    mPresenter.codeUpload(result, PreferencesUtils.getString(mContext, Constants.SHOP_ID));
                }
            });*/
            MPermissionUtils.requestPermissionsResult((Activity) mContext, 1, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new MPermissionUtils.OnPermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Intent intent = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                }

                @Override
                public void onPermissionDenied() {
                    MPermissionUtils.showTipsDialog(mContext);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initListener() {
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("1".equals(stock)){
                    Intent intent = new Intent(mContext, StockSearchActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("stock", stock);
                    startActivity(intent);
                }
            }
        });

        tvUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRight.setVisibility(View.GONE);
                tvSearch.setHint("搜索在售商品");
                type = "1";
                stock = "";
                tvUp.setSelected(true);
                tvRest.setSelected(false);
                tvDown.setSelected(false);
//                mPresenter.getCategory(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
                PAGE = 1;
                mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, type, stock, PAGE+"", "10", "");
            }
        });

        tvRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRight.setVisibility(View.GONE);
                tvSearch.setHint("搜索待补库存");
                type = "";
                stock = "1";
                tvUp.setSelected(false);
                tvRest.setSelected(true);
                tvDown.setSelected(false);
//                mPresenter.getCategory(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
                PAGE = 1;
                mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, type, stock, PAGE+"", "10", "");
            }
        });

        tvDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRight.setVisibility(View.GONE);
                tvSearch.setHint("搜索待上架商品");
                type = "0";
                stock = "";
                tvUp.setSelected(false);
                tvRest.setSelected(false);
                tvDown.setSelected(true);
//                mPresenter.getCategory(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
                PAGE = 1;
                mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, type, stock, PAGE+"", "10", "");
            }
        });
    }

    private void initRecyclerView() {
        recyclerCategory = (RecyclerView) findViewById(R.id.recyclerCategory);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent = (SwipeMenuRecyclerView) findViewById(R.id.rv_content);
        rvContent.setSwipeMenuCreator(swipeMenuCreator);
        rvContent.setSwipeMenuItemClickListener(mMenuItemClickListener);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new ShopManagerAdapter(R.layout.item_shop_manager, null, isSort);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);

        //设置适配器可以上拉加载
        adapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
        refresh.setDelegate(this);
        refresh.setRefreshViewHolder(new NormalRefreshViewHolder(mContext, true));

        recyclerCategory.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                PAGE = 1;
                typeId = categoryList.get(position).getGoodsTypeId();
                categoryAdapter.setSelectPos(position);
                categoryAdapter.notifyDataSetChanged();
                if("1".equals(stock)){
                    mPresenter.getStock(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, PAGE+"", "10");
                }else{
                    mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, type, stock, PAGE+"", "10", "");
                }
            }
        });
        /*recyclerCategory.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                categoryAdapter.setSelectPos(position);
                categoryAdapter.notifyDataSetChanged();
                PAGE = 1;
                if("1".equals(stock)){
                    mPresenter.getStock(PreferencesUtils.getString(mContext, Constants.SHOP_ID), categoryList.get(0).getGoodsTypeId(), PAGE+"", "10");
                }else{
                    mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), categoryList.get(0).getGoodsTypeId(), type, stock, PAGE+"", "10", "");
                }
            }
        });*/

        //上移
        adapter.setOnUpListener(new OnUpListener() {
            @Override
            public void onUp(int position) {
                sortPos = position;
                flag = "0";
                mPresenter.changeSort(((BaseList)adapter.getItem(position)).getGoodsId(), "0");
            }
        });
        //下移
        adapter.setOnDownListener(new OnDownListener() {
            @Override
            public void onDown(int position) {
                sortPos = position;
                flag = "1";
                mPresenter.changeSort(((BaseList)adapter.getItem(position)).getGoodsId(), "1");
            }
        });
        //加库存
        adapter.setOnAddListener(new OnAddListener() {
            @Override
            public void onAdd(int position, String num) {
                stockPos = position;
                stockNum = Integer.parseInt(num)+1+"";
                mPresenter.addStock(((BaseList)adapter.getItem(position)).getGoodsId(), Integer.parseInt(num)+1+"");
            }
        });
        //减库存
        adapter.setOnSubListener(new OnSubListener() {
            @Override
            public void onSub(int position, String num) {
                stockPos = position;
                stockNum = Integer.parseInt(num)+1+"";
                if(Integer.parseInt(num)-1<0){
                    showShortToast("库存不可减少");
                    return;
                }
                mPresenter.addStock(((BaseList)adapter.getItem(position)).getGoodsId(), Integer.parseInt(num)-1+"");
            }
        });
        //库存
        adapter.setOnStockListener(new OnStockListener() {
            @Override
            public void onStock(int position, String num) {
                stockPos = position;
                stockNum = Integer.parseInt(num)+1+"";
                if(Integer.parseInt(num)<0){
                    showShortToast("库存不可减少");
                    return;
                }
                mPresenter.addStock(((BaseList)adapter.getItem(position)).getGoodsId(), Integer.parseInt(num)+"");
            }
        });

        //编辑
        adapter.setOnEditListener(new OnEditListener() {
            @Override
            public void OnEditListener(int position) {
                Intent intent = new Intent(mContext, AddShopActivity.class);
                intent.putExtra("shopManager", ((BaseList)adapter.getItem(position)));
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
    public void showCategory(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getData()){
                    if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                        categoryList = baseBeanResult.getData().getList();
                        categoryAdapter = new CategoryAdapter(R.layout.item_category, categoryList);
                        recyclerCategory.setAdapter(categoryAdapter);
                        if(!"typeName".equals(typeName)&&!"".equals(typeName)&&null!=typeName){
                            for(int i=0; i<categoryList.size(); i++){
                                if(typeName.equals(categoryList.get(i).getName())){
                                    typeId = categoryList.get(i).getGoodsTypeId();
                                    categoryAdapter.setSelectPos(i);
                                    categoryAdapter.notifyDataSetChanged();
                                }
                            }
                        }else{
                            typeId = categoryList.get(0).getGoodsTypeId();
                        }
                        if(isSort){
                            mPresenter.getSortGoodsInfo(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, PAGE+"", "10");
                        }else{
                            if("1".equals(stock)){
                                mPresenter.getStock(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, PAGE+"", "10");
                            }else{
                                mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, type, stock, PAGE+"", "10", "");
                            }
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
                if(null!=baseBeanResult.getData()){
                    if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                        adapter.setType(type);
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
                if("1".equals(baseBeanResult.getCode())){
                    adapter.getData().remove(adapterPosition);
                    adapter.notifyDataSetChanged();
                    mRxManager.post("delGoods", "");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showSortGoodsInfo(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                result = baseBeanResult;
                if(null!=baseBeanResult.getData()){
                    if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                        adapter.setSort(true);
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
    public void showSortResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(isSort){
                        mPresenter.getSortGoodsInfo(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, PAGE+"", "10");
                    }else{
                        if("1".equals(stock)){
                            mPresenter.getStock(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, PAGE+"", "10");
                        }else{
                            mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, type, stock, PAGE+"", "10", "");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showAddResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    ((BaseList)adapter.getItem(stockPos)).setStock(stockNum);
                    adapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showUpload(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    Intent intent = new Intent(mContext, AddShopActivity.class);
                    if(null!=baseBeanResult.getData()&&null!=baseBeanResult.getData().getObject()){
                        intent.putExtra("bean", baseBeanResult.getData().getObject());
                    }
                    intent.putExtra("barCode", barCode);
                    startActivity(intent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        PAGE = 1;
        if(isSort){
            mPresenter.getSortGoodsInfo(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, PAGE+"", "10");
        }else{
            if("1".equals(stock)){
                mPresenter.getStock(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, PAGE+"", "10");
            }else{
                mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, type, stock, PAGE+"", "10", "");
            }
        }
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (result != null) {
            if(null!=result.getData()&&null!=result.getData().getTotal()) {
                if (PAGE + 1 <= (Integer.parseInt(result.getData().getTotal())%10>0?Integer.parseInt(result.getData().getTotal())/10+1:Integer.parseInt(result.getData().getTotal())/10)) {
                    PAGE++;
                    if(isSort){
                        mPresenter.getSortGoodsInfo(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, PAGE+"", "10");
                    }else{
                        if("1".equals(stock)){
                            mPresenter.getStock(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, PAGE+"", "10");
                        }else{
                            mPresenter.getGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), typeId, type, stock, PAGE+"", "10", "");
                        }
                    }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            // 扫描二维码/条码回传
            if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
                if (data != null) {
                    String content = data.getStringExtra(Constant.CODED_CONTENT);
                    barCode = content;
                    mPresenter.codeUpload(content, PreferencesUtils.getString(mContext, Constants.SHOP_ID));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
