package com.tao.gldemo.chapter9;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tao.gldemo.LogUtils;

public class AirHockey9Activity extends AppCompatActivity {
    private static final String TAG = "AirHockey9Activity";

    GLSurfaceView glSurfaceView;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);
        // Request an OpenGL ES 2.0 compatible context.
        glSurfaceView.setEGLContextClientVersion(2);
        //glSurfaceView.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        final AirHockey9Renderer airHockey9Renderer = new AirHockey9Renderer(this);

        glSurfaceView.setRenderer(airHockey9Renderer);
        setContentView(glSurfaceView);

        glSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    // Convert touch coordinates into normalized device
                    // coordinates, keeping in mind that Android's Y
                    // coordinates are inverted.
                    //视图左上角映射到（0，0），右下角映射的坐标等于视图大小。
                    //把触控坐标转化为归一化坐标。还需要把y轴反转
                    // 左上（-1，1），右上 （1，1）
                    //左下（-1，-1），右下（1，-1）；
                    final float normalizedX =
                            (event.getX() / (float) v.getWidth()) * 2 - 1;
                    final float normalizedY =
                            -((event.getY() / (float) v.getHeight()) * 2 - 1);
                    LogUtils.d(TAG,"( " + event.getX()
                        + ", " + event.getY() + " ) -> ( "
                        + normalizedX + " , " + normalizedY + " )");
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        glSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                airHockey9Renderer.handleTouchPress(
                                        normalizedX, normalizedY);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        glSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                airHockey9Renderer.handleTouchDrag(
                                        normalizedX, normalizedY);
                            }
                        });
                    }

                    return true;
                } else {
                    return false;
                }
            }
        });
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