package com.example.a1407232261.test0215;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CountdownActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView number;
    private Button start;

    Animation animation,animation2;
    int count=4;
    int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        number = (TextView) findViewById(R.id.textView17);
        start = (Button) findViewById(R.id.button9);
        animation= AnimationUtils.loadAnimation(this,R.anim.animation_a);
        animation2= AnimationUtils.loadAnimation(this,R.anim.animation_b);

        start.setOnClickListener(this);


    }
    public int getCount(){
        if(count>1) {
            count--;
        /*if(count<1||count>10){
            count=3;
        }*/
        }
            return count;

    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                if(count<=1){
                    number.setText("游戏开始");
                    handler.sendEmptyMessageDelayed(1,0);
                }else {
                    number.setText(""+getCount());
                    handler.sendEmptyMessageDelayed(0,1000);
                    number.startAnimation(animation);

                }
            }else{
                Intent intent=new Intent();
                intent.setClass(CountdownActivity.this,BallActivity.class);
                startActivity(intent);
                CountdownActivity.this.finish();
            }
        }
    };

    @Override//单独写一个ONCLICK方法，对所有按钮进行监听
    public void onClick(View v) {
        switch (num){
            case 0:
               // Toast.makeText(CountdownActivity.this,"点击次数为奇数",Toast.LENGTH_SHORT).show();
                handler.removeMessages(1);
                handler.sendEmptyMessageDelayed(0,1000);
                num=1;
                break;
           /* case 1:
                //Toast.makeText(CountdownActivity.this,"点击次数为偶数",Toast.LENGTH_SHORT).show();
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(1,1000);
                num=0;
                break;*/
        }
    }
}
