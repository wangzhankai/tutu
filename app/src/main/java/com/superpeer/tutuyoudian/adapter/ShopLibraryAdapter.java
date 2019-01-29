package com.superpeer.tutuyoudian.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
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
import com.superpeer.tutuyoudian.listener.OnImgListener;
import com.superpeer.tutuyoudian.listener.OnOperListener;
import com.superpeer.tutuyoudian.listener.OnUpdatePriceListener;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

public class ShopLibraryAdapter extends BaseQuickAdapter {

    private OnEditListener onEditListener;
    private OnOperListener onOperListener;
    private OnUpdatePriceListener onUpdatePriceListener;
    private OnImgListener onImgListener;

    public OnImgListener getOnImgListener() {
        return onImgListener;
    }

    public void setOnImgListener(OnImgListener onImgListener) {
        this.onImgListener = onImgListener;
    }

    public OnUpdatePriceListener getOnUpdatePriceListener() {
        return onUpdatePriceListener;
    }

    public void setOnUpdatePriceListener(OnUpdatePriceListener onUpdatePriceListener) {
        this.onUpdatePriceListener = onUpdatePriceListener;
    }

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
        final EditText tvPrice = ((EditText) helper.getView(R.id.tvPrice));

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

        if(null!=bean.getVipPrice()&&!"".equals(bean.getVipPrice())){
            tvPrice.setText(numberFormat.format(new BigDecimal(bean.getVipPrice())));
        }else {
            if (null != bean.getPrice()&&!"".equals(bean.getPrice())) {
                tvPrice.setText(numberFormat.format(new BigDecimal(bean.getPrice())));
            } else {
                tvPrice.setText(numberFormat.format(new BigDecimal("0")));
            }
        }

        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImgListener.onImgListener(position);
            }
        });
        /*tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdatePriceListener.onUpdatePrice(position, tvPrice.getText().toString().trim());
            }
        });*/

        tvPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    onUpdatePriceListener.onUpdatePrice(position, tvPrice.getText().toString().trim());
                }
            }
        });

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
            tvAddOrRemove.setText("取消");
            tvAddOrRemove.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            tvAddOrRemove.setBackgroundResource(R.drawable.bg_orange_circle_stroke);
            ivSelect.setImageResource(R.mipmap.iv_agree);
        }else{
            tvAddOrRemove.setText("添加");
            tvAddOrRemove.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvAddOrRemove.setBackgroundResource(R.drawable.bg_orange_circle);
            ivSelect.setImageResource(R.mipmap.iv_noagree);
        }

    }
}
