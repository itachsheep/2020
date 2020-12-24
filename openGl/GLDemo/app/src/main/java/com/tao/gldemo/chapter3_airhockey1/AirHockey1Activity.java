package com.tao.gldemo.chapter3_airhockey1;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AirHockey1Activity extends AppCompatActivity {
    GLSurfaceView glSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_airhockey1);
        //glSurfaceView = findViewById(R.id.glSurface);

        glSurfaceView = new GLSurfaceView(this);
        // Request an OpenGL ES 2.0 compatible context.
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new AirHockeyRenderer(this));
        setContentView(glSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //glSurfaceView.onPause();
    }
}