package com.superpeer.tutuyoudian.activity.collageorder.detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.base_libs.utils.ConstantsUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.verify.VerifyActivity;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.utils.TvUtils;

import java.net.URI;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;

public class CollageOrderDetailActivity extends BaseActivity<CollageOrderDetailPresenter, CollageOrderDetailModel> implements CollageOrderDetailContract.View {

    private String orderId = "";
    private ImageView ivStatus;
    private TextView tvStatus;
    private TextView tvCancel;
    private TextView tvGet;
    private TextView tvDelete;
    private TextView tvVerify;
    private TextView tvTotalNum;
    private TextView tvCollageNum;
    private TextView tvSendType;
    private TextView tvGetTime;
    private TextView tvWantTime;
    private TextView tvContractPhone;
    private TextView tvStoreAddress;
    private LinearLayout linearInfo;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private ImageView ivAvatar;
    private TextView tvStoreName;
    private TextView tvPackageFee;
    private TextView tvSendFee;
    private TextView tvCoupon;
    private TextView tvTotal;
    private TextView tvCouponPrice;
    private TextView tvPayTrue;
    private LinearLayout linearPhone;
    private TextView tvOrderNum;
    private TextView tvOrderTime;
    private LinearLayout linearCount;
    private BaseObject userInfo;
    private ImageView ivImg;
    private TextView tvShopName;
    private TextView tvGoodsNum;
    private TextView tvPrice;
    private TextView tvOrignPrice;
    private String phone = "";
    private BaseObject object;
    private CountdownView countView;

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        orderId = getIntent().getStringExtra("orderId");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collage_order_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("订单详情");
        ivStatus = (ImageView) findViewById(R.id.ivStatus);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvGet = (TextView) findViewById(R.id.tvGet);
        tvDelete = (TextView) findViewById(R.id.tvDelete);
        tvVerify = (TextView) findViewById(R.id.tvVerify);
        tvTotalNum = (TextView) findViewById(R.id.tvTotalNum);
        tvCollageNum = (TextView) findViewById(R.id.tvCollageNum);
        tvSendType = (TextView) findViewById(R.id.tvSendType);
        tvGetTime = (TextView) findViewById(R.id.tvGetTime);
        tvWantTime = (TextView) findViewById(R.id.tvWantTime);
        tvContractPhone = (TextView) findViewById(R.id.tvContractPhone);
        tvStoreAddress = (TextView) findViewById(R.id.tvStoreAddress);
        linearInfo = (LinearLayout) findViewById(R.id.linearInfo);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        tvStoreName = (TextView) findViewById(R.id.tvStoreName);
        tvPackageFee = (TextView) findViewById(R.id.tvPackageFee);
        tvSendFee = (TextView) findViewById(R.id.tvSendFee);
        tvCoupon = (TextView) findViewById(R.id.tvCoupon);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvCouponPrice = (TextView) findViewById(R.id.tvCouponPrice);
        tvPayTrue = (TextView) findViewById(R.id.tvPayTrue);
        linearPhone = (LinearLayout) findViewById(R.id.linearPhone);
        linearCount = (LinearLayout) findViewById(R.id.linearCount);
        tvOrderNum = (TextView) findViewById(R.id.tvOrderNum);
        tvOrderTime = (TextView) findViewById(R.id.tvOrderTime);
        tvCancel = (TextView) findViewById(R.id.tvCancel);

        ivImg = (ImageView) findViewById(R.id.ivImg);
        tvShopName = (TextView) findViewById(R.id.tvShopName);
        tvGoodsNum = (TextView) findViewById(R.id.tvGoodsNum);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvOrignPrice = (TextView) findViewById(R.id.tvOrignPrice);

        countView = ((CountdownView) findViewById(R.id.countView));
        DynamicConfig.BackgroundInfo backgroundInfo = new DynamicConfig.BackgroundInfo();
        backgroundInfo.setColor(0xFFF39213)
                .setSize(30f)
                .setRadius(0f)
                .setShowTimeBgDivisionLine(false);
        DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
        dynamicConfigBuilder.setBackgroundInfo(backgroundInfo);
        countView.dynamicShow(dynamicConfigBuilder.build());

        userInfo = getUserInfo();

        mPresenter.getOrderInfo(orderId);

