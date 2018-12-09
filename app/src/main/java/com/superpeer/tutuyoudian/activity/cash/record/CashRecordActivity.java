package com.superpeer.tutuyoudian.activity.cash.record;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.adapter.RecordAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

public class CashRecordActivity extends BaseActivity<CashRecordPresenter, CashRecordModel> implements CashRecordContract.View {

    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private RecordAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cash_record;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("提现记录");

        initRecyclerView();

        mPresenter.getRecordList(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
    }

    private void initRecyclerView() {
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new RecordAdapter(R.layout.item_record, null);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);
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
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                        adapter.setNewData(baseBeanResult.getData().getList());
                    }
                    adapter.loadComplete();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
