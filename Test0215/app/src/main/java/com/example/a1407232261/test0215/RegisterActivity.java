package com.example.a1407232261.test0215;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private Button cancel;
    private EditText name;
    private EditText pwd;
    private TextView back;
    private Button exitsystem;
    private EditText age;

    dbHelper dbHelper;
    String DB_Name = "mydb";
    SQLiteDatabase db;
    Cursor cursor;

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.button2);
        name = (EditText) findViewById(R.id.name);
        pwd = (EditText) findViewById(R.id.pwd);
        back = (TextView) findViewById(R.id.back);
        exitsystem = (Button) findViewById(R.id.exitsystem);
        age = (EditText) findViewById(R.id.age);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
                Intent intent=new Intent();
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        //创建连接数据库
        dbHelper = new dbHelper(this,DB_Name,null,1);
        db = dbHelper.getWritableDatabase();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().equals("admin")){
                    Toast.makeText(RegisterActivity.this,"不允许使用admin作为用户名！！！",Toast.LENGTH_SHORT).show();
                }else{
                    ContentValues values = new ContentValues();
                    cursor = db.query(dbHelper.TB_Name,null,null,null,null,null,"uid ASC");
                    cursor.moveToFirst();
                    while(!cursor.isAfterLast()){
                        if(name.getText().toString().trim().equals(cursor.getString(1))){
                            flag = false;
                        }
                        cursor.moveToNext();
                    }
                    if(flag==true){
                        values.put("uname",name.getText().toString().trim());
                        values.put("pwd",pwd.getText().toString().trim());
                        values.put("age",age.getText().toString().trim());
                        long rowId = db.insert(dbHelper.TB_Name,null,values);
                        if(rowId==-1){
                            Toast.makeText(RegisterActivity.this,"发生未知错误，注册失败",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                            RegisterActivity.this.finish();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"用户名已存在！",Toast.LENGTH_SHORT).show();
                        flag = true;
                    }
                }
                }

        });


        exitsystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab=new AlertDialog.Builder(RegisterActivity.this);
                ab.setIcon(R.mipmap.logo);
                ab.setTitle("警告");
                ab.setMessage("您是否要退出？");
                ab.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RegisterActivity.this.finish();
                        //System.exit(0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
                ab.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RegisterActivity.this,"明智的选择",Toast.LENGTH_SHORT).show();
                    }
                });
                ab.create().show();
            }
        });
    }
}
