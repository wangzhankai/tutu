package com.superpeer.tutuyoudian.adapter;

import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.bean.BaseSearchResult;

import java.util.List;

public class SearchAdapter extends BaseQuickAdapter {

    public SearchAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        BaseSearchResult.SearchData bean = (BaseSearchResult.SearchData) item;
        ((TextView) helper.getView(R.id.tvPos)).setText(bean.getTitle());
        ((TextView) helper.getView(R.id.tvAddress)).setText(bean.getAddress());
    }
}
