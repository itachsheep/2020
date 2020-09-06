package com.tao.ndktest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("audioencoder");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED){
            ///没有 CALL_PHONE 权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        LogUtils.i(TAG,"onCreate absolutePath: " + absolutePath);

    }

    public void bt_add(View view) {
        int add = add(100, 200);
        TextView tv = (TextView) findViewById(R.id.tv_result);
        tv.setText("result = " + add);
    }

    public void bt_encode(View view) {

        String pcmPath = "/storage/emulated/0/111/vocal.pcm";
        int audioChannels = 2;
        int bitRate = 128 * 1024;
        int sampleRate = 44100;
        String mp3Path = "/storage/emulated/0/111/vocal.mp3";
        File file = new File(pcmPath);
        if(file != null) {
            LogUtils.i(TAG,"bt_encode file exsit ? " + file.exists());
        } else {
            LogUtils.i(TAG,"bt_encode file no exsit ");
        }
        int ret = init(pcmPath, audioChannels, bitRate, sampleRate, mp3Path);
        if(ret >= 0) {
            encode();
            destroy();
            LogUtils.i(TAG, "Encode Mp3 Success");
        } else {
            LogUtils.i(TAG, "Encoder Initialized Failed...");
        }
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public static native int add(int a, int b);

    public native int init(String pcmPath, int audioChannels, int bitRate, int sampleRate, String mp3Path);
    public native void encode();
    public native void destroy();
}
