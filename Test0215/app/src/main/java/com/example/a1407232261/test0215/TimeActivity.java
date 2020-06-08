package com.example.a1407232261.test0215;

import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class TimeActivity extends AppCompatActivity {
    private Chronometer cc;
    private Button start;
    private Button pause;
    private Button reset;
    private Button button6;
    Vibrator vv;//震动变量
   long tt;//时间变量
    private TextView record;
    String a[]={"","","","",""};
    String b[]={"","","","",""};
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        cc = (Chronometer) findViewById(R.id.time);
        start = (Button) findViewById(R.id.start);
        pause = (Button) findViewById(R.id.pause);
        reset = (Button) findViewById(R.id.reset);
        button6 = (Button) findViewById(R.id.button6);
        record = (TextView) findViewById(R.id.record);

        vv=(Vibrator)this.getSystemService(VIBRATOR_SERVICE);
        cc.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                String time = cc.getText().toString();
                int newTime = Integer.parseInt(time.substring(0,time.indexOf(":")))*60+Integer.parseInt(time.substring(time.length()-2));
                if(newTime%10==0&&newTime!=0){
                    //震动五秒
                   // vv.vibrate(5000);
                   /* long []patten={5000,1000,5000,1000};//表示震动规律，隔5秒震1秒
                    vv.vibrate(patten,1)//前面时间规律，后面是重复次数;*/
                    vv.vibrate(new long[]{0,500},-1);
                }
            }
        });
        /*cc.start();
        cc.stop();
        cc.setBase(SystemClock.elapsedRealtime());//复位*/
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cc.setBase(SystemClock.elapsedRealtime()-tt);
                start.setEnabled(false);
                pause.setEnabled(true);
                reset.setEnabled(false);
                start.setText("继续");
                cc.start();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tt=SystemClock.elapsedRealtime()-cc.getBase();
                start.setEnabled(true);
                pause.setEnabled(false);
                reset.setEnabled(true);
                cc.stop();
                if(count<=4){
                    a[count]=cc.getText().toString();
                }
                if(count==4){
                    Toast.makeText(TimeActivity.this,"只能同时记录5次时间，若继续则原有时间将被覆盖",Toast.LENGTH_SHORT).show();
                }
                if(count>4){
                    b[0]=a[1];
                    b[1]=a[2];
                    b[2]=a[3];
                    b[3]=a[4];
                    b[4]=cc.getText().toString();
                   for(int i=0;i<b.length;i++){
                       a[i]=b[i];
                   }

                }
                String record2="";
                for(int i=0;i<a.length;i++){
                        record2+=a[i]+"\n";
                }
                record.setText(record2);
                button6.setText("当前名次："+String.valueOf(count+1));
                count++;
                record2="";
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(true);
                start.setText("开始");
                pause.setEnabled(false);
                reset.setEnabled(false);
                tt=0;
                record.setText("");
                count=0;
                for(int i=0;i<a.length;i++){
                    a[i]="";
                }
                button6.setText("当前名次：");
                cc.setBase(SystemClock.elapsedRealtime());
            }
        });
        //点击暂停时，记录当前秒表的时间，并显示在程序中
        //最多可以记录五次，点击重置时，显示的时间也被清空
        //记录到第六次时提示只能记录五次时间
        //记录超过五次，第一次时间被覆盖，后面时间依次向上一行
    }
}
