package com.superpeer.tutuyoudian.activity.position;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.superpeer.base_libs.base.BaseActivity;
import com.superpeer.base_libs.base.baseadapter.BaseQuickAdapter;
import com.superpeer.base_libs.base.baseadapter.OnItemClickListener;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.adapter.PositionAdapter;
import com.superpeer.tutuyoudian.adapter.SearchAdapter;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseLocationBean;
import com.superpeer.tutuyoudian.bean.BaseSearchResult;
import com.superpeer.tutuyoudian.bean.LocationData;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnLocationListener;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.map.geolocation.TencentPoi;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.List;

public class PositionActivity extends BaseActivity<PositionPresenter, PositionModel>  implements TencentLocationListener, PositionContract.View {
    private MapView mapview = null;
    private RecyclerView rvContent;
    private PositionAdapter adapter;
    private EditText etSearch;
    private ImageView ivSearch;
    private String code = "";
    private SearchAdapter searchAdapter;
    private String city = "";

    private OnLocationListener listener = new OnLocationListener() {
        @Override
        public void onSuccess(String str) {
            Log.i("base", str);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_position;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("地址选择");

        initRecyclerView();

        etSearch = (EditText) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);

        mapview=(MapView)findViewById(R.id.mapview);
        TencentLocationManager locationManager =
                TencentLocationManager.getInstance(mContext);
        locationManager.removeUpdates(this);
        TencentLocationRequest request = TencentLocationRequest.create().setInterval(60*60*1000).setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
        locationManager.requestLocationUpdates(request, this);

        initSearch();
    }

    private void initSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    String content = etSearch.getText().toString().trim();
                    if(TextUtils.isEmpty(content)){
                        showShortToast("请输入搜索城市");
                        return false;
                    }
                    mPresenter.getLocation(content, city, "T3NBZ-2ZD3J-XDTFR-FR7HI-UHC3O-KLBBQ", listener);
                }
                return false;
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etSearch.getText().toString().trim();
                if(TextUtils.isEmpty(content)){
                    showShortToast("请输入搜索城市");
                    return;
                }
                mPresenter.getLocation(content, city, "T3NBZ-2ZD3J-XDTFR-FR7HI-UHC3O-KLBBQ", listener);
//                mPresenter.search(content, "region("+city+content+",0)","T3NBZ-2ZD3J-XDTFR-FR7HI-UHC3O-KLBBQ", "5", "1");
            }
        });

       mapview.getMap().setOnCameraChangeListener(new TencentMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinished(CameraPosition cameraPosition) {
                mapview.getMap().clear();
                //获取TencentMap实例
                TencentMap tencentMap = mapview.getMap();
                //设置实时路况开启
                tencentMap.setTrafficEnabled(true);
                /*//设置地图中心点
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                new LatLng(Float.parseFloat(cameraPosition.target.latitude+""),Float.parseFloat(cameraPosition.target.longitude+"")), //新的中心点坐标
                                19,  //新的缩放级别
                                0f, //俯仰角 0~45° (垂直地图时为0)
                                0f)); //偏航角 0~360° (正北方为0)*/
                //移动地图
//                tencentMap.moveCamera(cameraSigma);
                Marker marker = tencentMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Float.parseFloat(cameraPosition.target.latitude+""), Float.parseFloat(cameraPosition.target.longitude+"")))
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker())
                        .draggable(true));
                marker.showInfoWindow();// 设置默认显示一个infoWindow

                mPresenter.search("便利店", "nearby("+cameraPosition.target.latitude+","+cameraPosition.target.longitude+",1000)","T3NBZ-2ZD3J-XDTFR-FR7HI-UHC3O-KLBBQ", "5", "1");
            }
        });
    }

    private void initRecyclerView() {
        rvContent = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new PositionAdapter(R.layout.item_position, null);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(adapter);

        rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter myadapter, View view, int position) {
                mRxManager.post("selectPos", ((TencentPoi)adapter.getItem(position)));
                mRxManager.post("posCode", code);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapview.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        mapview.onStop();
        super.onStop();
    }

    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        if (TencentLocation.ERROR_OK == error){
            //定位成功
            city = location.getCity();
            code = location.getCityCode();
            initPosition(location);
        }else{
            //定位失败
            showShortToast("定位失败，请检查GPS和网络是否可用");
        }
    }

    private void initPosition(TencentLocation location) {
        initCenter(location);

        List<TencentPoi> list = location.getPoiList();
        adapter.setNewData(list);
        adapter.loadComplete();
    }

    private void initCenter(TencentLocation location) {
        //获取TencentMap实例
        TencentMap tencentMap = mapview.getMap();
        //设置实时路况开启
        tencentMap.setTrafficEnabled(true);
        //设置地图中心点
        CameraUpdate cameraSigma =
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(Float.parseFloat(location.getLatitude()+""),Float.parseFloat(location.getLongitude()+"")), //新的中心点坐标
                        19,  //新的缩放级别
                        0f, //俯仰角 0~45° (垂直地图时为0)
                        0f)); //偏航角 0~360° (正北方为0)
        //移动地图
        tencentMap.moveCamera(cameraSigma);
        Marker marker = tencentMap.addMarker(new MarkerOptions()
                .position(new LatLng(Float.parseFloat(location.getLatitude()+""), Float.parseFloat(location.getLongitude()+"")))
                .title(location.getCity()+location.getAddress())
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory
                        .defaultMarker())
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infoWindow
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

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
    public void showLocationResult(BaseLocationBean baseBeanResult) {
        try{
            if("0".equals(baseBeanResult.getStatus())){
                mPresenter.search("便利店", "nearby("+baseBeanResult.getResult().getLocation().getLat()+","+baseBeanResult.getResult().getLocation().getLng()+",1000)","T3NBZ-2ZD3J-XDTFR-FR7HI-UHC3O-KLBBQ", "5", "1");

                LocationData locationData = baseBeanResult.getResult().getLocation();
                //获取TencentMap实例
                TencentMap tencentMap = mapview.getMap();
                //设置实时路况开启
                tencentMap.setTrafficEnabled(true);
                //设置地图中心点
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                new LatLng(Float.parseFloat(locationData.getLat()+""),Float.parseFloat(locationData.getLng()+"")), //新的中心点坐标
                                19,  //新的缩放级别
                                0f, //俯仰角 0~45° (垂直地图时为0)
                                0f)); //偏航角 0~360° (正北方为0)
                //移动地图
                tencentMap.moveCamera(cameraSigma);
                Marker marker = tencentMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Float.parseFloat(locationData.getLat()+""), Float.parseFloat(locationData.getLng()+"")))
                        .title(baseBeanResult.getResult().getTitle())
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker())
                        .draggable(true));
                marker.showInfoWindow();// 设置默认显示一个infoWindow
            }else{
                showShortToast("未搜索到数据");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showSearchResult(BaseSearchResult baseBeanResult) {
        try{
            searchAdapter = new SearchAdapter(R.layout.item_position, baseBeanResult.getData());
            rvContent.setAdapter(searchAdapter);
            rvContent.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void SimpleOnItemClick(BaseQuickAdapter myadapter, View view, int position) {
                    mRxManager.post("position", ((BaseSearchResult.SearchData)searchAdapter.getItem(position)));
                    mRxManager.post("posCode", ((BaseSearchResult.SearchData)searchAdapter.getItem(position)).getAd_info().getAdcode());
                    finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showLocationTipResult(BaseSearchResult baseBeanResult) {

    }
}

