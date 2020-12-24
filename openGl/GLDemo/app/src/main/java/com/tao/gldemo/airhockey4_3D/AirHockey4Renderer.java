package com.tao.gldemo.airhockey4_3D;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.tao.gldemo.R;
import com.tao.gldemo.airhockey1.ShaderHelper;
import com.tao.gldemo.airhockey1.TextResourceReader;

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

public class AirHockey4Renderer implements GLSurfaceView.Renderer {
//    private static final int POSITION_COMPONENT_COUNT = 4;//增加z,w分量
    private static final int POSITION_COMPONENT_COUNT = 2;//还原，用投影矩阵，只需要x,y
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
    private static final String U_MATRIX = "u_Matrix";

    //    private int uColorLocation;
    private int aPositionLocation;
    private int aColorLocation;
    private int uMatrixLocation;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    public AirHockey4Renderer(Context context) {
        float[] tableVertices = {
                0f, 0f,
                0f, 14f,
                9f, 14f,
                9f, 0f
        };
        /*float[] tableVerticesWithTriangles = {
                // Order of coordinates: X, Y,Z,W R, G, B
                // Triangle Fan
                   0f,    0f,  0f, 1.5f, 1f, 1f, 1f,//中心点
                -0.5f, -0.8f,  0f,   1f, 1f, 0f, 0f,//左下
//                -0.3f, -0.6f, 0.7f, 0.7f, 0.7f,
//                0.2f, -0.6f, 0.7f, 0.7f, 0.7f,

                0.5f, -0.8f,  0f,   1f, 0f, 0f, 1f,//右下
//                0.6f, -0.3f, 0.7f, 0.7f, 0.7f,
//                0.6f, 0.3f, 0.7f, 0.7f, 0.7f,

                0.5f,  0.8f,   0f,  2f, 0f, 1f, 0f,//右上
//                0.2f, 0.6f, 0.7f, 0.7f, 0.7f,
//                -0.3f, 0.6f, 0.7f, 0.7f, 0.7f,


                -0.5f, 0.8f,   0f,  2f, 0f, 0f, 1f,//左上
//                -0.6f, 0.2f, 0.7f, 0.7f, 0.7f,
//                -0.6f, -0.2f, 0.7f, 0.7f, 0.7f,

                -0.5f, -0.8f,  0f,  1f, 1f, 0f, 0f,//左下

                // Line 1
                -0.5f,   0f,  0f, 1.5f, 1f, 0f, 0f,
                0.5f,    0f,  0f, 1.5f, 1f, 0f, 0f,

                // Mallets
                0f,   -0.4f,  0f, 1.25f, 0f, 0f, 1f,
                0f,   0.25f,  0f, 1.75f, 1f, 0f, 0f

        };*/


        float[] tableVerticesWithTriangles = {
                // Order of coordinates: X, Y,Z,W R, G, B
                // Triangle Fan
                0f,    0f,     1f, 1f, 1f,//中心点
                -0.5f, -0.8f,  1f, 0f, 0f,//左下
//                -0.3f, -0.6f, 0.7f, 0.7f, 0.7f,
//                0.2f, -0.6f, 0.7f, 0.7f, 0.7f,

                0.5f, -0.8f,   0f, 0f, 1f,//右下
//                0.6f, -0.3f, 0.7f, 0.7f, 0.7f,
//                0.6f, 0.3f, 0.7f, 0.7f, 0.7f,

                0.5f,  0.8f,   0f, 1f, 0f,//右上
//                0.2f, 0.6f, 0.7f, 0.7f, 0.7f,
//                -0.3f, 0.6f, 0.7f, 0.7f, 0.7f,


                -0.5f, 0.8f,   0f, 0f, 1f,//左上
//                -0.6f, 0.2f, 0.7f, 0.7f, 0.7f,
//                -0.6f, -0.2f, 0.7f, 0.7f, 0.7f,

                -0.5f, -0.8f,  1f, 0f, 0f,//左下

                // Line 1
                -0.5f,   0f,   1f, 0f, 0f,
                0.5f,    0f,   1f, 0f, 0f,

                // Mallets
                0f,   -0.4f,   0f, 0f, 1f,
                0f,   0.25f,   1f, 0f, 0f

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
                .readTextFileFromResource(mContext, R.raw.simple_vertex_shader4);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.simple_fragment_shader4);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);


        ShaderHelper.validateProgram(program);


        glUseProgram(program);

//        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aColorLocation = glGetAttribLocation(program, A_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);


        // Bind our data, specified by the variable vertexData, to the vertex
        // attribute at location A_POSITION_LOCATION.
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT,
                false, STRIDE, vertexData);

        glEnableVertexAttribArray(aPositionLocation);

        // Bind our data, specified by the variable vertexData, to the vertex
        // attribute at location A_COLOR_LOCATION.
        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT,
                false, STRIDE, vertexData);
        glEnableVertexAttribArray(aColorLocation);


    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);
       /* final float aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;
        if (width > height) {
            //横屏
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio,aspectRatio,
                    -1f,1f,-1f,1f);
        } else {
            //竖屏
            Matrix.orthoM(projectionMatrix,0,-1f,1f,
                    -aspectRatio,aspectRatio,-1f,1f);
        }*/

       //创建投影矩阵，基于y轴的视野 45，计算焦距
       MatrixHelper.perspectiveM(projectionMatrix,45,
               (float)width/(float)height, 1f,10f);

       Matrix.setIdentityM(modelMatrix,0);
       //将曲棍球桌子移到距离内
       Matrix.translateM(modelMatrix,0,0f,0f,-2.5f);
       //增加旋转
       Matrix.rotateM(modelMatrix,0,-60f,1f,0f,0f);

       final float[] temp = new float[16];
       //相乘后，保存
       Matrix.multiplyMM(temp,0,projectionMatrix,0,modelMatrix,0);
       System.arraycopy(temp,0,projectionMatrix,0,temp.length);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,projectionMatrix,0);
        // Draw the table.
//        glDrawArrays(GL_TRIANGLE_FAN, 0, 14);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        // Draw the center dividing line.
//        glDrawArrays(GL_LINES, 14, 2);
        glDrawArrays(GL_LINES, 6, 2);

        // Draw the first mallet.
//        glDrawArrays(GL_POINTS, 16, 1);
        glDrawArrays(GL_POINTS, 8, 1);

        // Draw the second mallet.
//        glDrawArrays(GL_POINTS, 17, 1);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
