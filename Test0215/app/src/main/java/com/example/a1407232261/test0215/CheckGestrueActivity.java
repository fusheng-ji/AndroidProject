package com.example.a1407232261.test0215;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckGestrueActivity extends AppCompatActivity {
GestureOverlayView gestureOverlayView;
    GestureLibrary gestureLibrary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_gestrue);

        //读取程序创建的程序库
        gestureLibrary= GestureLibraries.fromFile("/mnt/sdcard/fusheng");
        if(gestureLibrary.load()){
            Toast.makeText(CheckGestrueActivity.this,"手势加载成功！！！",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(CheckGestrueActivity.this,"手势加载失败！！！",Toast.LENGTH_SHORT).show();
        }
        gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestureOverlayView);
        gestureOverlayView.setGestureColor(Color.BLACK);
        gestureOverlayView.setGestureStrokeWidth(30);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictions=gestureLibrary.recognize(gesture);
                ArrayList<String> result=new ArrayList<String>();
                for (Prediction pred : predictions){
                    if(pred.score>2.0){
                        result.add("与手势【"+pred.name+"】相似");
                    }
                }
                if(result.size()>0){
                    Intent intent=new Intent();
                    intent.setClass(CheckGestrueActivity.this,GameActivity.class);
                    startActivity(intent);
                    CheckGestrueActivity.this.finish();
                    Toast.makeText(CheckGestrueActivity.this,"手势验证成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CheckGestrueActivity.this,"手势验证失败",Toast.LENGTH_SHORT).show();
                }
            }
            //1.去除验证按钮
            //2.当没有保存过手势时，点击game，进入到手势绘制界面
            //3.要求手势绘制只能一次
            //4.绘制成功后，界面跳转到验证手势界面
            //5.当已绘制手势，点击game，进入到手势验证界面
        });
    }
}
