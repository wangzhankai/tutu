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
    private String type = "0";

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public CategoryAdapter(int layoutResId, List data, String type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {

        BaseList bean = (BaseList) item;

        LinearLayout linearItem = ((LinearLayout) helper.getView(R.id.linearItem));
        TextView tvCategory = ((TextView) helper.getView(R.id.tvCategory));
        TextView tvNum = ((TextView) helper.getView(R.id.tvNum));

        StringBuilder sb = new StringBuilder();
        if(null!=bean.getName()){
//            sb.append(bean.getName());
            tvCategory.setText(bean.getName());
        }
        if(null!=bean.getNum()&&"1".equals(type)){
//            sb.append("("+bean.getNum()+")");
            tvNum.setText("("+bean.getNum()+")");
        }
//        tvCategory.setText(sb.toString());

        if(position == selectPos){
            linearItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            tvCategory.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            tvNum.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
        }else{
            linearItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.backgroundLight));
            tvCategory.setTextColor(ContextCompat.getColor(mContext, R.color.grey3));
            tvNum.setTextColor(ContextCompat.getColor(mContext, R.color.grey3));
        }

    }
}
