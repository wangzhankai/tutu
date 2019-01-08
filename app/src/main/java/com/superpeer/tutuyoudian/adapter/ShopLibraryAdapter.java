package com.superpeer.tutuyoudian.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.listener.OnAddListener;
import com.superpeer.tutuyoudian.listener.OnEditListener;
import com.superpeer.tutuyoudian.listener.OnOperListener;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

public class ShopLibraryAdapter extends BaseQuickAdapter {

    private OnEditListener onEditListener;
    private OnOperListener onOperListener;

    public OnOperListener getOnOperListener() {
        return onOperListener;
    }

    public void setOnOperListener(OnOperListener onOperListener) {
        this.onOperListener = onOperListener;
    }

    public OnEditListener getOnEditListener() {
        return onEditListener;
    }

    public void setOnEditListener(OnEditListener onEditListener) {
        this.onEditListener = onEditListener;
    }

    public ShopLibraryAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, final int position) {
        BaseList bean = (BaseList) item;

        ImageView ivImg = ((ImageView) helper.getView(R.id.ivImg));
        ImageView ivSelect = ((ImageView) helper.getView(R.id.ivSelect));
        TextView tvModify = (TextView) helper.getView(R.id.tvModify);
        TextView tvAddOrRemove = (TextView) helper.getView(R.id.tvAddOrRemove);

        if(null!=bean.getImagePath())
            Glide.with(mContext).load(bean.getImagePath().contains("http")?bean.getImagePath(): Url.IP+bean.getImagePath()).centerCrop().into(ivImg);
        StringBuilder sb = new StringBuilder();
        if(null!=bean.getName()){
            sb.append(bean.getName());
        }
        if(null!=bean.getSpecifications()){
            sb.append("-"+bean.getSpecifications());
        }
        ((TextView) helper.getView(R.id.tvTitle)).setText(sb.toString());
        if(null!=bean.getPrice()){
            ((TextView) helper.getView(R.id.tvPrice)).setText("￥"+numberFormat.format(new BigDecimal(bean.getPrice())));
        }else{
            ((TextView) helper.getView(R.id.tvPrice)).setText("￥"+numberFormat.format(new BigDecimal("0")));
        }

        //修改
        tvModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditListener.OnEditListener(position);
            }
        });
        //
        tvAddOrRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperListener.onOperListener(position);
            }
        });

        if(bean.isChecked()){
            tvAddOrRemove.setText("删除");
            ivSelect.setImageResource(R.mipmap.iv_agree);
        }else{
            tvAddOrRemove.setText("添加");
            ivSelect.setImageResource(R.mipmap.iv_noagree);
        }
    }
}
