package com.tao.gldemo.chapter7_airhockey5_textured;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.tao.gldemo.LogUtils;
import com.tao.gldemo.R;
import com.tao.gldemo.chapter6_airhockey4_3D.MatrixHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glVertexAttribPointer;

public class AirHockey5Renderer implements GLSurfaceView.Renderer {
    private static final String TAG = "AirHockey5Renderer";
    private final Context mContext;
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table table;
    private Mallet mallet;
    private Cup cup;

    private TextureShaderProgram textureProgram;
    private TextureShaderProgram cupProgram;
    private ColorShaderProgram colorProgram;

    private int texture;
    private int cupTexture;

    public AirHockey5Renderer(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //清屏颜色为黑色
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet();
        cup = new Cup();

        textureProgram = new TextureShaderProgram(mContext);
        cupProgram = new TextureShaderProgram(mContext);
        colorProgram = new ColorShaderProgram(mContext);

        //用于绘制桌面的纹理
        texture = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface);
        cupTexture = TextureHelper.loadTexture(mContext,R.drawable.pikaqiu);

        LogUtils.d(TAG,"onSurfaceCreated texture: " + texture +
                ", texturePkq: " + cupTexture);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
                / (float) height, 1f, 10f);

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f);
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,projectionMatrix,0);
        // Clear the rendering surface.
        //清除屏幕
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

//        Matrix.setIdentityM(projectionMatrix,0);

        // Draw the table.
        //使用这个shader程序
        textureProgram.useProgram();
        //把投影矩阵，和纹理传递
        textureProgram.setUniforms(projectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        // Draw the cup
        cupProgram.useProgram();
        cupProgram.setUniforms(projectionMatrix, cupTexture);
        cup.bindData(cupProgram);
        cup.draw();

        // Draw the mallets.
        colorProgram.useProgram();
        colorProgram.setUniforms(projectionMatrix);
        mallet.bindData(colorProgram);
        mallet.draw();
    }
}
