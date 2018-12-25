package com.superpeer.tutuyoudian.activity.datacount;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.income.IncomeDetailActivity;
import com.superpeer.tutuyoudian.adapter.CountAdapter;
import com.superpeer.tutuyoudian.adapter.FeeAdapter;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseCountBean;
import com.superpeer.tutuyoudian.bean.BaseCountData;
import com.superpeer.tutuyoudian.bean.BaseCountList;
import com.superpeer.tutuyoudian.bean.BaseCountObj;
import com.superpeer.tutuyoudian.bean.BaseRunBean;
import com.superpeer.tutuyoudian.bean.IncomeBean;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.widget.NoScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountActivity extends BaseActivity<CountPresenter, CountModel> implements CountContract.View {

    private TextView tvView;
    private TextView tvOrder;
    private TextView tvIncome;
    private LineChart lineChart;
    private LinearLayout linearWeek;
    private LinearLayout linearMonth;
    private LinearLayout linearQuarter;
    private TextView tvWeek;
    private TextView tvMonth;
    private TextView tvQuarter;
    private String days = "7";
    private LinearLayout lineaOrderOrVisit;
    private LinearLayout linearTotalIncome;
    private LinearLayout linearRank;
    private TextView tvPayNum;
    private TextView tvSendNum;
    private TextView tvDataSecond;
    private TextView tvDataFirst;
    private XAxis xAxis;
    private YAxis leftYAxis;
    private YAxis rightYaxis;
    private Legend legend;
    private LinearLayout linearDetail;
    private LinearLayout linearWithDraw;
    private int type = 0;
    private TextView tvTotalIncome;
    private ImageView ivBack;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private CountAdapter countAdapter;

    @Override
    protected boolean hasHeadTitle() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_count;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {

        ivBack = (ImageView) findViewById(R.id.ivBack);

        tvView = (TextView) findViewById(R.id.tvView);
        tvOrder = (TextView) findViewById(R.id.tvOrder);
        tvIncome = (TextView) findViewById(R.id.tvIncome);
        tvWeek = (TextView) findViewById(R.id.tvWeek);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvQuarter = (TextView) findViewById(R.id.tvQuarter);

        tvPayNum = (TextView) findViewById(R.id.tvPayNum);
        tvSendNum = (TextView) findViewById(R.id.tvSendNum);
        tvDataFirst = (TextView) findViewById(R.id.tvDataFirst);
        tvDataSecond = (TextView) findViewById(R.id.tvDataSecond);
        tvTotalIncome = (TextView) findViewById(R.id.tvTotalIncome);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);

        lineChart = (LineChart) findViewById(R.id.lineChart);

        linearWeek = (LinearLayout) findViewById(R.id.linearWeek);
        linearMonth = (LinearLayout) findViewById(R.id.linearMonth);
        linearQuarter = (LinearLayout) findViewById(R.id.linearQuarter);

        lineaOrderOrVisit = (LinearLayout) findViewById(R.id.lineaOrderOrVisit);
        linearTotalIncome = (LinearLayout) findViewById(R.id.linearTotalIncome);
        linearRank = (LinearLayout) findViewById(R.id.linearRank);
        linearDetail = (LinearLayout) findViewById(R.id.linearDetail);
        linearWithDraw = (LinearLayout) findViewById(R.id.linearWithDraw);

        initRecyclerView();

        initListener();

        mPresenter.getVisitData(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days);

    }

    private void initRecyclerView() {
        NoScrollRecyclerView recycler = (NoScrollRecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        countAdapter = new CountAdapter(R.layout.item_count, null);
        recycler.setAdapter(countAdapter);
    }

    private void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //访客
        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                reset();
                tvDataFirst.setText("访客人数");
                tvDataSecond.setText("浏览次数");
                lineaOrderOrVisit.setVisibility(View.VISIBLE);
                linearTotalIncome.setVisibility(View.GONE);
                linearRank.setVisibility(View.GONE);
                linearWithDraw.setVisibility(View.GONE);
                linearDetail.setVisibility(View.GONE);
                tvView.setBackgroundResource(R.drawable.bg_white_solid);
                tvView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                mPresenter.getVisitData(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days);
            }
        });

        //订单
        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                tvDataFirst.setText("付款订单");
                tvDataSecond.setText("发货订单");
                reset();
                lineaOrderOrVisit.setVisibility(View.VISIBLE);
                linearTotalIncome.setVisibility(View.GONE);
                linearRank.setVisibility(View.VISIBLE);
                linearWithDraw.setVisibility(View.GONE);
                linearDetail.setVisibility(View.GONE);
                tvOrder.setBackgroundResource(R.drawable.bg_white_solid);
                tvOrder.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                mPresenter.getOrderNum(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days, "10");
            }
        });

        //营销
        tvIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                reset();
                lineaOrderOrVisit.setVisibility(View.GONE);
                linearTotalIncome.setVisibility(View.VISIBLE);
                linearRank.setVisibility(View.GONE);
                linearWithDraw.setVisibility(View.VISIBLE);
                linearDetail.setVisibility(View.VISIBLE);
                tvIncome.setBackgroundResource(R.drawable.bg_white_solid);
                tvIncome.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                mPresenter.getRunNum(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days);
            }
        });

        //7天
        linearWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = "7";
                tvWeek.setBackgroundResource(R.drawable.bg_primary_line);
                tvMonth.setBackgroundResource(R.drawable.bg_grey_line);
                tvQuarter.setBackgroundResource(R.drawable.bg_grey_line);
                if(type == 0){
                    mPresenter.getVisitData(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days);
                }else if(type == 1){
                    mPresenter.getOrderNum(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days, "10");
                }else if(type == 2){
                    mPresenter.getRunNum(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days);
                }
            }
        });
        //30天
        linearMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = "30";
                tvWeek.setBackgroundResource(R.drawable.bg_grey_line);
                tvMonth.setBackgroundResource(R.drawable.bg_primary_line);
                tvQuarter.setBackgroundResource(R.drawable.bg_grey_line);
                if(type == 0){
                    mPresenter.getVisitData(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days);
                }else if(type == 1){
                    mPresenter.getOrderNum(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days, "10");
                }else if(type == 2){
                    mPresenter.getRunNum(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days);
                }
            }
        });
        //90天
        linearQuarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = "90";
                tvWeek.setBackgroundResource(R.drawable.bg_grey_line);
                tvMonth.setBackgroundResource(R.drawable.bg_grey_line);
                tvQuarter.setBackgroundResource(R.drawable.bg_primary_line);
                if(type == 0){
                    mPresenter.getVisitData(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days);
                }else if(type == 1){
                    mPresenter.getOrderNum(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days, "10");
                }else if(type == 2){
                    mPresenter.getRunNum(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days);
                }
            }
        });

        //营收明细
        linearDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IncomeDetailActivity.class);
            }
        });
    }

    private void reset() {
        tvView.setBackgroundResource(R.drawable.bg_white_stroke);
        tvOrder.setBackgroundResource(R.drawable.bg_white_stroke);
        tvIncome.setBackgroundResource(R.drawable.bg_white_stroke);
        tvView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        tvOrder.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        tvIncome.setTextColor(ContextCompat.getColor(mContext, R.color.white));
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
    public void showVisitResult(BaseCountBean baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getData()) {
                    if(null!=baseBeanResult.getData().getObject()){
                        BaseCountObj obj = baseBeanResult.getData().getObject();
                        if(null!=obj.getVisitorTotal()){
                            tvPayNum.setText(obj.getVisitorTotal());
                        }
                        if(null!=obj.getBrowseTotal()){
                            tvSendNum.setText(obj.getBrowseTotal());
                        }
                    }
                    if (null != baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0) {
                        List<BaseCountList> list = baseBeanResult.getData().getList();
                        tvStartTime.setText(list.get(0).getDate());
                        tvEndTime.setText(list.get(list.size()-1).getDate());
                        showLineChart(baseBeanResult.getData().getList(), "访问人数", "浏览次数", ContextCompat.getColor(mContext, R.color.dard_orange), ContextCompat.getColor(mContext, R.color.blue), "0");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showOrderResult(BaseCountBean baseBeanResult) {
        try{
            mPresenter.getSaleGoods(PreferencesUtils.getString(mContext, Constants.SHOP_ID), days, "10");
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getData()) {
                    if(null!=baseBeanResult.getData().getObject()){
                        BaseCountObj obj = baseBeanResult.getData().getObject();
                        if(null!=obj.getPayOrderTotal()){
                            tvPayNum.setText(obj.getPayOrderTotal());
                        }
                        if(null!=obj.getSendOrderTotal()){
                            tvSendNum.setText(obj.getSendOrderTotal());
                        }
                    }
                    if (null != baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0) {
                        List<BaseCountList> list = baseBeanResult.getData().getList();
                        tvStartTime.setText(list.get(0).getDate());
                        tvEndTime.setText(list.get(list.size()-1).getDate());
                        showLineChart(baseBeanResult.getData().getList(), "付款订单", "发货订单", ContextCompat.getColor(mContext, R.color.dard_orange), ContextCompat.getColor(mContext, R.color.blue), "1");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showRunResult(BaseCountBean baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getData()) {
                    if(null!=baseBeanResult.getData().getObject()){
                        BaseCountObj obj = baseBeanResult.getData().getObject();
                        if(null!=obj.getTotalAmount()){
                            tvTotalIncome.setText(obj.getTotalAmount());
                        }
                    }
                    if (null != baseBeanResult.getData().getList()&&baseBeanResult.getData().getList().size()>0) {
                        List<BaseCountList> list = baseBeanResult.getData().getList();
                        tvStartTime.setText(list.get(0).getDate());
                        tvEndTime.setText(list.get(list.size()-1).getDate());
                        showLineChart(baseBeanResult.getData().getList(), "营收", ContextCompat.getColor(mContext, R.color.blue));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showRecordResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getData().getObject()){

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showSaleResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getData().getList()){
                    countAdapter.setNewData(baseBeanResult.getData().getList());
                }
            }
            countAdapter.loadComplete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /** * 初始化图表 */
    private void initChart(LineChart lineChart) {
        /***图表设置***/
        //是否展示网格线
         lineChart.setDrawGridBackground(false);
        // 是否显示边界
         lineChart.setDrawBorders(true);
        // 是否可以拖动
         lineChart.setDragEnabled(true);
        // 是否有触摸事件
         lineChart.setTouchEnabled(true);
        // 设置XY轴动画效果
         lineChart.animateY(2500);
         lineChart.animateX(1500);
        // /***XY轴的设置***/
         xAxis = lineChart.getXAxis();
         leftYAxis = lineChart.getAxisLeft();
         rightYaxis = lineChart.getAxisRight();
        // X轴设置显示位置在底部
         xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
         xAxis.setAxisMinimum(0f);
         xAxis.setGranularity(1f);
         xAxis.setLabelCount(5);
         xAxis.setSpaceMin(100f);
        // 保证Y轴从0开始，不然会上移一点
         leftYAxis.setAxisMinimum(0f);
         rightYaxis.setAxisMinimum(0f);
        // /***折线图例 标签 设置***/
         legend = lineChart.getLegend();
        // 设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
         legend.setForm(Legend.LegendForm.LINE);
         legend.setTextSize(12f);
        // 显示位置 左下方
         legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
         legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
         legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        // 是否绘制在图表里面
         legend.setDrawInside(false);
    }

    /** * 展示曲线
     * * * @param dataList 数据集合 * @param name
     * 曲线名称 * @param color
     * 曲线颜色
     * */
    public void showLineChart(List<BaseCountList> object, String firstName, String secondName, int dard_orange, int blue, String type) {
        initChart(lineChart);
        List<Entry> entriesFirst = new ArrayList<>();
        List<Entry> entriesSecond = new ArrayList<>();
        for(int i=0; i<object.size(); i++){
            if("0".equals(type)) {
                Entry entry1 = new Entry(i, Float.parseFloat(object.get(i).getVisitorNum()));
                entriesFirst.add(entry1);
                Entry entry2 = new Entry(i, Float.parseFloat(object.get(i).getBrowseNum()));
                entriesSecond.add(entry2);
            }else if ("1".equals(type)){
                Entry entry1 = new Entry(i, Float.parseFloat(object.get(i).getPayOrderNum()));
                entriesFirst.add(entry1);
                Entry entry2 = new Entry(i, Float.parseFloat(object.get(i).getSendOrderNum()));
                entriesSecond.add(entry2);
            }
        }
        /*tvPayNum.setText((int)firstNum+"");
        tvSendNum.setText((int)secondNum+"");*/
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entriesFirst, firstName);
        initLineDataSet(lineDataSet, dard_orange, LineDataSet.Mode.LINEAR);
        LineDataSet lineDataSet2 = new LineDataSet(entriesSecond, secondName);
        initLineDataSet(lineDataSet2, blue, LineDataSet.Mode.LINEAR);
        LineData lineData = new LineData(lineDataSet, lineDataSet2);
        lineChart.setData(lineData);

    }

    /** * 展示曲线
     * * * @param dataList 数据集合 * @param name
     * 曲线名称 * @param color
     * 曲线颜色
     * */
    public void showLineChart(final List<BaseCountList> object, String firstName, int blue) {
        initChart(lineChart);
        List<Entry> entriesFirst = new ArrayList<>();
        for(int i=0; i<object.size(); i++){
            Entry entry = new Entry(i, Float.parseFloat(object.get(i).getMoney()));
            entriesFirst.add(entry);
        }
        /*tvTotalIncome.setText(firstNum+"");*/
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entriesFirst, firstName);
        initLineDataSet(lineDataSet, blue, LineDataSet.Mode.LINEAR);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

    }

    /**
     * * 曲线初始化设置 一个LineDataSet 代表一条曲线
     * * * @param lineDataSet 线条
     * * @param color       线条颜色
     * * @param mode */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
         lineDataSet.setDrawCircleHole(false);
         lineDataSet.setValueTextSize(10f);
         // 设置折线图填充
         lineDataSet.setDrawFilled(true);
         lineDataSet.setFormLineWidth(1f);
         lineDataSet.setFormSize(15.f);
         if (mode == null) {
            // 设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
         } else {
            lineDataSet.setMode(mode);
         }
    }
}
