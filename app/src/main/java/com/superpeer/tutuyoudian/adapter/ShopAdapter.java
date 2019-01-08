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
 * Created by Administrator on 2018/11/5 0005.
 */

public class ShopAdapter extends BaseQuickAdapter {

    private int selectPos = -1;

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public ShopAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        BaseList bean = (BaseList) item;

        ImageView ivImg = ((ImageView) helper.getView(R.id.ivImg));
        ImageView ivSelect = ((ImageView) helper.getView(R.id.ivSelect));
        if(null!=bean.getImagePath())
            Glide.with(mContext).load(bean.getImagePath().contains("http")?bean.getImagePath(): Url.IP+bean.getImagePath()).centerCrop().into(ivImg);
        ((TextView) helper.getView(R.id.tvTitle)).setText(bean.getName());
        ((TextView) helper.getView(R.id.tvPrice)).setText(bean.getPrice());

        if(position == selectPos){
            ivSelect.setImageResource(R.mipmap.iv_agree);
        }else{
            ivSelect.setImageResource(R.mipmap.iv_noagree);
        }
    }
}
