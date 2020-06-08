package com.example.a1407232261.test0215;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class NoteActivity extends AppCompatActivity {
    private EditText inputText;
    private Button save;
    private Button reset;
    private TextView textLength,time;
    Timer timer = new Timer();//计时器内置对象
    Intent intent=new Intent();
    int num = -1;//打开程序的一瞬间显示-1，而用户能看到的是0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        inputText = (EditText) findViewById(R.id.inputText);
        save = (Button) findViewById(R.id.save);
        reset = (Button) findViewById(R.id.reset);
        textLength = (TextView)findViewById(R.id.noteLength);
        time=(TextView)findViewById(R.id.time);

        //onLoad();//打开文件获取文件内容
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //线程执行内容\
                        num++;
                        time.setText("-"+num+"秒 -");
                    }
                });
            }
        };
        timer.schedule(task,1000,1000);
        inputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText.setCursorVisible(true);
            }
        });
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                textLength.setText(inputText.getText().toString().length()+"");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileOutputStream fos = null;
                try{
                    //在原有内容上追加
                    //fos = openFileOutput("txt", Context.MODE_APPEND);
                    //覆盖原有内容
                    fos = openFileOutput("txt",Context.MODE_PRIVATE);
                    String text = inputText.getText().toString();
                    fos.write(text.getBytes());
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    try {
                        if(fos!=null) {
                            fos.flush();
                            Toast.makeText(NoteActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                            fos.close();
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  FileOutputStream fos = null;
                try {
                    fos = openFileOutput("txt",Context.MODE_PRIVATE);
                    String text = "";
                    fos.write(text.getBytes());
                    inputText.setText("");
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    try{
                        if(fos!=null){
                            fos.flush();
                            Toast.makeText(NoteActivity.this,"清空成功",Toast.LENGTH_SHORT).show();
                            fos.close();
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }*/
            //将清空按钮改成获取，打开程序是，不会主动获取文件中的内容，只有点击按钮还能显示文字
                onLoad();
            }
        });
    }

    public void onLoad(){
        FileInputStream fis = null;
        try{
            fis = openFileInput("txt");
            if(fis.available()==0){
                Toast.makeText(NoteActivity.this,"无内容",Toast.LENGTH_SHORT).show();
                return;
            }else{
                byte[] con = new byte[fis.available()];
                while(fis.read(con)!=-1){

                }
                inputText.setText(new String(con));
                inputText.setCursorVisible(false);
                textLength.setText(inputText.getText().toString().length()+"");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
