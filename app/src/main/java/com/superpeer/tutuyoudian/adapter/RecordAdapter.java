package com.superpeer.tutuyoudian.adapter;

import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class RecordAdapter extends BaseQuickAdapter {

    public RecordAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        BaseList bean = (BaseList) item;

        TextView tvType = ((TextView) helper.getView(R.id.tvType));
        if("0".equals(bean.getWithdrawType())){
            tvType.setText("微信");
        }else{
            tvType.setText("银行卡");
        }
        ((TextView) helper.getView(R.id.tvTime)).setText(bean.getCreateTime());
        ((TextView) helper.getView(R.id.tvMoney)).setText("￥"+bean.getWithdrawMoney());
        TextView tvStatus = ((TextView) helper.getView(R.id.tvStatus));
        if("0".equals(bean.getWithdrawStatus())){
            tvStatus.setText("提现中");
        }else{
            tvStatus.setText("提现成功");
        }


    }
}
