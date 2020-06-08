package com.example.a1407232261.test0215;

import android.content.Intent;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView welName;
    Button fl,record,time,alertdialog,friend,note,myInfo,r_b,game,compass,acfun;
    String name,pwd,age;

    GestureLibrary gestureLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welName =(TextView)findViewById(R.id.textView7);
        fl=(Button)findViewById(R.id.flashlight) ;
        record=(Button)findViewById(R.id.record) ;
        time=(Button)findViewById(R.id.time);
        friend=(Button)findViewById(R.id.friend);
        alertdialog=(Button)findViewById(R.id.alertdialog);
        note = (Button) findViewById(R.id.note);
        myInfo = (Button) findViewById(R.id.myInfo);
        game = (Button) findViewById(R.id.game);
        compass = (Button) findViewById(R.id.compass);
        acfun = (Button) findViewById(R.id.acfun);
        r_b = (Button) findViewById(R.id.R_B);
        acfun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,DanmuActivity.class);
                startActivity(intent);
            }
        });
        r_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,CountdownActivity.class);
                startActivity(intent);
            }
        });
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,CompassActivity.class);
                startActivity(intent);
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestureLibrary= GestureLibraries.fromFile("/mnt/sdcard/fusheng");
                if(gestureLibrary.load()){
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,CheckGestrueActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,GestrueActivity.class);
                    startActivity(intent);
                }

            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });
        alertdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,AlterDialogActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        if(intent.getStringExtra("name")!=null&&intent.getStringExtra("pwd")!=null&&intent.getStringExtra("age")!=null) {
            welName.setText("欢迎您:" + intent.getStringExtra("name"));
            name=intent.getStringExtra("name");
            pwd=intent.getStringExtra("pwd");
            age=intent.getStringExtra("age");
        }

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,FlashLightActivity.class);
                startActivity(intent);
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,TimeActivity.class);
                startActivity(intent);
            }
        });
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ContentActivity.class);
                startActivity(intent);
            }
        });
        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("name",name);
                intent.putExtra("pwd",pwd);
                intent.putExtra("age",age);
                intent.setClass(MainActivity.this,MyInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
