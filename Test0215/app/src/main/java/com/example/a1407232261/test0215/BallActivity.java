package com.example.a1407232261.test0215;

import android.app.Activity;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BallActivity extends Activity {
    public static TextView xy;
    RelativeLayout root;
    boolean isFlag=false;
    int Radius=20;
   public static BallActivity ball;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
        ball=this;
        xy = (TextView) findViewById(R.id.xy);
        root=(RelativeLayout)findViewById(R.id.root);
        DrawView drawView=new DrawView(BallActivity.this);
        drawView.setMinimumHeight(650);
        drawView.setMinimumWidth(400);
        drawView.R_Radius=Radius;
        root.addView(drawView);

    }
    public void show(BallActivity ball){
        AlertDialog.Builder ah = new AlertDialog.Builder(ball);
        ah.setTitle("提示");
        ah.setMessage("恭喜你赢了！！！");
        ah.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isFlag=false;
            }
        } );
        ah.setCancelable(false);
        ah.create();
        if(!isFlag){
            isFlag=true;
            ah.show();
            Radius+=10;
            onCreate(null);
        }else{
            return;
        }
    }
    //每次碰到红球，红球变大10
}
