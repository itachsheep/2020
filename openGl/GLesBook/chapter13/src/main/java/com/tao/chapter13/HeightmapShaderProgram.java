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

import com.tao.utils.Geometry;
import com.tao.utils.Geometry.*;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform3f;
import static android.opengl.GLES20.glUniform3fv;
import static android.opengl.GLES20.glUniformMatrix4fv;


public class HeightmapShaderProgram extends ShaderProgram {
    private final int uMatrixLocation;
    private final int aPositionLocation;

    private final int uVectorToLightLocation;
    private final int aNormalLocation;

    public HeightmapShaderProgram(Context context) {
        super(context, R.raw.heightmap_vertex_shader,
            R.raw.heightmap_fragment_shader);
        
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);

        aNormalLocation = glGetAttribLocation(program, A_NORMAL);
        uVectorToLightLocation = glGetUniformLocation(program, U_VECTOR_TO_LIGHT);
    }

    public void setUniforms(float[] matrix, Vector vectorToLight) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        glUniform3f(uVectorToLightLocation,
                vectorToLight.x, vectorToLight.y, vectorToLight.z);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getNormalAttributeLocation() {
        return aNormalLocation;
    }
}
