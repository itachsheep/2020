package com.tao.gldemo.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tao.gldemo.R;

public class FirstActivity extends AppCompatActivity {
    FirstGLSurfaceView glSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        glSurfaceView = findViewById(R.id.glSurface);
//        glSurfaceView.setEGLContextClientVersion(2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }
}