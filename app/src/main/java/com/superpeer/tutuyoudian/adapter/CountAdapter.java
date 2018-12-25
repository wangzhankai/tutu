package com.superpeer.tutuyoudian.adapter;

import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

public class CountAdapter extends BaseQuickAdapter {

    public CountAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {

        BaseList bean = (BaseList) item;

        TextView tvRank = (TextView) helper.getView(R.id.tvRank);
        TextView tvTitle = (TextView) helper.getView(R.id.tvTitle);
        TextView tvNum = (TextView) helper.getView(R.id.tvNum);

        tvRank.setText((position+1)+"");
        switch (position){
            case 0:
                tvRank.setBackgroundResource(R.drawable.bg_orange_point);
                break;
            case 1:
                tvRank.setBackgroundResource(R.drawable.bg_orange_point);
                break;
            case 2:
                tvRank.setBackgroundResource(R.drawable.bg_orange_point);
                break;
        }
        if(null!=bean.getGoodsName()){
            tvTitle.setText(bean.getGoodsName());
        }
        if(null!=bean.getNum()){
            tvNum.setText(bean.getNum());
        }

    }
}
