package com.tao.gldemo.chapter8;

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

public class AirHockey8Renderer implements GLSurfaceView.Renderer {
    private static final String TAG = "AirHockey5Renderer";
    private final Context mContext;
    //正交矩阵，调整宽高比
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    //视图矩阵，等同于相机，来回移动相机，从不同视角看到东西
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    private Table table;
    private Mallet mallet;
    //private Cup cup;
    private Puck puck;

    private TextureShaderProgram textureProgram;
    private TextureShaderProgram cupProgram;
    private ColorShaderProgram colorProgram;

    private int texture;
    private int cupTexture;

    public AirHockey8Renderer(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet(0.08f, 0.15f, 32);
        puck = new Puck(0.06f, 0.02f, 32);

        textureProgram = new TextureShaderProgram(mContext);
        colorProgram = new ColorShaderProgram(mContext);

        texture = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface);

        LogUtils.d(TAG,"onSurfaceCreated texture: " + texture +
                ", texturePkq: " + cupTexture);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        //设置视口，创建投影矩阵：联合了正交矩阵
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
                / (float) height, 1f, 10f);

        //创建一个特殊的视图矩阵，把眼睛设为(0, 1.2f, 2.2f)，意味着
        //眼睛位置在x-y上方1.2个单位，且向后2.2个单位
        //把中心设置为(0,0,0)，将向下看前面的原点。
        //把指向设为(0,1,0),你的头是笔直向上的，不会旋转到任何地方
        Matrix.setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f,
                0f, 0f, 0f, 0f, 1f, 0f);

    }

    private float mDeltaDegree = 0f;

    @Override
    public void onDrawFrame(GL10 gl10) {
        // Clear the rendering surface.
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

        /**
         * 通过改变视图矩阵，实现桌面旋转
         */
        mDeltaDegree += 1.0f;
        float eyeX = (float) (2.2f * Math.sin(Math.toRadians(mDeltaDegree)));
        float eyeZ = (float) (2.2f * Math.cos(Math.toRadians(mDeltaDegree)));

        Matrix.setLookAtM(viewMatrix, 0, eyeX, 1.2f, eyeZ,
                0f, 0f, 0f, 0f, 1f, 0f);
        // Multiply the view and projection matrices together.
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        // Draw the table.
        positionTableInScene();
        textureProgram.useProgram();
        textureProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        // Draw the mallets.
        positionObjectInScene(0f, mallet.height / 2f, -0.4f);
        colorProgram.useProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mallet.bindData(colorProgram);
        mallet.draw();

        positionObjectInScene(0f, mallet.height / 2f, 0.4f);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        // Note that we don't have to define the object data twice -- we just
        // draw the same mallet again but in a different position and with a
        // different color.
        mallet.draw();

        // Draw the puck.
        positionObjectInScene(0f, puck.height / 2f, 0f);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);
        puck.bindData(colorProgram);
        puck.draw();
    }

    // 旋转x轴90度，桌台直接放到x-z平面，不需要旋转了
    private void positionTableInScene() {
        // The table is defined in terms of X & Y coordinates, so we rotate it
        // 90 degrees to lie flat on the XZ plane.
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }

    // The mallets and the puck are positioned on the same plane as the table.
    // 冰球和木槌直接放到x-z平面，不需要旋转了
    private void positionObjectInScene(float x, float y, float z) {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, x, y, z);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }
}
