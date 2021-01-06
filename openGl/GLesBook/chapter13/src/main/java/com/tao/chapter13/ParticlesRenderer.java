/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
***/
package com.tao.chapter13;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.GLSurfaceView.Renderer;

import com.tao.utils.Geometry.*;
import com.tao.utils.MatrixHelper;
import com.tao.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_LEQUAL;
import static android.opengl.GLES20.GL_LESS;
import static android.opengl.GLES20.GL_ONE;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDepthFunc;
import static android.opengl.GLES20.glDepthMask;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.scaleM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;


public class ParticlesRenderer implements Renderer {    
    private final Context context;

    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewMatrixForSkybox = new float[16];
    private final float[] projectionMatrix = new float[16];        
    
    private final float[] tempMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private HeightmapShaderProgram heightmapProgram;
    private Heightmap heightmap;
      
    private SkyboxShaderProgram skyboxProgram;
    private Skybox skybox;   
    
    private ParticleShaderProgram particleProgram;      
    private ParticleSystem particleSystem;
    private ParticleShooter redParticleShooter;
    private ParticleShooter greenParticleShooter;
    private ParticleShooter blueParticleShooter;     

    private long globalStartTime;    
    private int particleTexture;
    private int skyboxTexture;
    
    private float xRotation, yRotation;  

    public ParticlesRenderer(Context context) {
        this.context = context;
    }

    public void handleTouchDrag(float deltaX, float deltaY) {
        xRotation += deltaX / 16f;
        yRotation += deltaY / 16f;
        
        if (yRotation < -90) {
            yRotation = -90;
        } else if (yRotation > 90) {
            yRotation = 90;
        } 
        
        // Setup view matrix
        updateViewMatrices();        
    }
    
    private void updateViewMatrices() {
        setIdentityM(viewMatrix, 0);
        rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
        rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f);
        System.arraycopy(viewMatrix, 0, viewMatrixForSkybox, 0, viewMatrix.length);
        
        // We want the translation to apply to the regular view matrix, and not
        // the skybox.
        translateM(viewMatrix, 0, 0, -1.5f, -5f);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        //打开深度缓冲区功能，当这个缓冲区打开时，opengl会为每个片段执行深度测试算法
        //如果片段比已经存在的片段更近，就绘制它，否则丢弃，主要是看这个片段是否被
        //已经存在的片段遮挡
        glEnable(GL_DEPTH_TEST);

        //剔除技术：消除隐藏面，因为地形图的下方没有什么用处，所以
        //告诉opengl关闭两面绘制，削减绘制开销
        glEnable(GL_CULL_FACE);
        
        heightmapProgram = new HeightmapShaderProgram(context);
        heightmap = new Heightmap(((BitmapDrawable)context.getResources()
            .getDrawable(R.drawable.heightmap)).getBitmap());
        
        skyboxProgram = new SkyboxShaderProgram(context);
        skybox = new Skybox();      
        
        particleProgram = new ParticleShaderProgram(context);        
        particleSystem = new ParticleSystem(10000);        
        globalStartTime = System.nanoTime();
        
        final Vector particleDirection = new Vector(0f, 0.5f, 0f);
        final float angleVarianceInDegrees = 5f; 
        final float speedVariance = 1f;
            
        redParticleShooter = new ParticleShooter(
            new Point(-1f, 0f, 0f), 
            particleDirection,                
            Color.rgb(255, 50, 5),            
            angleVarianceInDegrees, 
            speedVariance);
        
        greenParticleShooter = new ParticleShooter(
            new Point(0f, 0f, 0f), 
            particleDirection,
            Color.rgb(25, 255, 25),            
            angleVarianceInDegrees, 
            speedVariance);
        
        blueParticleShooter = new ParticleShooter(
            new Point(1f, 0f, 0f), 
            particleDirection,
            Color.rgb(5, 50, 255),            
            angleVarianceInDegrees, 
            speedVariance); 
                
