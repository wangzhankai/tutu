package com.superpeer.tutuyoudian.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;

import java.util.List;

public class PayAdapter extends BaseQuickAdapter {

    private int selectPos = -1;

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public PayAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        String bean = (String) item;

        ((TextView) helper.getView(R.id.tvCategory)).setText(bean);
        ImageView ivSelect = ((ImageView) helper.getView(R.id.ivSelect));

        if(selectPos == position){
            ivSelect.setImageResource(R.mipmap.iv_select);
        }else{
            ivSelect.setImageResource(R.mipmap.iv_noselect);
        }
    }
}
