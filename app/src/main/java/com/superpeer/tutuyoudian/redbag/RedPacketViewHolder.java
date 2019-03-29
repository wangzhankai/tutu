package com.superpeer.tutuyoudian.redbag;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.widget.CircleImageView;

/**
 * @author ChayChan
 * @description: 红包弹框
 * @date 2017/11/27  15:12
 */

public class RedPacketViewHolder implements View.OnClickListener {

    ImageView mIvClose;

    CircleImageView mIvAvatar;

    TextView mTvName;

    TextView mTvMsg;

    ImageView mIvOpen;

    TextView tvMoney;

    private Context mContext;
    private OnRedPacketDialogClickListener mListener;

    private int[] mImgResIds = new int[]{
            R.mipmap.icon_open_red_packet1,
            R.mipmap.icon_open_red_packet2,
            R.mipmap.icon_open_red_packet3,
            R.mipmap.icon_open_red_packet4,
            R.mipmap.icon_open_red_packet5,
            R.mipmap.icon_open_red_packet6,
            R.mipmap.icon_open_red_packet7,
            R.mipmap.icon_open_red_packet7,
            R.mipmap.icon_open_red_packet8,
            R.mipmap.icon_open_red_packet9,
            R.mipmap.icon_open_red_packet4,
            R.mipmap.icon_open_red_packet10,
            R.mipmap.icon_open_red_packet11,
    };
    private FrameAnimation mFrameAnimation;

    public RedPacketViewHolder(Context context, View view) {
        mContext = context;
        mIvClose = view.findViewById(R.id.iv_close);
        mIvAvatar = view.findViewById(R.id.iv_avatar);
        mTvName = view.findViewById(R.id.tv_name);
        mTvMsg = view.findViewById(R.id.tv_msg);
        mIvClose = view.findViewById(R.id.iv_close);
        mIvOpen = view.findViewById(R.id.iv_open);
        tvMoney = view.findViewById(R.id.tvMoney);

        mIvClose.setOnClickListener(this);
        mIvOpen.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                stopAnim();
                if (mListener != null) {
                    mListener.onCloseClick();
                }
                break;

            case R.id.iv_open:
                if (mFrameAnimation != null) {
                    //如果正在转动，则直接返回
                    return;
                }

                startAnim();

                if (mListener != null) {
                    mListener.onOpenClick();
                }
                break;
        }
    }

    public void setData(RedPacketEntity entity) {
        Glide.with(mContext).load(entity.avatar).centerCrop().into(mIvAvatar);
        mTvName.setText(entity.name);
        mTvMsg.setText(entity.remark);
    }

    public void startAnim() {
        mFrameAnimation = new FrameAnimation(mIvOpen, mImgResIds, 125, true);
        mFrameAnimation.setAnimationListener(new FrameAnimation.AnimationListener() {
            @Override
            public void onAnimationStart() {
                Log.i("", "start");
            }

            @Override
            public void onAnimationEnd() {
                Log.i("", "end");
            }

            @Override
            public void onAnimationRepeat() {
                Log.i("", "repeat");
            }

            @Override
            public void onAnimationPause() {
                mIvOpen.setBackgroundResource(R.mipmap.icon_open_red_packet1);
            }
        });
    }

    public void stopAnim() {
        if (mFrameAnimation != null) {
            mFrameAnimation.release();
            mFrameAnimation = null;
        }
    }

    public void setOnRedPacketDialogClickListener(OnRedPacketDialogClickListener listener) {
        mListener = listener;
    }

    public void getMoney(String receiveRedPacketMoney) {
        mIvOpen.setVisibility(View.GONE);
        tvMoney.setVisibility(View.VISIBLE);
        tvMoney.setText(receiveRedPacketMoney+"元");
    }
}
