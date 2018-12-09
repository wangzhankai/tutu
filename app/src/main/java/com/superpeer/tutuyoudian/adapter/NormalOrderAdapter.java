package com.superpeer.tutuyoudian.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.base_libs.utils.DateUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.listener.OnCancelListener;
import com.superpeer.tutuyoudian.listener.OnCompleteListener;
import com.superpeer.tutuyoudian.listener.OnDeleteListener;
import com.superpeer.tutuyoudian.listener.OnGetListener;
import com.superpeer.tutuyoudian.listener.OnItemListener;
import com.superpeer.tutuyoudian.listener.OnVerifyListener;
import com.superpeer.tutuyoudian.utils.TvUtils;

import java.util.List;

public class NormalOrderAdapter extends BaseQuickAdapter {

    private OnItemListener onItemListener;
    private OnCancelListener onCancelListener;
    private OnGetListener onGetListener;
    private OnVerifyListener onVerifyListener;
    private OnDeleteListener onDeleteListener;
    private OnCompleteListener onCompleteListener;

    public OnCompleteListener getOnCompleteListener() {
        return onCompleteListener;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public OnDeleteListener getOnDeleteListener() {
        return onDeleteListener;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public OnVerifyListener getOnVerifyListener() {
        return onVerifyListener;
    }

    public void setOnVerifyListener(OnVerifyListener onVerifyListener) {
        this.onVerifyListener = onVerifyListener;
    }

    public OnGetListener getOnGetListener() {
        return onGetListener;
    }

    public void setOnGetListener(OnGetListener onGetListener) {
        this.onGetListener = onGetListener;
    }

    public OnItemListener getOnItemListener() {
        return onItemListener;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public OnCancelListener getOnCancelListener() {
        return onCancelListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public NormalOrderAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, final int position) {
        BaseList bean = (BaseList) item;

        LinearLayout linearItem = (LinearLayout) helper.getView(R.id.linearItem);
        ImageView ivImg = (ImageView) helper.getView(R.id.ivImg);
        ImageView ivStatus = (ImageView) helper.getView(R.id.ivStatus);
        TextView tvPhone = ((TextView) helper.getView(R.id.tvPhone));
        TextView tvStatus = ((TextView) helper.getView(R.id.tvStatus));
        TextView tvType = ((TextView) helper.getView(R.id.tvType));
        TextView tvTotal = ((TextView) helper.getView(R.id.tvTotal));
        TextView tvPrice = ((TextView) helper.getView(R.id.tvPrice));

        TextView tvCancel = ((TextView) helper.getView(R.id.tvCancelOrder));
        TextView tvGet = (TextView) helper.getView(R.id.tvGetOrder);
        TextView tvComplete = (TextView) helper.getView(R.id.tvSend);
        TextView tvVerify = (TextView) helper.getView(R.id.tvVerify);
        TextView tvDelete = (TextView) helper.getView(R.id.tvDelete);

        switch (bean.getOrderStatus()){
            case "1":
                tvGet.setVisibility(View.GONE);
                tvComplete.setVisibility(View.GONE);
                tvVerify.setVisibility(View.GONE);
                tvCancel.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.iv_no_pay);
                tvStatus.setText("待付款");
                break;
            case "2":
                ivStatus.setImageResource(R.mipmap.iv_no_pay);
                tvStatus.setText("付款中");
                tvCancel.setVisibility(View.VISIBLE);
                break;
            case "3":
                ivStatus.setImageResource(R.mipmap.iv_order_readytaking);
                tvStatus.setText("待接单");
                tvCancel.setVisibility(View.VISIBLE);
                tvGet.setVisibility(View.VISIBLE);
                tvComplete.setVisibility(View.GONE);
                tvVerify.setVisibility(View.GONE);
                tvDelete.setVisibility(View.GONE);
                break;
            case "4":
                tvGet.setVisibility(View.GONE);
                tvDelete.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.iv_order_readyget);
                if(null!=bean.getShippingType()){
                    if("1".equals(bean.getShippingType())){ //送货上门
                        tvStatus.setText("送货中");
                        tvCancel.setVisibility(View.VISIBLE);
                        tvComplete.setVisibility(View.VISIBLE);
                    }else{      //自提
                        tvStatus.setText("待提货");
                        tvCancel.setVisibility(View.VISIBLE);
                        tvVerify.setVisibility(View.VISIBLE);
                        tvComplete.setVisibility(View.GONE);
                    }
                }
                break;
            case "5":
                tvGet.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
                tvComplete.setVisibility(View.GONE);
                tvVerify.setVisibility(View.GONE);
                tvDelete.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.iv_order_complete);
                tvStatus.setText("已完成");
                break;
            case "6":
                tvGet.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.iv_order_complete);
                tvStatus.setText("已取消");
                tvDelete.setVisibility(View.VISIBLE);
                break;
        }

        if(null!=bean.getConsignee()){
            tvPhone.setText(bean.getConsignee());
        }

        if(null!=bean.getCreateTime()){
            ((TextView) helper.getView(R.id.tvTime)).setText(DateUtils.getStringToDate(bean.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        }

        if(null!=bean.getOrderNum()){
            ((TextView) helper.getView(R.id.tvOrder)).setText(bean.getOrderNum());
        }

        if(null!=bean.getShippingType()){
            if("1".equals(bean.getShippingType())){
                tvType.setText("发货方式：送货上门");
            }else{
                tvType.setText("发货方式：自提");
            }
        }

        StringBuilder sb = new StringBuilder();
        if(null!=bean.getGoodsVos()&&bean.getGoodsVos().size()>0){
            if(null!=bean.getGoodsVos().get(0).getNum())
            ((TextView) helper.getView(R.id.tvNum)).setText("x"+bean.getGoodsVos().get(0).getNum());
            Glide.with(mContext).load(bean.getGoodsVos().get(0).getImagePath().contains("http")?bean.getGoodsVos().get(0).getImagePath(): Url.IP+bean.getGoodsVos().get(0).getImagePath())
                    .centerCrop().into(ivImg);
            ((TextView) helper.getView(R.id.tvName)).setText(bean.getGoodsVos().get(0).getName());

            if(null!=bean.getGoodsVos().get(0).getPrice()){
                TvUtils.setLine(tvPrice);
                tvPrice.setText("￥"+bean.getGoodsVos().get(0).getPrice());
            }

            sb.append("共"+bean.getGoodsVos().size()+"件,");
        }

        if(null!=bean.getPrice()){
            sb.append("合计"+bean.getPrice()+"元");
        }
        if(null!=bean.getFreight()){
            sb.append("（含运费"+bean.getFreight()+"元)");
        }
        tvTotal.setText(sb.toString());

        //取消订单
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelListener.onCancel(position);
            }
        });

        //接单
        tvGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetListener.onGet(position);
            }
        });

        //提货验证
        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVerifyListener.onVerify(position);
            }
        });

        //删除订单
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteListener.onDeleteListener(position);
            }
        });

        //订单送达
        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCompleteListener.onComplete(position);
            }
        });

        linearItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListener.onItem(position);
            }
        });

    }
}
