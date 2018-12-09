package com.superpeer.tutuyoudian.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.DayList;

import java.util.List;

/**
 * Created by Administrator on 2018/11/9 0009.
 */

public class DayAdapter extends BaseQuickAdapter {

    public DayAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        DayList bean = (DayList) item;

        ImageView ivSelect = ((ImageView) helper.getView(R.id.ivSelect));
        ((TextView) helper.getView(R.id.tvDay)).setText(bean.getDayName());
        if(bean.isChecked()){
            ivSelect.setVisibility(View.VISIBLE);
        }else{
            ivSelect.setVisibility(View.GONE);
        }
    }
}
