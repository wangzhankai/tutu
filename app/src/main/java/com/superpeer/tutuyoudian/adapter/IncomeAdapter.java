package com.superpeer.tutuyoudian.adapter;

import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class IncomeAdapter extends BaseQuickAdapter {
    public IncomeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        BaseList bean = (BaseList) item;

        TextView tvType = ((TextView) helper.getView(R.id.tvType));
        TextView tvOrder = ((TextView) helper.getView(R.id.tvOrder));
        TextView tvMoney = ((TextView) helper.getView(R.id.tvMoney));

        if("0".equals(bean.getType())){
            tvType.setText("收入");
        }
        if(null!=bean.getOrderNum()){
            tvOrder.setText(bean.getOrderNum());
        }
        if(null!=bean.getTransactionMoney()){
            tvMoney.setText("+"+bean.getTransactionMoney());
        }
    }
}
