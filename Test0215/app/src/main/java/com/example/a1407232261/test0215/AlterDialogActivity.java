package com.example.a1407232261.test0215;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlterDialogActivity extends AppCompatActivity {
    Button btn1,btn2,btn3,btn4,btn5;
    String star[]={"天马座","天龙座","仙女座","处女座"};
    String hobby[]={"吃饭","睡觉","打豆豆"};
    TextView choicStar,choichobby;
    String xingzuo;
    String h="";
    boolean hobbyChoice[]={false,false,false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_dialog);
        btn1=(Button)findViewById(R.id.button3);
        btn2=(Button)findViewById(R.id.button4);
        btn3=(Button)findViewById(R.id.button5) ;
        btn4=(Button)findViewById(R.id.button7) ;
        btn5=(Button)findViewById(R.id.button8) ;
        choicStar=(TextView)findViewById(R.id.textView10);
        choichobby=(TextView)findViewById(R.id.textView12);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab=new AlertDialog.Builder(AlterDialogActivity.this);
                ab.setIcon(R.mipmap.logo);
                ab.setTitle("警告");
                ab.setMessage("您是否要退出？");
                ab.create().show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab=new AlertDialog.Builder(AlterDialogActivity.this);
                ab.setIcon(R.mipmap.logo);
                ab.setTitle("警告");
                ab.setMessage("您是否要退出？");
                ab.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       AlterDialogActivity.this.finish();
                        //System.exit(0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
                ab.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlterDialogActivity.this,"明智的选择",Toast.LENGTH_SHORT).show();
                    }
                });
                ab.create().show();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab=new AlertDialog.Builder(AlterDialogActivity.this);
                ab.setIcon(R.mipmap.logo);
                ab.setTitle("请选择你的星座？");
                ab.setItems(star, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        choicStar.setText(star[i]);
                    }
                });
                ab.create().show();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab=new AlertDialog.Builder(AlterDialogActivity.this);
                ab.setIcon(R.mipmap.logo);
                ab.setTitle("请选择你的星座？");
                ab.setSingleChoiceItems(star, -1,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        xingzuo=star[i];
                    }
                });
                ab.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        choicStar.setText(xingzuo);
                    }
                });
                ab.setNegativeButton("否",null);
                ab.create().show();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab=new AlertDialog.Builder(AlterDialogActivity.this);
                ab.setIcon(R.mipmap.logo);
                ab.setTitle("请选择你的爱好？");
                ab.setMultiChoiceItems(hobby, hobbyChoice, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                        hobbyChoice[i]=isChecked;
                    }
                });
                ab.setNegativeButton("否", null);
                ab.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for(int i=0;i<hobbyChoice.length;i++){
                            if(hobbyChoice[i]==true) {
                                h =h+ hobby[i] + "\t";
                            }
                        }
                        choichobby.setText(h);
                    }
                });

                ab.create().show();
            }
        });
    }
}
