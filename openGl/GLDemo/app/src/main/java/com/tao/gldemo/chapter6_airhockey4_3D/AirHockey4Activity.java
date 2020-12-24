package com.tao.gldemo.chapter6_airhockey4_3D;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AirHockey4Activity extends AppCompatActivity {
    GLSurfaceView glSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);
        // Request an OpenGL ES 2.0 compatible context.
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        glSurfaceView.setRenderer(new AirHockey4Renderer(this));
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