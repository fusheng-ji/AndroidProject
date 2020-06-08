package com.example.a1407232261.test0215;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;


public class CompassActivity extends AppCompatActivity {
    private ImageView panel;
    private TextView show, location;

    SensorManager sm;
    SensorListener listener = new SensorListener();
    //定位管理器
    LocationManager lm;
    private static String[] PERMISSIONS_STORGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    //权限的请求编码
    private static int REQUEST_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP){
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,PERMISSIONS_STORGE,REQUEST_PERMISSION_CODE);
            }
        }
        show = (TextView) findViewById(R.id.show);
        panel = (ImageView) findViewById(R.id.panel);
        location = (TextView) findViewById(R.id.location);
        //设置屏幕常亮
        panel.setKeepScreenOn(true);
        //获得传感器对象
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //获得定位传感器对象
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!isGpsAble(lm)) {
            Toast.makeText(CompassActivity.this, "请先打开GPS！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            CompassActivity.this.finish();
        }
        //通过GPS获取最近一次位置信息
        Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lc==null){
            lc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        getAddress(lc);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //当定位信息已经发生改变
                getAddress(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                if(ActivityCompat.checkSelfPermission(CompassActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(CompassActivity.this,PERMISSIONS_STORGE,REQUEST_PERMISSION_CODE);
                }
                getAddress(lm.getLastKnownLocation(s));
            }
            //作业
            //指南针可以显示朝向
            @Override
            public void onProviderDisabled(String s) {
                getAddress(null);
            }
        });
    }
   protected void onResume(){
        Sensor sensor=sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        sm.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_GAME);

        super.onResume();
    }
    private class SensorListener implements SensorEventListener{
        //定义变量，记录之前的角度
        float predegree=0;
        @Override
        public void onSensorChanged(SensorEvent event) {
            float degree=event.values[0];
            String Orention;
            if(degree<=111&&degree>=67)
            {Orention="东";}
            else if(degree<=202&&degree>=158)
            {Orention="南";}
            else if(degree<=288&&degree>=247)
            {Orention="西";}
            else if((degree<=359&&degree>=338)||(degree<=21&&degree>=0))
            {Orention="北";}
            else if(degree<=246&&degree>=112)
            {Orention="东南";}
            else if(degree<=246&&degree>=203)
            {Orention="西南";}
            else if(degree<=66&&degree>=22)
            {Orention="东北";}
            else{
                Orention="西北";
            }
            show.setText(Orention+degree);
            RotateAnimation an=new RotateAnimation(predegree,-degree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            //设置动画持续时间
            an.setDuration(250);
            panel.setAnimation(an);
            predegree=-degree;

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
    private boolean isGpsAble(LocationManager lm){
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)?true:false;

    }
    private void getAddress(Location lc){
        if(lc!=null){
            StringBuilder sb = new StringBuilder();
            sb.append("当前位置:\n");
            DecimalFormat df = new DecimalFormat(".00");
            sb.append("经度:"+df.format(lc.getLongitude())+"\n");
            sb.append("纬度:"+df.format(lc.getLatitude())+"\n");
            Geocoder ge = new Geocoder(CompassActivity.this);
            List<Address> addList = null;
            try {
                //sb.append("当前城市:"+ge.getFromLocation(lc.getLatitude(),lc.getLongitude(),1).get(0).getFeatureName());
                addList = ge.getFromLocation(lc.getLatitude(),lc.getLongitude(),1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address ad = addList.get(0);
            sb.append("省份:"+ad.getAdminArea()+"\n");  //省份
            sb.append("城市:"+ad.getLocality()+"\n");  //城市
            sb.append("街道:"+ad.getFeatureName()+"\n");  //街道
            location.setText(sb.toString());
        }else{
            location.setText("当前位置:暂无信息");
            Toast.makeText(CompassActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
        }
    }
}
