package com.example.a1407232261.test0215;

import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {
    private ImageView upToDown;
    private Button up,down,left,right;

    AnimationDrawable ad;//图像变化
    ObjectAnimator ob;//图像位置效果的变化

    String state="stop";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //逐帧动画
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        upToDown = (ImageView) findViewById(R.id.upToDown);
        ad=(AnimationDrawable) upToDown.getBackground();
        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        ob=ObjectAnimator.ofFloat(upToDown,"translationY",0,300);
        ob.setDuration(5000);
        ob.setInterpolator(new LinearInterpolator());
        ob.setRepeatCount(-1);
        ob.setRepeatMode(ObjectAnimator.RESTART);
        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==event.ACTION_DOWN){
                    ad.start();
                    if(state.equals("stop")){
                        ob.start();
                    }else if(state.equals("pause")){
                        ob.resume();
                    }

                }else if(event.getAction()==event.ACTION_UP){
                    ad.stop();
                    ob.pause();
                    state="pause";
                }
                return false;
            }
        });
    }
}
