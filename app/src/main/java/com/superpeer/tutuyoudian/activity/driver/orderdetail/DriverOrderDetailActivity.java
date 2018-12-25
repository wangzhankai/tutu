package com.superpeer.tutuyoudian.activity.driver.orderdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.utils.ConstantsUtils;
import com.superpeer.base_libs.utils.DateUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.verify.VerifyActivity;
import com.superpeer.tutuyoudian.adapter.OrderShopAdapter;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnSureListener;
import com.superpeer.tutuyoudian.utils.DialogUtils;

public class DriverOrderDetailActivity extends BaseActivity<DriverOrderDetailPresenter, DriverOrderDetailModel> implements DriverOrderDetailContract.View {

    private String orderId = "";
    private ImageView ivStatus;
    private TextView tvStatus;
    private ImageView ivAvatar;
    private TextView tvCancel;
    private TextView tvGet;
    private TextView tvDelete;
    private TextView tvComplete;
    private TextView tvStoreName;
    private TextView tvPackageFee;
    private TextView tvSendFee;
    private TextView tvCoupon;
    private TextView tvTotal;
    private TextView tvCouponPrice;
    private TextView tvPayTrue;
    private LinearLayout linearPhone;
    private TextView tvSendType;
    //    private TextView tvWantTime;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvOrderNum;
    private TextView tvOrderTime;
    private TextView tvPayType;
    private BaseObject object;
    private TextView tvName;
    private TextView tvSendPhone;
    private LinearLayout linearSend;
    private TextView tvSendAddress;
    private TextView tvGetTime;
    private TextView tvWantTime;
    private RecyclerView recyclerView;
    private OrderShopAdapter adapter;

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        orderId = getIntent().getStringExtra("orderId");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_order_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("订单详情");

        ivStatus = (ImageView) findViewById(R.id.ivStatus);
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvGet = (TextView) findViewById(R.id.tvGet);
        tvDelete = (TextView) findViewById(R.id.tvDelete);
        tvComplete = (TextView) findViewById(R.id.tvComplete);
        tvStoreName = (TextView) findViewById(R.id.tvStoreName);
        tvPackageFee = (TextView) findViewById(R.id.tvPackageFee);
        tvSendFee = (TextView) findViewById(R.id.tvSendFee);
        tvCoupon = (TextView) findViewById(R.id.tvCoupon);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvCouponPrice = (TextView) findViewById(R.id.tvCouponPrice);
        tvPayTrue = (TextView) findViewById(R.id.tvPayTrue);
        linearPhone = (LinearLayout) findViewById(R.id.linearPhone);
        tvSendType = (TextView) findViewById(R.id.tvSendType);
        tvName = (TextView) findViewById(R.id.tvName);
        tvSendPhone = (TextView) findViewById(R.id.tvSendPhone);
        tvSendAddress = (TextView) findViewById(R.id.tvSendAddress);
        tvGetTime = (TextView) findViewById(R.id.tvGetTime);
        tvWantTime = (TextView) findViewById(R.id.tvWantTime);
        linearSend = (LinearLayout) findViewById(R.id.linearSend);
//        tvWantTime = (TextView) findViewById(R.id.tvWantTime);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvOrderNum = (TextView) findViewById(R.id.tvOrderNum);
        tvOrderTime = (TextView) findViewById(R.id.tvOrderTime);
        tvPayType = (TextView) findViewById(R.id.tvPayType);

        initRecyclerView();

        BaseObject bean = getUserInfo();
        if(null!=bean){
            initStoreInfo(bean);
        }

        mPresenter.getDetail(orderId);

