package com.superpeer.tutuyoudian.activity.collageadd;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.selectshop.SelectShopActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.utils.TvUtils;

import org.w3c.dom.Text;

import java.math.BigDecimal;

import rx.functions.Action1;

public class AddCollageActivity extends BaseActivity<AddCollagePresenter, AddCollageModel> implements AddCollageContract.View {

    private TextView tvStoreName;
    private EditText etTitle;
    private EditText etDesc;
    private EditText etOrignPrice;
    private EditText etCollagePrice;
    private EditText etCollageNum;
    private EditText etPeopleNum;
    private TextView tvSelf;
    private TextView tvSend;
    private EditText etKeepTime;
    private EditText etNoGroupTime;
    private EditText etNoGetTime;
    private EditText etNoSendTime;
    private TextView tvPublish;
    private TextView tvPublishAgain;

    private String goodsId = "";
    private int type = 0;       //0-自提 1-配送
    private LinearLayout linearSelf;
    private LinearLayout linearSend;
    private ImageView ivSelf;
    private ImageView ivSend;
    private String groupId = "";
    private EditText etGoodsNum;

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        groupId = getIntent().getStringExtra("groupId");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_collage;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("新增拼团商品");

        tvStoreName = (TextView) findViewById(R.id.tvStoreName);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDesc = (EditText) findViewById(R.id.etDesc);
        etOrignPrice = (EditText) findViewById(R.id.etOrignPrice);
        etCollagePrice = (EditText) findViewById(R.id.etCollagePrice);
        etCollageNum = (EditText) findViewById(R.id.etCollageNum);
        etPeopleNum = (EditText) findViewById(R.id.etPeopleNum);
        etGoodsNum = (EditText) findViewById(R.id.etGoodsNum);
        tvSelf = (TextView) findViewById(R.id.tvSelf);
        tvSend = (TextView) findViewById(R.id.tvSend);
        etKeepTime = (EditText) findViewById(R.id.etKeepTime);
        etNoGroupTime = (EditText) findViewById(R.id.etNoGroupTime);
        etNoGetTime = (EditText) findViewById(R.id.etNoGetTime);
        etNoSendTime = (EditText) findViewById(R.id.etNoSendTime);
        tvPublish = (TextView) findViewById(R.id.tvPublish);
        tvPublishAgain = (TextView) findViewById(R.id.tvPublishAgain);

        linearSelf = (LinearLayout) findViewById(R.id.linearSelf);
        linearSend = (LinearLayout) findViewById(R.id.linearSend);
        ivSelf = (ImageView) findViewById(R.id.ivSelf);
        ivSend = (ImageView) findViewById(R.id.ivSend);

        initListener();

        if(!TextUtils.isEmpty(groupId)){
            mPresenter.getDetail(groupId);
        }

