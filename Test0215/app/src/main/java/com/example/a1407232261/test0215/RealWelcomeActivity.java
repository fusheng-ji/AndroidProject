package com.example.a1407232261.test0215;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RealWelcomeActivity extends AppCompatActivity {
boolean isFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_welcome);

        //读取数据
        SharedPreferences sp = getSharedPreferences("isFirst",MODE_PRIVATE);
        isFirst = sp.getBoolean("isFirst",true);
        if(isFirst){
            startActivity(new Intent(RealWelcomeActivity.this,Welcom2Activity.class));
        }else{
            startActivity(new Intent(RealWelcomeActivity.this,WelcomActivity.class));
        }
        //如果用户是第一次进入滑动Image界面，进入到倒计时界面
        finish();
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isFirst",false);
        editor.commit();

    }
    //动画效果：旋转RotateAnimation，伸缩ScaleAnimation，位移TranslateAnimation，透明AlphaAnimation
    //DRAW画板

}