        initListener();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new OrderShopAdapter(R.layout.item_orde_shop, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        //取消订单
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog(mContext, "是否取消订单", new OnSureListener() {
                    @Override
                    public void onSure() {
                        mPresenter.cancelOrder(orderId);
                    }
                });
            }
        });
        //接单
        tvGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getOrder(orderId, PreferencesUtils.getString(mContext, Constants.SHOP_ID));
            }
        });
        //删除订单
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog(mContext, "是否删除订单", new OnSureListener() {
                    @Override
                    public void onSure() {
                        mPresenter.delOrder(orderId);
                    }
                });
            }
        });

        //订单送达
        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog(mContext, "订单送达", new OnSureListener() {
                    @Override
                    public void onSure() {
                        mPresenter.confirmOrder(orderId);
                    }
                });
            }
        });
    }

    private void initStoreInfo(BaseObject bean) {
        if(null!=bean.getImagePath()){
            Glide.with(mContext).load(Url.IP+bean.getImagePath()).centerCrop().into(ivAvatar);
        }
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
    public void showResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData().getObject()){
                        object = baseBeanResult.getData().getObject();
                        initData(object);
                    }
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
                    /*mRxManager.post("order", "");
                    tvCancel.setVisibility(View.GONE);
                    tvGet.setVisibility(View.GONE);
                    tvComplete.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.VISIBLE);
                    ivStatus.setImageResource(R.mipmap.iv_order_complete);
                    tvStatus.setText("已取消");*/
                    object.setOrderStatus("6");
                    initData(object);
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
                    object.setOrderStatus("4");
                    initData(object);
                    /*ivStatus.setImageResource(R.mipmap.iv_order_readyget);
                    mRxManager.post("order", "");
                    tvGet.setVisibility(View.GONE);

                    tvDelete.setVisibility(View.GONE);
                    tvCancel.setVisibility(View.VISIBLE);
                    if(null!=object.getShippingType()){
                        if("1".equals(object.getShippingType())){ //送货上门
                            tvStatus.setText("送货中");
                            tvAddress.setVisibility(View.GONE);
                            tvPhone.setVisibility(View.GONE);
                            tvGetTime.setVisibility(View.GONE);
                            tvComplete.setVisibility(View.VISIBLE);
                        }else{      //自提
                            tvStatus.setText("待提货");
                            linearSend.setVisibility(View.GONE);
                            tvWantTime.setVisibility(View.GONE);
                            tvVerify.setVisibility(View.VISIBLE);
                            tvComplete.setVisibility(View.GONE);
                        }
                    }*/
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showDeleteResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult.getMsg()){
                showShortToast(baseBeanResult.getMsg());
            }
            if("1".equals(baseBeanResult.getCode())) {
                mRxManager.post("drivermain", "");
                finish();
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
                    object.setOrderStatus("5");
                    /*mRxManager.post("order", "");
                    tvCancel.setVisibility(View.GONE);
                    tvGet.setVisibility(View.GONE);
                    tvComplete.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.GONE);
                    object.setOrderStatus("5");
                    ivStatus.setImageResource(R.mipmap.iv_order_complete);
                    tvStatus.setText("已完成");*/
                    initData(object);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据
     * @param bean
     */
    private void initData(final BaseObject bean) {
        if(null!=bean.getOrderStatus()) {
            switch (bean.getOrderStatus()) {
                case "1":
                    ivStatus.setImageResource(R.mipmap.iv_no_pay);
                    tvStatus.setText("待付款");
                    tvCancel.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    ivStatus.setImageResource(R.mipmap.iv_no_pay);
                    tvStatus.setText("付款中");
                    tvCancel.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    ivStatus.setImageResource(R.mipmap.iv_order_readytaking);
                    tvStatus.setText("待接单");
                    tvGet.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.GONE);
                    tvComplete.setVisibility(View.GONE);
                    break;
                case "4":
                    tvGet.setVisibility(View.GONE);
                    tvCancel.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.GONE);
                    tvComplete.setVisibility(View.VISIBLE);
                    ivStatus.setImageResource(R.mipmap.iv_order_readyget);
                    if(null!=bean.getShippingType()){
                        if("1".equals(bean.getShippingType())){ //送货上门
                            tvStatus.setText("送货中");
                        }else{      //自提
                            tvStatus.setText("待提货");
                        }
                    }
                    break;
                case "5":
                    tvGet.setVisibility(View.GONE);
                    tvCancel.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.VISIBLE);
                    tvComplete.setVisibility(View.GONE);
                    ivStatus.setImageResource(R.mipmap.iv_order_complete);
                    tvStatus.setText("已完成");
                    tvCancel.setVisibility(View.GONE);
                    break;
                case "6":
                    tvGet.setVisibility(View.GONE);
                    tvCancel.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.VISIBLE);
                    tvComplete.setVisibility(View.GONE);
                    ivStatus.setImageResource(R.mipmap.iv_order_complete);
                    tvStatus.setText("已取消");
                    tvDelete.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                    break;
            }
            if(null!=bean.getShippingType()){
                if("1".equals(bean.getShippingType())){ //送货上门
                    linearSend.setVisibility(View.VISIBLE);
                    tvWantTime.setVisibility(View.VISIBLE);
                    tvAddress.setVisibility(View.GONE);
                    tvPhone.setVisibility(View.GONE);
                }else{      //自提
                    linearSend.setVisibility(View.GONE);
                    tvWantTime.setVisibility(View.GONE);
                    tvAddress.setVisibility(View.VISIBLE);
                    tvPhone.setVisibility(View.VISIBLE);
                }
            }
            if(null!=bean.getShopName()){
                tvStoreName.setText(bean.getShopName());
            }
            if(null!=bean.getShippingType()){
                if("1".equals(bean.getShippingType())){ //送货上门
                    tvSendType.setText("配送方式：送货上门");
                }else{      //自提
                    tvSendType.setText("配送方式：自提");
                }
            }
            if(null!=bean.getFreight()){
                tvSendFee.setText(bean.getFreight());
            }
            if(null!=bean.getPackingFee()){
                tvPackageFee.setText("￥"+bean.getPackingFee());
            }
            if(null!=bean.getTotalPrice()){
                tvTotal.setText("共计：￥"+bean.getTotalPrice());
            }
            if(null!=bean.getPrice()){
                tvPayTrue.setText("￥"+bean.getPrice());
            }
            if(null!=bean.getAddress()){
                tvAddress.setText("送货地址："+bean.getAddress());
                tvSendAddress.setText(bean.getAddress());
            }
            if(null!=bean.getPhone()){
                tvPhone.setText("联系电话："+bean.getPhone());
                tvSendPhone.setText(bean.getPhone());
                final String phone = bean.getPhone();
                linearPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstantsUtils.callPhone(mContext, phone);
                    }
                });
            }
            if(null!=bean.getOrderNum()){
                tvOrderNum.setText("订单号码："+bean.getOrderNum());
            }
            if(null!=bean.getCreateTime()){
//                tvOrderTime.setText("订单时间："+DateUtils.getStringToDate(bean.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                tvOrderTime.setText("订单时间："+bean.getCreateTime());
            }
            if(null!=bean.getConsignee()){
                tvName.setText(bean.getConsignee());
            }
            if(null!=bean.getGoodsVos()&&bean.getGoodsVos().size()>0){
                adapter.setNewData(bean.getGoodsVos());
                adapter.loadComplete();
            }
            if(null!=bean.getShippingTime()){
                tvWantTime.setText("期望时间："+bean.getShippingTime());
            }
        }
    }
}
