package com.tao.ffmpegdecoder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("libffmpeg_decoder");
    }

    /** 原始的文件路径 **/
    private static String mp3FilePath = "/storage/emulated/0/111//131.mp3";
    /** 解码后的PCM文件路径 **/
    private static String pcmFilePath = "/storage/emulated/0/111//131.pcm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }


    public void on_bt_decode(View view) {

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native int init(String mp3FilePath, String pcmFilePath);

    public native void decode();

    public native void destroy();
}
