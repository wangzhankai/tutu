package com.superpeer.tutuyoudian.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

public class DialogCategoryAdapter extends BaseQuickAdapter {

    public DialogCategoryAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        BaseList bean = (BaseList) item;

        ImageView ivSelect = ((ImageView) helper.getView(R.id.ivSelect));
        ((TextView) helper.getView(R.id.tvCategory)).setText(bean.getName());

        if(bean.isCheck()){
            ivSelect.setImageResource(R.mipmap.iv_agree);
        }else{
            ivSelect.setImageResource(R.mipmap.iv_noagree);
        }

    }
}
