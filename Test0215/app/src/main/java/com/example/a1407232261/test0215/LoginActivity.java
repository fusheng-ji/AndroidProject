package com.example.a1407232261.test0215;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    EditText  username,pwd,inCode;
    Button login,exit,exitsystem;
    TextView register,us,showCode;//实例化对象在函数外

    dbHelper dbHelper;
    String DB_Name = "mydb";
    SQLiteDatabase db;
    Cursor cursor;
    private CheckBox saveName,savePwd;
    private String sname,spwd;


    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final SharedPreferences sp=getSharedPreferences("sname",MODE_PRIVATE);
        final SharedPreferences sp1=getSharedPreferences("spwd",MODE_PRIVATE);
        //创建连接数据库
        dbHelper = new dbHelper(this, DB_Name, null, 1);
        db = dbHelper.getWritableDatabase();
        savePwd = (CheckBox) findViewById(R.id.savePwd);
        saveName = (CheckBox) findViewById(R.id.saveName);
        username = (EditText) findViewById(R.id.editText);
        pwd = (EditText) findViewById(R.id.editText2);
        inCode = (EditText) findViewById(R.id.editText3);
        login = (Button) findViewById(R.id.button);
        exit = (Button) findViewById(R.id.button2);
        exitsystem = (Button) findViewById(R.id.exitsystem);
        register = (TextView) findViewById(R.id.textView4);
        us = (TextView) findViewById(R.id.textView6);
        showCode = (TextView) findViewById(R.id.textView9);
        sname=sp.getString("sname","");
        spwd=sp1.getString("spwd","");
        if(sp1!=null&&!sp1.equals("")){
            pwd.setText(sp1.getString("spwd",""));
            savePwd.setChecked(true);
        }
        if(sp!=null&&!sp.equals("")){
            username.setText(sp.getString("sname",""));
            saveName.setChecked(true);
        }
        showCode.setText(yzm());
        showCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCode.setText(yzm());
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String un = username.getText().toString();
                String ps = pwd.getText().toString();
                String age ="";
                int count=0;
                cursor = db.query(dbHelper.TB_Name, null, null, null, null, null, "uid ASC");
                cursor.moveToFirst();
                if (!inCode.getText().toString().equals(showCode.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }else{
                    if(un.equals("admin")&&ps.equals("123")){
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, ManagerActivity.class);
                        startActivity(intent);
                        if(saveName.isChecked()==true){
                            //SharedPreferences sp2 = getSharedPreferences("sname",MODE_PRIVATE);
                            sname = username.getText().toString();
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("sname",sname);
                            editor.commit();
                        }else{
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear();
                            editor.commit();
                        }
                        if(savePwd.isChecked()==true){
                            //SharedPreferences sp2 = getSharedPreferences("sname",MODE_PRIVATE);
                            spwd = pwd.getText().toString();
                            SharedPreferences.Editor editor = sp1.edit();
                            editor.putString("spwd",spwd);
                            editor.commit();
                        }else{
                            SharedPreferences.Editor editor = sp1.edit();
                            editor.clear();
                            editor.commit();
                        }
                    }else if(!un.equals("admin")&&ps.equals("123")){
                        Toast.makeText(LoginActivity.this, "用户名错误", Toast.LENGTH_SHORT).show();
                    }else if(un.equals("admin")&&!ps.equals("123")){
                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    }else{
                        while (!cursor.isAfterLast()) {
                            if (un.trim().equals(cursor.getString(1)
                            ) && ps.trim().equals(cursor.getString(2))) {
                                age=cursor.getString(3);
                                flag = true;

                            }else if(!un.trim().equals(cursor.getString(1)
                            ) && ps.trim().equals(cursor.getString(2))){
                                count =1;
                            }else if(un.trim().equals(cursor.getString(1)
                            ) && !ps.trim().equals(cursor.getString(2))){
                                count=2;
                            }
                            cursor.moveToNext();
                        }
                        if (flag == true) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            flag = false;
                            Intent intent = new Intent();
                            intent.putExtra("name", un);
                            intent.putExtra("pwd",ps);
                            intent.putExtra("age",age);
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            if(saveName.isChecked()==true){
                                //SharedPreferences sp2 = getSharedPreferences("sname",MODE_PRIVATE);
                                sname = username.getText().toString();
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("sname",sname);
                                editor.commit();
                            }else{
                                SharedPreferences.Editor editor = sp.edit();
                                editor.clear();
                                editor.commit();
                            }
                            if(savePwd.isChecked()==true){
                                //SharedPreferences sp2 = getSharedPreferences("sname",MODE_PRIVATE);
                                spwd = pwd.getText().toString();
                                SharedPreferences.Editor editor = sp1.edit();
                                editor.putString("spwd",spwd);
                                editor.commit();
                            }else{
                                SharedPreferences.Editor editor = sp1.edit();
                                editor.clear();
                                editor.commit();
                            }
                        } else if (count==1) {
                            Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                        } else if (count==2) {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    count=0;
                }


            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        us.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:18262604858");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
        exitsystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivity.this);
                ab.setIcon(R.mipmap.logo);
                ab.setTitle("警告");
                ab.setMessage("您是否要退出？");
                ab.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginActivity.this.finish();
                        //System.exit(0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
                ab.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(LoginActivity.this, "明智的选择", Toast.LENGTH_SHORT).show();
                    }
                });
                ab.create().show();
            }
        });
        //备忘录
        //将清空按钮改成获取，打开程序是，不会主动获取文件中的内容，只有点击按钮还能显示文字
        //当获取内容为空时，提示无内容
        //程序左上角显示打开时间
        //登陆界面
        //设置超级管理员用户 admin 123会进入ManagerActivity
        //注册时不能使用admin作为用户名
        //在main界面添加myinfo按钮，点击跳转到MyInfoActivity可以显示用户的注册信息

    }
    public  String yzm(){
        String str ="0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
        String str2[]=str.split(",");
        Random rand = new Random();
        int index = 0;
        String randStr ="";
        for(int i=0;i<4;i++){
            index = rand.nextInt(str2.length);
            randStr+=str2[index];
        }
        return randStr;
    }
    long firstTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event){
        long secondTime = System.currentTimeMillis();
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(secondTime-firstTime<2000){
                System.exit(0);
            }else{
                Toast.makeText(LoginActivity.this,"再按一次程序退出",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }
            return  true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
