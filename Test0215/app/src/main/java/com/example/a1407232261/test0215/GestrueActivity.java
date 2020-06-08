package com.example.a1407232261.test0215;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.preference.DialogPreference;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class GestrueActivity extends AppCompatActivity {
    private GestureOverlayView gestureOverlayView;
    //声明数组，用来保存所有需要动态开启的权限
    private static String[] PERMISSIONS_STORGE={
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    //权限的请求编码
    private static int REQUEST_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestrue);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,PERMISSIONS_STORGE,REQUEST_PERMISSION_CODE);
            }
        }
        gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestureOverlayView);
        //gestureOverlayView.setGestureColor(Color.BLACK);
        gestureOverlayView.setGestureStrokeWidth(30);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, final Gesture gesture) {
                //手势绘制完成后，出现保存页面
                LayoutInflater lif=LayoutInflater.from(GestrueActivity.this);
                View saveDialog = lif.inflate(R.layout.gesture_save,null);
                final EditText gSaveName=(EditText)saveDialog.findViewById(R.id.Gesture);
                ImageView gSaveView = (ImageView)saveDialog.findViewById(R.id.imageView6);
                Bitmap bitmap=gesture.toBitmap(128,128,10, Color.BLACK);
                gSaveView.setImageBitmap(bitmap);
                new AlertDialog.Builder(GestrueActivity.this)
                        .setView(saveDialog)
                        .setPositiveButton("取消",null)
                        .setNegativeButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GestureLibrary glib = GestureLibraries.fromFile("/mnt/sdcard/fusheng");
                                glib.addGesture(gSaveName.getText().toString().trim(),gesture);
                                glib.save();
                                Toast.makeText(GestrueActivity.this,"手势保存成功！！！",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent();
                                intent.setClass(GestrueActivity.this,CheckGestrueActivity.class);
                                startActivity(intent);
                            }
                        })
                        .create()
                        .show();
            }
        });
    }
}
