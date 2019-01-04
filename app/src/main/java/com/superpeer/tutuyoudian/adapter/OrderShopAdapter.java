package com.superpeer.tutuyoudian.adapter;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.bean.GoodsVos;

import java.util.List;

public class OrderShopAdapter extends BaseQuickAdapter {

    public OrderShopAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        GoodsVos bean = (GoodsVos) item;

        ImageView ivImg = (ImageView) helper.getView(R.id.ivImg);
        TextView tvName = (TextView) helper.getView(R.id.tvName);
        TextView tvPrice = (TextView) helper.getView(R.id.tvPrice);
        TextView tvNum = (TextView) helper.getView(R.id.tvNum);

        if(null!=bean.getImagePath()){
            Glide.with(mContext).load(Url.IP+bean.getImagePath()).centerCrop().into(ivImg);
        }
        if(null!=bean.getName()){
            tvName.setText(bean.getName());
        }
        if(null!=bean.getPrice()){
            tvPrice.setText("￥"+bean.getPrice());
        }
        if(null!=bean.getNum()){
            tvNum.setText("x"+bean.getNum());
        }
    }
}
