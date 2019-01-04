package com.superpeer.tutuyoudian.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.listener.OnCancelListener;
import com.superpeer.tutuyoudian.listener.OnDeleteListener;
import com.superpeer.tutuyoudian.listener.OnItemListener;
import com.superpeer.tutuyoudian.listener.OnVerifyListener;
import com.superpeer.tutuyoudian.utils.TvUtils;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;

public class CollageOrderAdapter extends BaseQuickAdapter {

    private OnItemListener onItemListener;
    private OnCancelListener onCancelListener;
    private OnVerifyListener onVerifyListener;
    private OnDeleteListener onDeleteListener;

    public OnCancelListener getOnCancelListener() {
        return onCancelListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public OnVerifyListener getOnVerifyListener() {
        return onVerifyListener;
    }

    public void setOnVerifyListener(OnVerifyListener onVerifyListener) {
        this.onVerifyListener = onVerifyListener;
    }

    public OnDeleteListener getOnDeleteListener() {
        return onDeleteListener;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public OnItemListener getOnItemListener() {
        return onItemListener;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public CollageOrderAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, final int position) {
        BaseList bean = (BaseList) item;

        LinearLayout linearItem = (LinearLayout) helper.getView(R.id.linearItem);
        LinearLayout linearCount = (LinearLayout) helper.getView(R.id.linearCount);
        TextView tvUserName = (TextView) helper.getView(R.id.tvUserName);
        TextView tvTotalNum = (TextView) helper.getView(R.id.tvTotalNum);
        TextView tvCollageNum = (TextView) helper.getView(R.id.tvCollageNum);
        ImageView ivStatus = (ImageView)helper.getView(R.id.ivStatus);
        TextView tvStatus = (TextView) helper.getView(R.id.tvStatus);
        ImageView ivImg = (ImageView)helper.getView(R.id.ivImg);
        TextView tvName = (TextView) helper.getView(R.id.tvName);
        TextView tvPrice = (TextView) helper.getView(R.id.tvPrice);
        TextView tvGoodsNum = (TextView) helper.getView(R.id.tvGoodsNum);
        TextView tvNum = (TextView) helper.getView(R.id.tvNum);
        TextView tvType = (TextView) helper.getView(R.id.tvType);
        TextView tvCancel = (TextView) helper.getView(R.id.tvCancelOrder);
        TextView tvGet = (TextView) helper.getView(R.id.tvGetOrder);
        TextView tvVerify = (TextView) helper.getView(R.id.tvVerify);
        TextView tvDelete = (TextView) helper.getView(R.id.tvDelete);

        final CountdownView countView = ((CountdownView) helper.getView(R.id.countView));
        DynamicConfig.BackgroundInfo backgroundInfo = new DynamicConfig.BackgroundInfo();
        backgroundInfo.setColor(0xFFF39213)
                .setSize(30f)
                .setRadius(0f)
                .setShowTimeBgDivisionLine(false);
        DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
        dynamicConfigBuilder.setBackgroundInfo(backgroundInfo);
        countView.dynamicShow(dynamicConfigBuilder.build());

        linearItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListener.onItem(position);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelListener.onCancel(position);
            }
        });

        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVerifyListener.onVerify(position);
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteListener.onDeleteListener(position);
            }
        });

        if(null!=bean.getConsignee()){
            tvUserName.setText(bean.getConsignee());
        }
        if(null!=bean.getNeedNum()){
            tvTotalNum.setText(bean.getNeedNum()+"人团");
            if(null!=bean.getJoinNum()){
                tvCollageNum.setText("还差"+(Integer.parseInt(bean.getNeedNum())-Integer.parseInt(bean.getJoinNum()))+"人");
            }
            if(bean.getNeedNum().equals(bean.getJoinNum())){
                tvCollageNum.setVisibility(View.GONE);
            }
        }
        if(null!=bean.getImagePath()){
            Glide.with(mContext).load(Url.IP+bean.getImagePath()).centerCrop().into(ivImg);
        }
        if(null!=bean.getTitle()){
            tvName.setText(bean.getTitle());
        }
        if(null!=bean.getPrice()){
            tvPrice.setText("￥"+bean.getPrice());
        }
        if(null!=bean.getGoodsNum()){
            tvGoodsNum.setText("1份/"+bean.getGoodsNum());
        }
        if(null!=bean.getOriginalPrice()){
            TvUtils.setLine(tvNum);
            tvNum.setText("￥"+bean.getOriginalPrice());
        }
        if(null!=bean.getShippingType()){
            if("0".equals(bean.getShippingType())){ //送货
                tvType.setText("发货方式：送货上门");
            }else{
                tvType.setText("发货方式：自提");
            }
        }
        if(null!=bean.getOrderStatus()){
            switch (bean.getOrderStatus()){
                case "1":
                    tvCancel.setVisibility(View.VISIBLE);
                    tvGet.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.GONE);
                    ivStatus.setImageResource(R.mipmap.iv_no_pay);
                    tvStatus.setText("待付款");
                    break;
                case "2":
                    ivStatus.setImageResource(R.mipmap.iv_no_pay);
                    tvStatus.setText("付款中");
                    tvCancel.setVisibility(View.VISIBLE);
                    tvGet.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.GONE);
                    break;
                case "3":
                    ivStatus.setImageResource(R.mipmap.iv_order_readytaking);
                    tvStatus.setText("待成团");
                    tvCancel.setVisibility(View.VISIBLE);
                    tvGet.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.GONE);
                    linearCount.setVisibility(View.VISIBLE);
                    if(null!=bean.getCancelTime()){
                        countView.start(Long.parseLong(bean.getCancelTime()));
                    }
                    break;
                case "4":
                    ivStatus.setImageResource(R.mipmap.iv_order_readyget);
                    if(null!=bean.getShippingType()){
                        if("1".equals(bean.getShippingType())){ //送货上门
                            tvStatus.setText("送货中");
                            tvCancel.setVisibility(View.VISIBLE);
                            tvGet.setVisibility(View.VISIBLE);
                            tvDelete.setVisibility(View.GONE);
                            tvVerify.setVisibility(View.GONE);
                        }else{      //自提
                            tvStatus.setText("待提货");
                            tvCancel.setVisibility(View.VISIBLE);
                            tvVerify.setVisibility(View.VISIBLE);
                            tvGet.setVisibility(View.GONE);
                            tvDelete.setVisibility(View.GONE);
                        }
                    }
                    break;
                case "5":
                    tvCancel.setVisibility(View.GONE);
                    tvGet.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.GONE);
                    ivStatus.setImageResource(R.mipmap.iv_order_complete);
                    tvStatus.setText("已完成");
                    break;
                case "6":
                case "7":
                    tvCancel.setVisibility(View.GONE);
                    tvGet.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.VISIBLE);
                    ivStatus.setImageResource(R.mipmap.iv_order_complete);
                    tvStatus.setText("已取消");
                    tvDelete.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }
}
