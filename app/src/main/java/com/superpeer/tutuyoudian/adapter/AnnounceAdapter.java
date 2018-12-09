package com.superpeer.tutuyoudian.adapter;

import android.view.View;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class AnnounceAdapter extends BaseQuickAdapter {

    public AnnounceAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        BaseList bean = (BaseList) item;
        try {
            ((TextView) helper.getView(R.id.tvDesc)).setText(bean.getTitle());
            ((TextView) helper.getView(R.id.tvTime)).setText(bean.getPubdate());
            TextView tvNum = ((TextView) helper.getView(R.id.tvNum));

            if(null!=bean.getReadFlag()){
                if("0".equals(bean.getReadFlag())){
                    tvNum.setVisibility(View.VISIBLE);
                }else{
                    tvNum.setVisibility(View.GONE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
