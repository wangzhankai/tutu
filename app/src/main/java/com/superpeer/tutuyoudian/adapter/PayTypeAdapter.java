package com.superpeer.tutuyoudian.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

/**
 * Created by Administrator on 2018/10/23 0023.
 */

public class PayTypeAdapter extends BaseQuickAdapter {

    private int selectPos = -1;

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public PayTypeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        BaseList bean = (BaseList) item;

        ImageView ivSelect = ((ImageView) helper.getView(R.id.ivSelect));

        if(null!=bean.getAccountType()){
            if("0".equals(bean.getAccountType())){
                ((TextView) helper.getView(R.id.tvName)).setText("微信");

                ((TextView) helper.getView(R.id.tvCard)).setText(bean.getBankName());
            }else{
                ((TextView) helper.getView(R.id.tvName)).setText(bean.getBankName());

                ((TextView) helper.getView(R.id.tvCard)).setText(bean.getBankCard());
            }
        }
        if(position == selectPos){
            ivSelect.setImageResource(R.mipmap.iv_select);
        }else{
            ivSelect.setImageResource(R.mipmap.iv_noselect);
        }
    }
}
