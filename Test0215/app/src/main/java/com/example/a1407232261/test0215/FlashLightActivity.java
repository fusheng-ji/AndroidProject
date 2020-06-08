package com.example.a1407232261.test0215;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

public class FlashLightActivity extends AppCompatActivity {
    TextView showInfo;
    ImageButton flBtn;

    boolean isflag=false;

    Camera cc;
    Camera.Parameters pp;
    SurfaceView sv;
    SurfaceHolder sh;
    RelativeLayout flabg;
    //声明数组，来保存所有需要动态开启的权限
    private static  String[] PERMISSIONS_STORGE={
        Manifest.permission.CAMERA
    };
    //权限的请求编码
    private  static  int REQUEST_PERMISSION_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        //检测系统版本号
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,PERMISSIONS_STORGE,REQUEST_PERMISSION_CODE);
            }
        }

        showInfo = (TextView)findViewById(R.id.textView11);
        flBtn = (ImageButton)findViewById(R.id.imageButton2);
        flabg=(RelativeLayout)findViewById(R.id.layout);
        sv = (SurfaceView)findViewById(R.id.surfaceView);
        cc=Camera.open();
        pp=cc.getParameters();
        sh=sv.getHolder();


        sh.addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cc.setPreviewDisplay(sh)
                    ;
                } catch (IOException e) {
                    e.printStackTrace();
                    cc.release();
                }
                cc.startPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        flBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isflag == false) {
                    pp.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    cc.setParameters(pp);
                    showInfo.setText("点击关闭");
                    showInfo.setTextColor(Color.BLACK);
                    flabg.setBackgroundResource(R.drawable.day);//更换背景图片
                    flBtn.setImageDrawable(getResources().getDrawable(R.drawable.switch_on));//更换Imagebutton
                    isflag = true;

                } else {
                    pp.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    cc.setParameters(pp);
                    showInfo.setText("点击打开");
                    showInfo.setTextColor(Color.WHITE);
                    flabg.setBackgroundResource(R.drawable.night);//更换背景图片
                    flBtn.setImageDrawable(getResources().getDrawable(R.drawable.switch_off));//更换Imagebutton
                    isflag = false;
                }
            }

        });


        }
    }

