package com.superpeer.tutuyoudian.adapter;

import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

public class DriverListAdapter extends BaseQuickAdapter {

    public DriverListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        BaseList bean = (BaseList) item;
        TextView tvUserName = (TextView) helper.getView(R.id.tvUserName);
        TextView tvPhone = (TextView) helper.getView(R.id.tvPhone);

        tvUserName.setText(bean.getUserName());
        tvPhone.setText(bean.getPhone());

    }
}
