package com.superpeer.tutuyoudian.activity.verify.successorfail;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.collageorder.detail.CollageOrderDetailActivity;
import com.superpeer.tutuyoudian.activity.order.detail.OrderDetailActivity;
import com.superpeer.tutuyoudian.adapter.VerifyAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseList;

import java.util.List;

public class VerifySuccessOrFailActivity extends BaseActivity<VerifySuccessOrFailPresenter, VerifySuccessOrFailModel> implements VerifySuccessOrFailContract.View {

    private TextView tvVerify;
    private TextView tvOrderDetail;
    private String type = "";
    private List<BaseList> list;
    private ImageView ivImg;
    private TextView tvVerifyStatus;
    private RecyclerView rvContent;
    private VerifyAdapter adapter;
    private String orderType = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        type = getIntent().getStringExtra("type");
        orderType = getIntent().getStringExtra("orderType");
        list = (List<BaseList>) getIntent().getSerializableExtra("list");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_success;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("提货验证");

        tvVerifyStatus = (TextView) findViewById(R.id.tvVerifyStatus);
        tvVerify = (TextView) findViewById(R.id.tvVerify);
        tvOrderDetail = (TextView) findViewById(R.id.tvOrderDetail);
        ivImg = (ImageView) findViewById(R.id.ivImg);

        if("1".equals(type)){
            tvVerifyStatus.setText("验证成功");
            ivImg.setImageResource(R.mipmap.iv_verify_success);
            tvOrderDetail.setVisibility(View.VISIBLE);
        }else{
            tvVerifyStatus.setText("验证失败");
            ivImg.setImageResource(R.mipmap.iv_verify_fail);
            tvVerify.setVisibility(View.VISIBLE);
        }

        initRecyclerView();

        initListener();

    }

    private void initRecyclerView() {
        rvContent = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new VerifyAdapter(R.layout.item_verify, list);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);
    }

    private void initListener() {
        //继续验证
        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //查看订单详情
        tvOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = null;
                    if("1".equals(orderType)){
                        intent = new Intent(mContext, OrderDetailActivity.class);
                    }else{
                        intent = new Intent(mContext, CollageOrderDetailActivity.class);
                    }
                    intent.putExtra("orderId", ((BaseList) adapter.getItem(0)).getOrderId());
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
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
}
