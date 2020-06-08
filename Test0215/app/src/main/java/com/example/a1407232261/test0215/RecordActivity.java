package com.example.a1407232261.test0215;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class RecordActivity extends AppCompatActivity {
    private ImageView stopView;
    private WebView playView;
    private Button start;
    private Button stop;
    private Button play;
    private Button stopplay;
    private  String FileName;
    private  MediaRecorder mRecord;
    private  MediaPlayer mPlayer;
    private static  String[] PERMISSIONS_STORGE={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    };
    //权限的请求编码
    private  static  int REQUEST_PERMISSION_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        //检测系统版本号
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,PERMISSIONS_STORGE,REQUEST_PERMISSION_CODE);
            }
        }

        stopView= (ImageView) findViewById(R.id.stopView);
        playView = (WebView) findViewById(R.id.playView);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        play = (Button) findViewById(R.id.play);
        stopplay = (Button) findViewById(R.id.stopplay);
        playView.loadUrl("file:///android_asset/record.gif");
        playView.getSettings().setDomStorageEnabled(true);

        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileName+="/myRecord.amr";
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playView.setVisibility(View.VISIBLE);
                stopView.setVisibility(View.INVISIBLE);

              mRecord = new MediaRecorder();
                mRecord.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecord.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                mRecord.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecord.setOutputFile(FileName);
                try {
                    mRecord.prepare();
                    mRecord.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopView.setVisibility(View.VISIBLE);
                playView.setVisibility(View.INVISIBLE);
                mRecord.stop();
                mRecord.release();
                mRecord=null;
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(FileName);
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        stopplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer=null;
            }
        });
    }
}
