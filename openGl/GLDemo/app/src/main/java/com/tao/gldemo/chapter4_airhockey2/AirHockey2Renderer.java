package com.tao.gldemo.chapter4_airhockey2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.tao.gldemo.R;
import com.tao.gldemo.chapter3_airhockey1.ShaderHelper;
import com.tao.gldemo.chapter3_airhockey1.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

public class AirHockey2Renderer implements GLSurfaceView.Renderer {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int BYTES_PER_FLOAT = 4; //浮点数有4个字节
    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private final FloatBuffer vertexData;
    private final Context mContext;

    private int program;
//    private static final String U_COLOR = "u_Color";
    private static final String A_COLOR = "a_Color";
    private static final String A_POSITION = "a_Position";

//    private int uColorLocation;
    private int aPositionLocation;
    private int aColorLocation;

    public AirHockey2Renderer(Context context) {
        float[] tableVertices = {
                0f,  0f,
                0f, 14f,
                9f, 14f,
                9f,  0f
        };
        float[] tableVerticesWithTriangles = {
                // Order of coordinates: X, Y, R, G, B
                // Triangle Fan
                0f,    0f,   1f,   1f,   1f,//中心点
                -0.5f, -0.5f, 1f, 0f, 0f,//左下
                -0.3f, -0.6f, 0.7f, 0.7f, 0.7f,
                0.2f, -0.6f, 0.7f, 0.7f, 0.7f,

                0.5f, -0.5f, 0f, 0f, 1f,//右下
                0.6f, -0.3f, 0.7f, 0.7f, 0.7f,
                0.6f, 0.3f, 0.7f, 0.7f, 0.7f,

                0.5f,  0.5f, 0f, 1f, 0f,//右上
                0.2f,  0.6f, 0.7f, 0.7f, 0.7f,
                -0.3f,  0.6f, 0.7f, 0.7f, 0.7f,


                -0.5f,  0.5f, 0f, 0f, 1f,//左上
                -0.6f,  0.2f, 0.7f, 0.7f, 0.7f,
                -0.6f,  -0.2f, 0.7f, 0.7f, 0.7f,

                -0.5f, -0.5f, 1f, 0f, 0f,//左下

                // Line 1
                -0.5f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 1f, 0f, 0f,

                // Mallets
                0f, -0.25f, 0f, 0f, 1f,
                0f,  0.25f, 1f, 0f, 0f
        };

        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        vertexData.put(tableVerticesWithTriangles);

        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.simple_vertex_shader2);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.simple_fragment_shader2);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);


        ShaderHelper.validateProgram(program);


        glUseProgram(program);

//        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aColorLocation = glGetAttribLocation(program,A_COLOR);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);

        // Bind our data, specified by the variable vertexData, to the vertex
        // attribute at location A_POSITION_LOCATION.
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation,POSITION_COMPONENT_COUNT,GL_FLOAT,
                false,STRIDE,vertexData);

        glEnableVertexAttribArray(aPositionLocation);

        // Bind our data, specified by the variable vertexData, to the vertex
        // attribute at location A_COLOR_LOCATION.
        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation,COLOR_COMPONENT_COUNT,GL_FLOAT,
                false,STRIDE,vertexData);
        glEnableVertexAttribArray(aColorLocation);


    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

        // Draw the table.
        glDrawArrays(GL_TRIANGLE_FAN, 0, 14);

        // Draw the center dividing line.
        glDrawArrays(GL_LINES, 14, 2);

        // Draw the first mallet.
        glDrawArrays(GL_POINTS, 16, 1);

        // Draw the second mallet.
        glDrawArrays(GL_POINTS, 17, 1);
    }
}
