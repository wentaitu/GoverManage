package cn.edu.cqupt.loc;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.cqupt.loc.permission.CheckPermissionsActivity;

/**
 * Created by wentai on 17-8-14.
 */

public class MapActivity extends CheckPermissionsActivity
        implements
            LocationSource,   //提供位置数据的接口
            AMapLocationListener, //定位回调接口
            View.OnClickListener {
    private static final int SCAN = 0;

    // TODO: 17-4-22  3个运行时权限处理(位置、外部存储、PHONE)
    private MapView mapView;   //地图控件，还有另一包有这两类
    private AMap amap;  //用于获取AMap地图对象及其操作方法与接口
    private String locMsg;
    private TextView showLoc;
    private MyLocationStyle myLocationStyle;
    private LatLng myLocation = null;
    private BitmapDescriptor chooseDescripter;

    private AMapLocationClient mLocationClient = null; //定位发起端
    private AMapLocationClientOption mLocationOption = null;  //定位参数
    private OnLocationChangedListener mListener = null;  //定位回调监听器

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;

    // 存经纬度，地址
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);  // 当Activity唤醒时调用地图唤醒，此方法必须重写
        initAMap();  //进行地图处理

        //Fab->定位
        FloatingActionButton order = (FloatingActionButton) findViewById(R.id.order);
        order.setOnClickListener(this);

        showLoc = (TextView)findViewById(R.id.show_loc);

        findViewById(R.id.back_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //重载这个方法时必须调用父类的这个方法，用于MapView保存地图状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //当Activity暂停的时候调用地图暂停
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //重载这个方法时必须调用父类的这个方法
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当Activity销毁时调用地图的销毁
        mapView.onDestroy();
    }

    //*********************************************************************************
    private void initAMap() {
        if (amap == null) {
            amap = mapView.getMap();  //获取地图控制器AMap对象
        }

        //设置地图UI 缩放按钮是否可见 指南针是否显示
        UiSettings settings = amap.getUiSettings();
        settings.setCompassEnabled(true);
        settings.setZoomControlsEnabled(false);

        amap.setLocationSource(this);
        //是否可触发定位并显示定位层（是否打开定位图层）
        amap.setMyLocationEnabled(true);

        //描述地图状态将要发生的变化
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(15);
        amap.moveCamera(cameraUpdate);

        //bitmap 描述信息 在高德地图API 里，如果需要将一张图片绘制为Marker，
        //需要用这个类把图片包装成对象，可以通过BitmapDescriptorFactory 获得一个BitmapDescriptor 对象
        chooseDescripter = BitmapDescriptorFactory.fromResource(R.drawable.icon_loaction_choose);

        //定位小图标，默认蓝点，可自定义图片 定位（当前位置）的绘制样式类
        myLocationStyle = new MyLocationStyle();
        //设置定位（当前位置）的icon图标 3D地图默认为蓝箭头
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.img_location));
        //精度圈处理  边框、填充无rgb(0, 0, 0);
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        amap.setMyLocationStyle(myLocationStyle);

        initLoc();
        AddMarker();
    }

    private void initLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //**************************************************************************View.OnClickListener
    @Override
    public void onClick(View v) {
        int id = v.getId();
        //主界面三个Fab->定位、立即预约、反馈
        if (id == R.id.order) {
            //自定义定位按钮
            CameraUpdate update = CameraUpdateFactory.changeLatLng(myLocation);
            amap.animateCamera(update);
        }
    }
    //****************************************************************************接口LocationSource
    @Override
    public void activate(OnLocationChangedListener listener) {
        //激活位置接口
        mListener = listener;
    }

    @Override
    public void deactivate() {
        //处理定位更新的接口
        mListener = null;
    }
    //*********************************************************************接口AMapLocationListener定位回调监听，当定位完成后调用此方法
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            //if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                //获取定位信息
                locMsg = amapLocation.getCountry() + " " + amapLocation.getProvince() + " "
                        + amapLocation.getDistrict() + " " + amapLocation.getStreet() + " " + amapLocation.getStreetNum() + " 坐标:" + "(" + amapLocation.getLatitude() + ", " + amapLocation.getLongitude() + ")";

                editor = pref.edit();
                editor.putString("address", amapLocation.getCountry() + " " + amapLocation.getProvince() + " "
                        + amapLocation.getDistrict() + " " + amapLocation.getStreet() + " " + amapLocation.getStreetNum());
                editor.putString("longitude", String.valueOf(amapLocation.getLatitude()));
                editor.putString("latitude", String.valueOf(amapLocation.getLongitude()));
                editor.apply();

                showLoc.setText(locMsg);
                myLocation = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());   //设置自定义定位按钮定位参数
                // 如果不设置标志位，此时再拖动地图，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    amap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    amap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    //添加图钉
                    amap.addMarker(getMarkerOptions(amapLocation));
                    isFirstLoc = false;
                }

        } else {
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表
            Log.e("AmapError", "location Error, ErrCode:"
                    + amapLocation.getErrorCode() + ", errInfo:"
                    + amapLocation.getErrorInfo());

            //Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
        }

    }

    //自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {

        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        options.icon(chooseDescripter);
        //位置
        options.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        StringBuffer buffer = new StringBuffer();
        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + ""
                + amapLocation.getCity() +  "" + amapLocation.getDistrict() + ""
                + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
        //标题
        options.title(buffer.toString());
        //子标题
        //options.snippet("Amap");
        //设置多少帧刷新一次图片资源
        options.period(60);
        return options;
    }

    //添加点
    private void AddMarker() {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_menu_send));
        markerOption.draggable(false);  //设置Marker覆盖物是否可拖拽

//        //final Marker marker2 = amap.addMarker(new MarkerOptions().position(new LatLng(39.906901,116.397972)).title("123").snippet("DefaultMarker"));
//
//        markerOption.position(new LatLng(29.532775, 106.604471));
//        //Marker marker = amap.addMarker(markerOption);
//        amap.addMarker(markerOption);
//        markerOption.position(new LatLng(29.532976, 106.604603)).title(getResources().getString(R.string.mapTips));
//        amap.addMarker(markerOption);

    }

}

