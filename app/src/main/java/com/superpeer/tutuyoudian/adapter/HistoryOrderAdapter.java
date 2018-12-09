package com.superpeer.tutuyoudian.adapter;

import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

public class HistoryOrderAdapter extends BaseQuickAdapter {
    public HistoryOrderAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        BaseList bean = (BaseList) item;

        ((TextView) helper.getView(R.id.tvOrderNum)).setText("订单编号："+bean.getOrderNum());
        ((TextView) helper.getView(R.id.tvOrderMoney)).setText(bean.getPrice());
        ((TextView) helper.getView(R.id.tvMoney)).setText(bean.getRunnerFee());
        ((TextView) helper.getView(R.id.tvTime)).setText("送达时间："+bean.getShippingTime());
    }
}