        particleTexture = TextureHelper.loadTexture(context, R.drawable.particle_texture);

        skyboxTexture = TextureHelper.loadCubeMap(context, 
            new int[] { R.drawable.left, R.drawable.right,
                        R.drawable.bottom, R.drawable.top, 
                        R.drawable.front, R.drawable.back});
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {                
        glViewport(0, 0, width, height);
        //100f,在被远平面裁剪之前最大100单位绘制
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
            / (float) height, 1f, 100f);
//        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
//            / (float) height, 1f, 10f);
        updateViewMatrices();
    }

    @Override    
    public void onDrawFrame(GL10 glUnused) {
        /*
        glClear(GL_COLOR_BUFFER_BIT);
         */
        //打开深度缓冲，为了粒子能不掉进"地里"，达到掉下去后被山体遮挡效果
        //打开深度缓冲区功能后GL_DEPTH_TEST，需要 GL_DEPTH_BUFFER_BIT ，
        //每一帧清除深度缓冲区
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        //glClear(GL_COLOR_BUFFER_BIT);


        //drawSkybox();
        drawHeightmap();
        drawSkybox();
        drawParticles();
    }
    private void drawHeightmap() {
        setIdentityM(modelMatrix, 0);  
        // Expand the heightmap's dimensions, but don't expand the height as
        // much so that we don't get insanely tall mountains.
        scaleM(modelMatrix, 0, 100f, 10f, 100f);
        updateMvpMatrix();
        heightmapProgram.useProgram();
        heightmapProgram.setUniforms(modelViewProjectionMatrix);
        heightmap.bindData(heightmapProgram);
        heightmap.draw(); 
    }
    
    private void drawSkybox() {   
        setIdentityM(modelMatrix, 0);
        updateMvpMatrixForSkybox();


        //打开深度缓冲，导致天空盒绘制不出来，暂时改变深度缓冲算法

        //opengl默认情况只绘制比其他片段更近，或者比远平面更近的片段，
        //深度测试打开带来一个问题，看不到天空盒的任何部分，
        //改变深度测试算法，新片段和已经存在的片段较近，或者在同等距离，绘制它
        glDepthFunc(GL_LEQUAL); // This avoids problems with the skybox itself getting clipped.
        skyboxProgram.useProgram();
        skyboxProgram.setUniforms(modelViewProjectionMatrix, skyboxTexture);
        skybox.bindData(skyboxProgram);
        skybox.draw();
        glDepthFunc(GL_LESS);
    }
   
    private void drawParticles() {        
        float currentTime = (System.nanoTime() - globalStartTime) / 1000000000f;

        redParticleShooter.addParticles(particleSystem, currentTime, 1);
        greenParticleShooter.addParticles(particleSystem, currentTime, 1);
        blueParticleShooter.addParticles(particleSystem, currentTime, 1);

        setIdentityM(modelMatrix, 0);
        updateMvpMatrix();

        //粒子被地面裁剪，且彼此遮罩
        //glDepthMask：在粒子接触到地面的地方，需要他们彼此不遮挡，且还需要裁剪
        //保持深度缓冲开启的同时，禁用深度更新，粒子将对地面进行针对测试
        //但是测试结果不会进入深度缓冲区，这样就不会彼此阻挡
        glDepthMask(false);
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);

        particleProgram.useProgram();
        particleProgram.setUniforms(modelViewProjectionMatrix, currentTime, particleTexture);
        particleSystem.bindData(particleProgram);
        particleSystem.draw();

        glDisable(GL_BLEND);
        //关闭glDepthMask
        glDepthMask(true);
    }
    
    private void updateMvpMatrix() {
        multiplyMM(tempMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, tempMatrix, 0);
    }   
    private void updateMvpMatrixForSkybox() {
        multiplyMM(tempMatrix, 0, viewMatrixForSkybox, 0, modelMatrix, 0);
        multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, tempMatrix, 0);
    }
}