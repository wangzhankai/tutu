package com.superpeer.tutuyoudian.adapter;

import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.tencent.map.geolocation.TencentPoi;

import java.util.List;

/**
 * Created by Administrator on 2018/11/10 0010.
 */

public class PositionAdapter extends BaseQuickAdapter {

    public PositionAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        TencentPoi poi = (TencentPoi) item;

        ((TextView) helper.getView(R.id.tvPos)).setText(poi.getAddress());
    }
}
