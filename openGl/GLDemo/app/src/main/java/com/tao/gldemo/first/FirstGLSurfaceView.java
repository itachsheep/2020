package com.tao.gldemo.first;


import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT;

public class FirstGLSurfaceView extends GLSurfaceView {
    public FirstGLSurfaceView(Context context) {
        this(context,null);
    }

    public FirstGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRenderer(new FirstRender());
    }
}

class FirstRender implements GLSurfaceView.Renderer {

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(1.0f,0f,0f,0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // Clear the rendering surface.
        GLES20.glClear(GL_COLOR_BUFFER_BIT);
    }
}