        initRxBus();

    }

    private void initData(BaseObject bean) {
        try{
            if(null!=bean.getGoodsName()){
                tvStoreName.setText(bean.getGoodsName());
            }
            if(null!=bean.getTitle()){
                etTitle.setText(bean.getTitle());
            }
            if(null!=bean.getGroupDesc()){
                etDesc.setText(bean.getGroupDesc());
            }
            if(null!=bean.getGroupPrice()){
                etCollagePrice.setText(bean.getGroupPrice());
            }
            if(null!=bean.getPrice()){
                etOrignPrice.setText(bean.getPrice());
            }
            if(null!=bean.getGroupNum()){
                etCollageNum.setText(bean.getGroupNum());
            }
            if(null!=bean.getKeepHour()){
                etKeepTime.setText(bean.getKeepHour());
            }
            if(null!=bean.getCancelHour()){
                etNoGroupTime.setText(bean.getCancelHour());
            }
            if(null!=bean.getNoGetHour()){
                etNoGetTime.setText(bean.getNoGetHour());
            }
            if(null!=bean.getNoSendHour()){
                etNoSendTime.setText(bean.getNoSendHour());
            }
            if("0".equals(bean.getShippingType())){
                ivSelf.setImageResource(R.mipmap.iv_select);
                ivSend.setImageResource(R.mipmap.iv_noselect);
            }else{
                ivSelf.setImageResource(R.mipmap.iv_noselect);
                ivSend.setImageResource(R.mipmap.iv_select);
            }
            if(null!=bean.getNeedNum()){
                etPeopleNum.setText(bean.getNeedNum());
            }
            if(null!=bean.getGoodsNum()){
                etGoodsNum.setText(bean.getGoodsNum());
            }
            if(null!=bean.getGoodsId()){
                goodsId = bean.getGoodsId();
            }
            if(null!=bean.getRemainingTime()){
                if("0".equals(bean.getRemainingTime())){
                    tvPublishAgain.setVisibility(View.GONE);
                }else{
                    tvPublishAgain.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initRxBus() {
        mRxManager.on("selectShop", new Action1<BaseList>() {
            @Override
            public void call(BaseList baseList) {
                goodsId = baseList.getGoodsId();
                tvStoreName.setText(baseList.getName());
                String num = etGoodsNum.getText().toString().trim();
                if(TextUtils.isEmpty(num)){
                    etGoodsNum.setText("1");
                    etOrignPrice.setText(baseList.getPrice());
                }else{
                    etOrignPrice.setText(new BigDecimal(baseList.getPrice()).multiply(new BigDecimal(num))+"");
                    TvUtils.setTwoDecimal(etOrignPrice);
                    TvUtils.setTwoDecimal(etCollagePrice);
                }
            }
        });
    }

    private void initListener() {

        etGoodsNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String totalPrice = etOrignPrice.getText().toString().trim();
                    if (!TextUtils.isEmpty(s)) {
                        if(!TextUtils.isEmpty(totalPrice)){
                            etOrignPrice.setText(new BigDecimal(totalPrice).multiply(new BigDecimal(s.toString()))+"");
                            TvUtils.setTwoDecimal(etOrignPrice);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        //自提
        linearSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                ivSelf.setImageResource(R.mipmap.iv_select);
                ivSend.setImageResource(R.mipmap.iv_noselect);
            }
        });
        //送货上门
        linearSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                ivSelf.setImageResource(R.mipmap.iv_noselect);
                ivSend.setImageResource(R.mipmap.iv_select);
            }
        });
        //选择商品
        tvStoreName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SelectShopActivity.class);
            }
        });
        tvPublishAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String orignPrice = etOrignPrice.getText().toString().trim();
                String collagePrice = etCollagePrice.getText().toString().trim();
                String collageNum = etCollageNum.getText().toString().trim();
                String peopleNum = etPeopleNum.getText().toString().trim();
                String keepTime = etKeepTime.getText().toString().trim();
                String noGroupTime = etNoGroupTime.getText().toString().trim();
                String noGetTime = etNoGetTime.getText().toString().trim();
                String noSendTime = etNoSendTime.getText().toString().trim();
                String goodsNum = etGoodsNum.getText().toString().trim();

                if(TextUtils.isEmpty(goodsId)){
                    showShortToast("请选择商品");
                    return;
                }
                if(TextUtils.isEmpty(title)){
                    showShortToast("请输入拼团标题");
                    return;
                }
                if(TextUtils.isEmpty(desc)){
                    showShortToast("请输入拼团说明");
                    return;
                }
                if(TextUtils.isEmpty(orignPrice)){
                    showShortToast("请输入原价");
                    return;
                }
                if(TextUtils.isEmpty(collagePrice)){
                    showShortToast("请输入拼团价");
                    return;
                }
                if(TextUtils.isEmpty(collageNum)){
                    showShortToast("请输入拼团数量");
                    return;
                }
                if(TextUtils.isEmpty(goodsNum)){
                    showShortToast("请输入每单商品数量");
                    return;
                }
                if(TextUtils.isEmpty(peopleNum)){
                    showShortToast("请输入成团人数");
                    return;
                }
                if(TextUtils.isEmpty(keepTime)){
                    showShortToast("请输入持续时间");
                    return;
                }
                if(TextUtils.isEmpty(noGroupTime)){
                    showShortToast("请输入未成团自动取消订单时间");
                    return;
                }
                if(TextUtils.isEmpty(noGetTime)){
                    showShortToast("请输入未提货自动取消订单时间");
                    return;
                }
                /*if(TextUtils.isEmpty(noSendTime)){
                    showShortToast("请输入未送货自动取消订单时间");
                    return;
                }*/


                mPresenter.setCollageInfo("", PreferencesUtils.getString(mContext, Constants.SHOP_ID), goodsId, title, goodsNum, desc,
                        collagePrice, collageNum, peopleNum, type+"", keepTime, noGroupTime, noGetTime, noSendTime);
            }
        });

        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String orignPrice = etOrignPrice.getText().toString().trim();
                String collagePrice = etCollagePrice.getText().toString().trim();
                String collageNum = etCollageNum.getText().toString().trim();
                String peopleNum = etPeopleNum.getText().toString().trim();
                String keepTime = etKeepTime.getText().toString().trim();
                String noGroupTime = etNoGroupTime.getText().toString().trim();
                String noGetTime = etNoGetTime.getText().toString().trim();
                String noSendTime = etNoSendTime.getText().toString().trim();
                String goodsNum = etGoodsNum.getText().toString().trim();

                if(TextUtils.isEmpty(goodsId)){
                    showShortToast("请选择商品");
                    return;
                }
                if(TextUtils.isEmpty(title)){
                    showShortToast("请输入拼团标题");
                    return;
                }
                if(TextUtils.isEmpty(desc)){
                    showShortToast("请输入拼团说明");
                    return;
                }
                if(TextUtils.isEmpty(orignPrice)){
                    showShortToast("请输入原价");
                    return;
                }
                if(TextUtils.isEmpty(collagePrice)){
                    showShortToast("请输入拼团价");
                    return;
                }
                if(TextUtils.isEmpty(collageNum)){
                    showShortToast("请输入拼团数量");
                    return;
                }
                if(TextUtils.isEmpty(goodsNum)){
                    showShortToast("请输入每单商品数量");
                    return;
                }
                if(TextUtils.isEmpty(peopleNum)){
                    showShortToast("请输入成团人数");
                    return;
                }
                if(TextUtils.isEmpty(keepTime)){
                    showShortToast("请输入持续时间");
                    return;
                }
                if(TextUtils.isEmpty(noGroupTime)){
                    showShortToast("请输入未成团自动取消订单时间");
                    return;
                }
                if(TextUtils.isEmpty(noGetTime)){
                    showShortToast("请输入未提货自动取消订单时间");
                    return;
                }
                /*if(TextUtils.isEmpty(noSendTime)){
                    showShortToast("请输入未送货自动取消订单时间");
                    return;
                }*/


                mPresenter.setCollageInfo(groupId, PreferencesUtils.getString(mContext, Constants.SHOP_ID), goodsId, title, goodsNum, desc,
                        collagePrice, collageNum, peopleNum, type+"", keepTime, noGroupTime, noGetTime, noSendTime);
            }
        });
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void showResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    mRxManager.post("setCollageInfo", "");
                    finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showDetail(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getData().getObject()){
                    initData(baseBeanResult.getData().getObject());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
