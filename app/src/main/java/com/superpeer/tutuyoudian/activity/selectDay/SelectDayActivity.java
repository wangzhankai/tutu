package com.superpeer.tutuyoudian.activity.selectDay;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.adapter.DayAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.DayList;

import java.util.ArrayList;
import java.util.List;

public class SelectDayActivity extends BaseActivity<SelectDayPresenter, SelectDayModel> implements SelectDayContract.View {

    private List<DayList> list = new ArrayList();
    private RecyclerView recyclerView;
    private DayAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_day;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("休息日");

        DayList bean = new DayList();
        bean.setDayName("无");
        DayList bean1 = new DayList();
        bean1.setDayName("周一");
        DayList bean2 = new DayList();
        bean2.setDayName("周二");
        DayList bean3 = new DayList();
        bean3.setDayName("周三");
        DayList bean4 = new DayList();
        bean4.setDayName("周四");
        DayList bean5 = new DayList();
        bean5.setDayName("周五");
        DayList bean6 = new DayList();
        bean6.setDayName("周六");
        DayList bean7 = new DayList();
        bean7.setDayName("周日");

        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);

        initRecyclerView();

        setToolBarViewStubText("完成").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                String days = "";
                for(int i=0; i<list.size(); i++){
                    if(list.get(i).isChecked()){
                        if("".equals(str)){
                            str+=list.get(i).getDayName();
                            if("".equals(days)){
                                if(i==7){
                                    days+=0+"";
                                }else{
                                    days+=i+"";
                                }
                            }else{
                                if(i==7){
                                    days+=","+0;
                                }else{
                                    days+=","+i;
                                }
                            }
                        }else{
                            str+=","+list.get(i).getDayName();
                            if("".equals(days)){
                                if(i==7){
                                    days+=0+"";
                                }else{
                                    days+=i+"";
                                }
                            }else{
                                if(i==7){
                                    days+=","+0;
                                }else{
                                    days+=","+i;
                                }
                            }
                        }
                    }
                }
                mRxManager.post("selectDay", str);
                mRxManager.post("days", days);
                finish();
            }
        });

    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new DayAdapter(R.layout.item_day, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                DayList bean = ((DayList) adapter.getItem(position));
                bean.setChecked(!bean.isChecked());
                if(bean.isChecked()){
                    if(position == 0){
                        for(int i=1; i<8; i++){
                            DayList bean1 = ((DayList) adapter.getItem(i));
                            bean1.setChecked(false);
                        }
                    }else{
                        DayList bean1 = ((DayList) adapter.getItem(0));
                        bean1.setChecked(false);
                    }
                }
                adapter.notifyDataSetChanged();
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
