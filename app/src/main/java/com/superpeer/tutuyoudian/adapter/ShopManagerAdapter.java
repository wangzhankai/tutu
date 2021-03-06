package com.superpeer.tutuyoudian.adapter;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.listener.OnAddListener;
import com.superpeer.tutuyoudian.listener.OnDownListener;
import com.superpeer.tutuyoudian.listener.OnEditListener;
import com.superpeer.tutuyoudian.listener.OnImgListener;
import com.superpeer.tutuyoudian.listener.OnOperListener;
import com.superpeer.tutuyoudian.listener.OnStockListener;
import com.superpeer.tutuyoudian.listener.OnSubListener;
import com.superpeer.tutuyoudian.listener.OnUpListener;
import com.superpeer.tutuyoudian.listener.OnUpOrDownListener;
import com.superpeer.tutuyoudian.listener.OnUpdatePriceListener;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/10/27 0027.
 */

public class ShopManagerAdapter extends BaseQuickAdapter {

    private boolean isSort;
    private String type = "";

    private OnUpListener onUpListener;
    private OnDownListener onDownListener;
    private OnSubListener onSubListener;
    private OnAddListener onAddListener;
    private OnStockListener onStockListener;
    private OnEditListener onEditListener;
    private OnUpOrDownListener onUpOrDownListener;
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

    public OnUpOrDownListener getOnUpOrDownListener() {
        return onUpOrDownListener;
    }

    public void setOnUpOrDownListener(OnUpOrDownListener onUpOrDownListener) {
        this.onUpOrDownListener = onUpOrDownListener;
    }

    public OnEditListener getOnEditListener() {
        return onEditListener;
    }

    public void setOnEditListener(OnEditListener onEditListener) {
        this.onEditListener = onEditListener;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OnStockListener getOnStockListener() {
        return onStockListener;
    }

    public void setOnStockListener(OnStockListener onStockListener) {
        this.onStockListener = onStockListener;
    }

    public OnSubListener getOnSubListener() {
        return onSubListener;
    }

    public void setOnSubListener(OnSubListener onSubListener) {
        this.onSubListener = onSubListener;
    }

    public OnAddListener getOnAddListener() {
        return onAddListener;
    }

    public void setOnAddListener(OnAddListener onAddListener) {
        this.onAddListener = onAddListener;
    }

    public OnUpListener getOnUpListener() {
        return onUpListener;
    }

    public void setOnUpListener(OnUpListener onUpListener) {
        this.onUpListener = onUpListener;
    }

    public OnDownListener getOnDownListener() {
        return onDownListener;
    }

    public void setOnDownListener(OnDownListener onDownListener) {
        this.onDownListener = onDownListener;
    }

    public boolean isSort() {
        return isSort;
    }

    public void setSort(boolean sort) {
        isSort = sort;
    }

    public ShopManagerAdapter(int layoutResId, List data, boolean isSort) {
        super(layoutResId, data);
        this.isSort = isSort;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, final int position) {

        BaseList bean = (BaseList) item;

        LinearLayout linearSort = (LinearLayout) helper.getView(R.id.linearSort);
        ImageView ivUp = (ImageView) helper.getView(R.id.ivUp);
        ImageView ivDown = (ImageView) helper.getView(R.id.ivDown);

        ImageView ivImg = ((ImageView) helper.getView(R.id.ivImg));
        ImageView ivSub = ((ImageView) helper.getView(R.id.ivSub));
        ImageView ivAdd = ((ImageView) helper.getView(R.id.ivAdd));
        final EditText etNum = ((EditText) helper.getView(R.id.etNum));

        TextView tvModify = (TextView) helper.getView(R.id.tvModify);
        TextView tvAddOrRemove = (TextView) helper.getView(R.id.tvAddOrRemove);
        final EditText tvPrice = ((EditText) helper.getView(R.id.tvPrice));

        if("0".equals(type)){
            tvAddOrRemove.setText("上架");
            tvAddOrRemove.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvAddOrRemove.setBackgroundResource(R.drawable.bg_orange_circle);
        }else{
            tvAddOrRemove.setText("下架");
            tvAddOrRemove.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            tvAddOrRemove.setBackgroundResource(R.drawable.bg_orange_circle_stroke);
        }

        //设置库存和价格
        tvModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditListener.OnEditListener(position);
            }
        });
        //上下架
        tvAddOrRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpOrDownListener.onUpOrDownListener(position, type);
            }
        });


        if(isSort){
            linearSort.setVisibility(View.VISIBLE);
        }else{
            linearSort.setVisibility(View.GONE);
        }

        ivUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpListener.onUp(position);
            }
        });

        ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDownListener.onDown(position);
            }
        });

        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImgListener.onImgListener(position);
            }
        });

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
        if(null!=bean.getStock()){
            if(bean.getStock().contains("-")){
                ((TextView) helper.getView(R.id.tvRest)).setText("库存0");
            }else{
                ((TextView) helper.getView(R.id.tvRest)).setText("库存"+bean.getStock());
            }
        }else{
            ((TextView) helper.getView(R.id.tvRest)).setText("库存0");
        }
        if(null!=bean.getVipPrice()&&!"".equals(bean.getVipPrice())){
            tvPrice.setText(numberFormat.format(new BigDecimal(bean.getVipPrice())));
        }else {
            if (null != bean.getPrice()&&!"".equals(bean.getPrice())) {
                tvPrice.setText(numberFormat.format(new BigDecimal(bean.getPrice())));
            } else {
                tvPrice.setText(numberFormat.format(new BigDecimal("0")));
            }
        }

        tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdatePriceListener.onUpdatePrice(position, tvPrice.getText().toString().trim());
            }
        });

        tvPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    onUpdatePriceListener.onUpdatePrice(position, tvPrice.getText().toString().trim());
                }
            }
        });

        ivSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onSubListener.onSub(position, etNum.getText().toString().trim());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddListener.onAdd(position, etNum.getText().toString().trim());
            }
        });

        etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onStockListener.onStock(position, s.toString());
            }
        });
    }
}
