package com.superpeer.tutuyoudian.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

public class WithDrawRecordAdapter extends BaseQuickAdapter {

    public WithDrawRecordAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {

        BaseList bean = (BaseList) item;

        TextView tvType = (TextView) helper.getView(R.id.tvType);
        TextView tvMoney = (TextView) helper.getView(R.id.tvMoney);
        TextView tvTime = (TextView) helper.getView(R.id.tvTime);
        TextView tvStatus = (TextView) helper.getView(R.id.tvStatus);

        if(null!=bean.getWithdrawType()){
            if("1".equals(bean.getWithdrawType())){
                tvType.setText("银行卡");
            }else{
                tvType.setText("微信");
            }
        }
        if(null!=bean.getWithdrawMoney()){
            tvMoney.setText("￥"+bean.getWithdrawMoney());
        }
        if(null!=bean.getAuditTime()){
            tvTime.setText(bean.getAuditTime());
        }
        if(null!=bean.getStatus()){
            switch (bean.getStatus()){
                case "1":       //未审核
                    tvStatus.setText("提现中");
                    tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
                    break;
                case "2":       //已通过
                    tvStatus.setText("提现成功");
                    tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.grey3));
                    break;
                case "3":       //已拒绝
                    tvStatus.setText("提现失败");
                    tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    break;
            }
        }

    }
}
