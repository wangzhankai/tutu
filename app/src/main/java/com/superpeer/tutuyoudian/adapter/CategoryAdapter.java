package com.superpeer.tutuyoudian.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

/**
 * Created by Administrator on 2018/10/27 0027.
 */

public class CategoryAdapter extends BaseQuickAdapter {

    private int selectPos = 0;

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public CategoryAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {

        BaseList bean = (BaseList) item;

        LinearLayout linearItem = ((LinearLayout) helper.getView(R.id.linearItem));
        TextView tvCategory = ((TextView) helper.getView(R.id.tvCategory));

        tvCategory.setText(bean.getName());

        if(position == selectPos){
            linearItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            tvCategory.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
        }else{
            linearItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.backgroundLight));
            tvCategory.setTextColor(ContextCompat.getColor(mContext, R.color.grey3));
        }

    }
}
