package com.superpeer.tutuyoudian.adapter;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.bean.GoodsVos;
import com.superpeer.tutuyoudian.listener.OnCancelListener;
import com.superpeer.tutuyoudian.listener.OnCompleteListener;
import com.superpeer.tutuyoudian.listener.OnGetListener;
import com.superpeer.tutuyoudian.listener.OnItemListener;

import java.util.List;

public class DriverOrderAdapter extends BaseQuickAdapter {

    private OnItemListener onItemListener;
    private OnCancelListener onCancelListener;
    private OnCompleteListener onCompleteListener;
    private OnGetListener onGetListener;

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

    public OnCompleteListener getOnCompleteListener() {
        return onCompleteListener;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public DriverOrderAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, final int position) {
        BaseList bean = (BaseList) item;

        LinearLayout linearItem = (LinearLayout) helper.getView(R.id.linearItem);
        TextView tvName = ((TextView) helper.getView(R.id.tvName));
        TextView tvPhone = ((TextView) helper.getView(R.id.tvPhone));
        TextView tvStatus = ((TextView) helper.getView(R.id.tvStatus));
        TextView tvAddress = ((TextView) helper.getView(R.id.tvAddress));
        TextView tvTitle = ((TextView) helper.getView(R.id.tvTitle));
        TextView tvNum = ((TextView) helper.getView(R.id.tvNum));
        TextView tvPrice = ((TextView) helper.getView(R.id.tvPrice));
        TextView tvTotalNum = ((TextView) helper.getView(R.id.tvTotalNum));
        TextView tvTotalPrice = ((TextView) helper.getView(R.id.tvTotalPrice));
        TextView tvDriverFee = ((TextView) helper.getView(R.id.tvDriverFee));
        TextView tvQuit = ((TextView) helper.getView(R.id.tvQuit));
        TextView tvArrive = ((TextView) helper.getView(R.id.tvArrive));
        TextView tvGet = ((TextView) helper.getView(R.id.tvGet));
        TextView tvDelete = ((TextView) helper.getView(R.id.tvDelete));
        ImageView ivImg = ((ImageView)helper.getView(R.id.ivImg));
        ImageView ivStatus = ((ImageView)helper.getView(R.id.ivStatus));

        if(null!=bean.getConsignee()){
            tvName.setText(bean.getConsignee());
        }
        if(null!=bean.getPhone()){
            tvPhone.setText(bean.getPhone());
        }
        if(null!=bean.getAddress()){
            tvAddress.setText(bean.getAddress());
        }
        if(null!=bean.getGoodsVos()&&bean.getGoodsVos().size()>0){
            List<GoodsVos> goods = bean.getGoodsVos();
            if(null!=goods.get(0).getName()){
                tvTitle.setText(goods.get(0).getName());
            }
            if(null!=goods.get(0).getImagePath()){
                Glide.with(mContext).load(Url.IP+goods.get(0).getImagePath()).centerCrop().into(ivImg);
            }
            if(null!=goods.get(0).getNum()){
                tvNum.setText("x"+goods.get(0).getNum());
            }
            if(null!=goods.get(0).getPrice()){
                tvPrice.setText("￥"+goods.get(0).getPrice());
            }
        }
        if(null!=bean.getGoodsNum()){
            tvTotalNum.setText("共"+bean.getGoodsNum()+"件");
        }
        if(null!=bean.getTotalPrice()){
            tvTotalPrice.setText("合计：￥"+bean.getTotalPrice());
        }
        if(null!=bean.getFreight()){
            tvDriverFee.setText("(含运费"+bean.getFreight()+")");
        }
        tvGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetListener.onGet(position);
            }
        });
        tvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelListener.onCancel(position);
            }
        });
        tvArrive.setOnClickListener(new View.OnClickListener() {
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
        if(null!=bean.getOrderStatus()){
            switch(bean.getOrderStatus()){
                case "1":       //未付款
                    tvStatus.setText("未付款");
                    break;
                case "2":       //付款中
                    tvStatus.setText("付款中");
                    break;
                case "3":       //待成团
                    tvStatus.setText("待接单");
                    tvGet.setVisibility(View.VISIBLE);
                    break;
                case "4":       //待提货/带配送
                    if("1".equals(bean.getShippingType())){
                        tvStatus.setText("配送中");
                        tvQuit.setVisibility(View.VISIBLE);
                        tvArrive.setVisibility(View.VISIBLE);
                    }else{
                        tvStatus.setText("待提货");
                    }
                    break;
                case "5":       //已完成
                    tvStatus.setText("已完成");
                    tvDelete.setVisibility(View.VISIBLE);
                    break;
                case "6":       //已取消
                    tvStatus.setText("已取消");
                    break;
            }
        }
    }
}
