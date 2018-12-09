package com.superpeer.tutuyoudian.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.BaseViewHolder;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Url;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.listener.OnAddPublishListener;
import com.superpeer.tutuyoudian.listener.OnDeleteListener;
import com.superpeer.tutuyoudian.listener.OnEditListener;
import com.superpeer.tutuyoudian.listener.OnEndListener;

import java.math.BigDecimal;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;

/**
 * Created by Administrator on 2018/10/23 0023.
 */

public class CollageSetAdapter extends BaseQuickAdapter {

    private OnDeleteListener onDeleteListener;
    private OnEditListener onEditListener;
    private OnEndListener onEndListener;
    private OnAddPublishListener onAddPublishListener;
    private long lastTime;
    private long remainTime;

    public OnAddPublishListener getOnAddPublishListener() {
        return onAddPublishListener;
    }

    public void setOnAddPublishListener(OnAddPublishListener onAddPublishListener) {
        this.onAddPublishListener = onAddPublishListener;
    }

    public OnEndListener getOnEndListener() {
        return onEndListener;
    }

    public void setOnEndListener(OnEndListener onEndListener) {
        this.onEndListener = onEndListener;
    }

    public OnEditListener getOnEditListener() {
        return onEditListener;
    }

    public void setOnEditListener(OnEditListener onEditListener) {
        this.onEditListener = onEditListener;
    }

    public OnDeleteListener getOnDeleteListener() {
        return onDeleteListener;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public CollageSetAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, final int position) {
        BaseList bean = (BaseList) item;
        TextView tvTips = ((TextView) helper.getView(R.id.tvTips));
        TextView tvOrignPrice = ((TextView) helper.getView(R.id.tvOrignPrice));
        TextView tvPublish = ((TextView) helper.getView(R.id.tvPublish));
        ImageView ivImg = ((ImageView) helper.getView(R.id.ivImg));

        if(null!=bean.getImgPath())
        Glide.with(mContext).load(Url.IP+bean.getImgPath()).into(ivImg);

        ((TextView) helper.getView(R.id.tvNum)).setText(bean.getNeedNum());
        ((TextView) helper.getView(R.id.tvTitle)).setText(bean.getTitle());
        ((TextView) helper.getView(R.id.tvDesc)).setText(bean.getGroupDesc());
        ((TextView) helper.getView(R.id.tvPrice)).setText("￥"+bean.getGroupPrice());
        tvOrignPrice.setText("￥"+new BigDecimal(bean.getPrice()).multiply(new BigDecimal(bean.getGoodsNum())));
        tvOrignPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        ((TextView) helper.getView(R.id.tvTotalNum)).setText("已团"+bean.getSuccessNum()+"件");

        final CountdownView countView = ((CountdownView) helper.getView(R.id.countView));
        DynamicConfig.BackgroundInfo backgroundInfo = new DynamicConfig.BackgroundInfo();
        backgroundInfo.setColor(0xFFF39213)
                .setSize(30f)
                .setRadius(0f)
                .setShowTimeBgDivisionLine(false);
        DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
        dynamicConfigBuilder.setBackgroundInfo(backgroundInfo);
        countView.dynamicShow(dynamicConfigBuilder.build());

        try {
            countView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    countView.start(remainTime - (System.currentTimeMillis() - lastTime));
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    remainTime = countView.getRemainTime();
                    lastTime = System.currentTimeMillis();
                    countView.stop();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        //删除
        ((TextView) helper.getView(R.id.tvDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteListener.onDeleteListener(position);
            }
        });

        //编辑
        ((TextView) helper.getView(R.id.tvEdit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditListener.OnEditListener(position);
            }
        });
        //新增
        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPublishListener.onAddListener(position);
            }
        });
        if(null!=bean.getRemainingTime()){
            countView.setVisibility(View.VISIBLE);
            countView.start(Long.parseLong(bean.getRemainingTime()));
        }

        countView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                onEditListener.OnEditListener(position);
            }
        });
    }
}
