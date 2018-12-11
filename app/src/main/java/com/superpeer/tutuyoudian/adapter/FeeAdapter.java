package com.superpeer.tutuyoudian.adapter;

import android.graphics.Paint;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;

import org.w3c.dom.Text;

import java.util.List;

public class FeeAdapter extends BaseQuickAdapter {

    private int selectPos = 0;

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public FeeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        BaseList bean = (BaseList) item;
        TextView tvTime = (TextView) helper.getView(R.id.tvTime);
        TextView tvOrignPrice = (TextView) helper.getView(R.id.tvOrignPrice);
        TextView tvPrice = (TextView) helper.getView(R.id.tvPrice);
        TextView tvDesc = (TextView) helper.getView(R.id.tvDesc);
        TextView tvView = (TextView) helper.getView(R.id.tvView);

        ImageView ivSelect = (ImageView) helper.getView(R.id.ivSelect);

        if(null!=bean.getDescription()){
            tvTime.setText(bean.getDescription());
        }
        if(position==selectPos){
            ivSelect.setImageResource(R.mipmap.iv_select);
        }else{
            ivSelect.setImageResource(R.mipmap.iv_noselect);
        }
        if(null!=bean.getType()){
            switch (bean.getType()){
                case "0":
                    tvOrignPrice.setVisibility(View.GONE);
                    tvView.setVisibility(View.VISIBLE);
                    if(null!=bean.getPrice()){
                        tvPrice.setText("￥"+bean.getPrice());
                    }
                    break;
                case "1":
                    if(null!=bean.getPrice()){
                        tvOrignPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
                        tvOrignPrice.setText("￥"+bean.getPrice());
                    }
                    if(null!=bean.getDiscountPrice()){
                        tvPrice.setText("￥"+bean.getDiscountPrice());
                    }
                    if(null!=bean.getDiscount()){
                        tvDesc.setText("限时"+bean.getDiscount()+"折");
                    }
                    break;
                case "2":
                    tvOrignPrice.setVisibility(View.GONE);
                    tvView.setVisibility(View.VISIBLE);
                    if(null!=bean.getPrice()){
                        tvPrice.setText("￥"+bean.getPrice());
                    }
                    if(null!=bean.getKeep()){
                        tvDesc.setText("赠送"+bean.getKeep()+"个月");
                    }
                    break;
            }
        }


    }
}
