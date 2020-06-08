package com.example.a1407232261.test0215;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyInfoActivity extends AppCompatActivity {
    private TextView name;
    private TextView pwd;
    private TextView age;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        name = (TextView) findViewById(R.id.name);
        pwd = (TextView) findViewById(R.id.pwd);
        age = (TextView) findViewById(R.id.age);
        Intent intent = getIntent();
        if(intent.getStringExtra("name")!=null&&intent.getStringExtra("pwd")!=null&&intent.getStringExtra("age")!=null) {
            name.setText("用户名："+intent.getStringExtra("name"));
            pwd.setText("密码："+intent.getStringExtra("pwd"));
            age.setText("年龄："+intent.getStringExtra("age"));
        }
    }
}
