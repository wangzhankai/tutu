package com.superpeer.tutuyoudian.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

/**
 * Created by Administrator on 2018/11/8 0008.
 */

public class VerifyAdapter extends BaseQuickAdapter {

    public VerifyAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {

        BaseList bean = (BaseList) item;

        ImageView ivImg = ((ImageView) helper.getView(R.id.ivImg));
        ((TextView) helper.getView(R.id.tvTitle)).setText(bean.getName());
        ((TextView) helper.getView(R.id.tvNum)).setText("X"+bean.getNum());

        Glide.with(mContext).load(bean.getImagePath().contains("http")?bean.getImagePath(): Url.IP+bean.getImagePath()).centerCrop().into(ivImg);
    }
}