        initListener();

    }

    private void initListener() {
        /**
         * 打电话
         */
        linearPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantsUtils.callPhone(mContext, phone);
            }
        });
        /**
         * 取消订单
         */
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cancelOrder(orderId);
            }
        });

        /**
         * 接单
         */
        tvGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getOrder(orderId);
            }
        });

        /**
         * 删除
         */
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.delOrder(orderId);
            }
        });

        /**
         * 提货验证
         */
        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VerifyActivity.class);
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
    public void showResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData().getObject()){
                        object = baseBeanResult.getData().getObject();
                        initData(baseBeanResult.getData().getObject());
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
                    mRxManager.post("collage", "");
                    tvCancel.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.VISIBLE);
                    object.setOrderStatus("6");
                    ivStatus.setImageResource(R.mipmap.iv_order_complete);
                    tvStatus.setText("已取消");
                    initData(object);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showGetResult(BaseBeanResult baseBeanResult) {

    }

    @Override
    public void showDeleteResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult.getMsg()){
                showShortToast(baseBeanResult.getMsg());
            }
            if("1".equals(baseBeanResult.getCode())) {
                mRxManager.post("collage", "");
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initData(BaseObject object) {
        try{
            if(null!=object.getShippingType()){
                if("1".equals(object.getShippingType())){   //送货上门
                    tvSendType.setText("配送方式：送货上门");
                    if(null!=object.getShippingTime()){
                        tvWantTime.setText("期望时间："+object.getShippingTime());
                    }
                    tvGetTime.setVisibility(View.GONE);
                    tvContractPhone.setVisibility(View.GONE);
                    tvStoreAddress.setVisibility(View.GONE);
                }else{  //自提
                    tvSendType.setText("配送方式：自提");
                    if(null!=object.getShippingTime()){
                        tvGetTime.setText(object.getShippingTime());
                    }
                    tvWantTime.setVisibility(View.GONE);
                    linearInfo.setVisibility(View.GONE);
                }
            }
            if(null!=userInfo.getAddress()){
                tvStoreAddress.setText(userInfo.getAddress());
            }
            if(null!=userInfo.getImagePath()){
                Glide.with(mContext).load(Url.IP+userInfo.getImagePath()).centerCrop().into(ivAvatar);
            }
            if(null!=userInfo.getName()){
                tvStoreName.setText(userInfo.getName());
            }
            if(null!=object.getNeedNum()){
                tvTotalNum.setText(object.getNeedNum()+"人团");
                if(null!=object.getJoinNum()){
                    tvCollageNum.setText("还差"+(Integer.parseInt(object.getNeedNum())-Integer.parseInt(object.getJoinNum()))+"人");
                }
            }
            if(null!=object.getConsignee()){
                tvName.setText(object.getConsignee());
            }
            if(null!=object.getPhone()){
                phone = object.getPhone();
                tvPhone.setText(object.getPhone());
            }
            if(null!=object.getAddress()){
                tvAddress.setText(object.getAddress());
            }
            if(null!=object.getPackingFee()){
                tvPackageFee.setText(object.getPackingFee());
            }
            if(null!=object.getFreight()){
                tvSendFee.setText(object.getFreight());
            }
            if(null!=object.getDiscountDesc()){
                tvCoupon.setText(object.getDiscountDesc());
            }
            if(null!=object.getOrderNum()){
                tvOrderNum.setText("订单号码："+object.getOrderNum());
            }
            if(null!=object.getCreateTime()){
                tvOrderTime.setText("订单时间："+object.getCreateTime());
            }
            if(null!=object.getTotalPrice()){
                tvTotal.setText("共计￥"+object.getTotalPrice());
            }
            if(null!=object.getRealPrice()){
                tvPayTrue.setText("￥"+object.getRealPrice());
            }
            if(null!=object.getImagePath()){
                Glide.with(mContext).load(Url.IP+object.getImagePath()).centerCrop().into(ivImg);
            }
            if(null!=object.getTitle()){
                tvShopName.setText(object.getTitle());
            }
            if(null!=object.getPrice()){
                tvPrice.setText("￥"+object.getPrice());
            }
            if(null!=object.getOriginalPrice()){
                TvUtils.setLine(tvOrignPrice);
                tvOrignPrice.setText(object.getOriginalPrice());
            }
            if(null!=object.getGoodsNum()){
                tvGoodsNum.setText("1份/"+object.getGoodsNum());
            }
            if(null!=object.getOrderStatus()){
                switch(object.getOrderStatus()){
                    case "1":       //未付款
                        break;
                    case "2":       //付款中
                        break;
                    case "3":       //待成团
                        tvCancel.setVisibility(View.VISIBLE);
                        tvStatus.setText("未成团");
                        linearCount.setVisibility(View.VISIBLE);
                        tvCollageNum.setVisibility(View.VISIBLE);
                        if(null!=object.getCancelTime()){
                            countView.start(Long.parseLong(object.getCancelTime()));
                        }
                        break;
                    case "4":       //待提货/带配送
                        if(null!=object.getShippingType()){
                            if("1".equals(object.getShippingType())){ //送货上门
                                tvStatus.setText("送货中");
                            }else{      //自提
                                tvStatus.setText("待提货");
                            }
                        }
                        tvCancel.setVisibility(View.VISIBLE);
                        tvVerify.setVisibility(View.VISIBLE);
                        break;
                    case "5":       //已完成
                        tvStatus.setText("已完成");
                        tvCancel.setVisibility(View.GONE);
                        break;
                    case "6":       //已取消
                        tvStatus.setText("已取消");
                        tvDelete.setVisibility(View.VISIBLE);
                        tvCancel.setVisibility(View.GONE);
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
