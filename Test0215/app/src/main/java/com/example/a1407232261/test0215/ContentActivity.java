package com.example.a1407232261.test0215;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentActivity extends AppCompatActivity {
    private TextView textView14;
    private TextView textView15;
    private TextView textView16;
    private EditText fname;
    private EditText sname;
    private Button submit;
    private EditText phone;
    private ListView lv;

    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        textView14 = (TextView) findViewById(R.id.textView14);
        textView15 = (TextView) findViewById(R.id.textView15);
        textView16 = (TextView) findViewById(R.id.textView16);
        fname = (EditText) findViewById(R.id.fname);
        sname = (EditText) findViewById(R.id.sname);
        submit = (Button) findViewById(R.id.submit);
        phone = (EditText) findViewById(R.id.phone);
        lv = (ListView) findViewById(R.id.lv);
        final SimpleAdapter adapter=new SimpleAdapter(
                ContentActivity.this,
                list,
                R.layout.contact,
                new String[]{"fname","sname","phone"},
                new int[]{R.id.textView14,R.id.textView15,R.id.textView16}
        );
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ss=sname.getText().toString().trim();
                String ff=fname.getText().toString().trim();
                String phoneNO=phone.getText().toString().trim();
                if(!ff.equals("")||!ss.equals("")||!phoneNO.equals("")){
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("fname",ff);
                    map.put("sname",ss);
                    map.put("phone",phoneNO);
                    list.add(map);
                    lv.setAdapter(adapter);
                    Toast.makeText(ContentActivity.this,"联系人添加成功",Toast.LENGTH_SHORT).show();
                    fname.setText("");
                    sname.setText("");
                    phone.setText("");
                }else{
                    Toast.makeText(ContentActivity.this,"数据不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String who = (String)((TextView)view.findViewById(R.id.textView14)).getText()+
                        (String)((TextView)view.findViewById(R.id.textView15)).getText();
                String phone = (String)((TextView)view.findViewById(R.id.textView16)).getText();
                Toast.makeText(ContentActivity.this,"点击第"+(i+1)+"条记录，名字是"+who+",手机号码是："+phone,Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int i, long l) {
                //长按时，出现一个弹框，问你是否删除，是的话删除
                AlertDialog.Builder ab=new AlertDialog.Builder(ContentActivity.this);
                ab.setIcon(R.mipmap.logo);
                ab.setTitle("警告");
                ab.setMessage("您是否要删除所选记录？");
                ab.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                });
                ab.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ContentActivity.this,"明智的选择",Toast.LENGTH_SHORT).show();
                    }
                });
                ab.create().show();
                return true;
            }
        });
    }
}
