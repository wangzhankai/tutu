package com.superpeer.tutuyoudian.activity.collageset;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.refresh.RefreshLayout;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.collageadd.AddCollageActivity;
import com.superpeer.tutuyoudian.adapter.CollageSetAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnAddPublishListener;
import com.superpeer.tutuyoudian.listener.OnDeleteListener;
import com.superpeer.tutuyoudian.listener.OnEditListener;
import com.superpeer.tutuyoudian.listener.OnEndListener;

import rx.functions.Action1;

public class CollageSetActivity extends BaseActivity<CollageSetPresenter, CollageSetModel> implements CollageSetContract.View {

    private RecyclerView rvContent;
    private RefreshLayout refresh;
    private CollageSetAdapter adapter;
    private int delPos;

    @Override
    public int getLayoutId() {
        return R.layout.activity_collage_set;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("拼团设置");

        setToolBarViewStubText("添加拼团").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddCollageActivity.class);
            }
        });

        initRecyclerView();

        initRxBus();

        mPresenter.getList(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
    }

    private void initRxBus() {
        mRxManager.on("setCollageInfo", new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.getList(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
            }
        });
    }

    private void initRecyclerView() {

        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new CollageSetAdapter(R.layout.item_collage_set, null);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(adapter);

        //编辑
        adapter.setOnEditListener(new OnEditListener() {
            @Override
            public void OnEditListener(int position) {
                Intent intent = new Intent(mContext, AddCollageActivity.class);
                intent.putExtra("groupId", ((BaseList)adapter.getItem(position)).getGroupId());
                startActivity(intent);
            }
        });

        //删除
        adapter.setOnDeleteListener(new OnDeleteListener() {
            @Override
            public void onDeleteListener(int position) {
                showDeleteDialog(position);
            }
        });

        //新增
        adapter.setOnAddPublishListener(new OnAddPublishListener() {
            @Override
            public void onAddListener(int position) {
                mPresenter.addCollage(((BaseList)adapter.getItem(position)).getGroupId());
            }
        });

        //倒计时结束
        adapter.setOnEndListener(new OnEndListener() {
            @Override
            public void onEndListener(int position) {
                mPresenter.getList(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
            }
        });

    }

    public void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("删除");
        builder.setMessage("是否删除");//提示内容
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                delPos = position;
                mPresenter.delete(((BaseList)adapter.getItem(position)).getGroupId());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
                    if(null!=baseBeanResult.getData()){
                        if(null!=baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0){
                            adapter.setNewData(baseBeanResult.getData().getList());
                        }
                    }
                }
                adapter.loadComplete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showDeleteResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    adapter.getData().remove(delPos);
                    adapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showAddResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    mPresenter.getList(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
