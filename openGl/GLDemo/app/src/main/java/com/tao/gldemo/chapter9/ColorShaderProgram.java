/**
 * @ClassName: ColorShaderProgram
 * @Description:
 * @author taowei
 * @version V1.0
 * @Date 2020.12.23
 */

package com.tao.gldemo.chapter9;

import android.content.Context;
import android.opengl.GLES20;

import com.tao.gldemo.R;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;

public class ColorShaderProgram extends ShaderProgram {
    // Uniform locations
    private final int uMatrixLocation;
    private final int uColorLocation;

    // Attribute locations
    private final int aPositionLocation;
//    private final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.chap9_simple_vertex_shader,
                R.raw.chap9_simple_fragment_shader);
        // Retrieve uniform locations for the shader program.
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        // Retrieve attribute locations for the shader program.
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
//        aColorLocation = glGetAttribLocation(program, A_COLOR);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
    }
    public void setUniforms(float[] matrix, float r, float g, float b) {
        // Pass the matrix into the shader program.
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        GLES20.glUniform4f(uColorLocation, r, g, b, 1f);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

//    public int getColorAttributeLocation() {
//        return aColorLocation;
//    }
}
