package com.tao.gldemo.chapter8;

import android.content.Context;
import android.opengl.GLES20;

import com.tao.gldemo.R;

public class TextureShaderProgram extends ShaderProgram {
    // Uniform locations
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    // Attribute locations
    private final int aPositionLocation;
    private final int aTextureCoordinatesLocation;

    public TextureShaderProgram(Context context) {
        super(context, R.raw.simple_texture_vertex_shader5,
                R.raw.simple_texture_fragment_shader5);

        // Retrieve uniform locations for the shader program.
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);

        // Retrieve attribute locations for the shader program.
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesLocation =
                GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    ///接受实际的纹理数据，sampler2D 是二维纹理数组
    ////opengl使用纹理绘制，不需要直接给着色器传递纹理，只使用纹理单元(texture unit)保存纹理，
    ////之所以这样，一个gpu只能同时绘制数量有限的纹理，使用纹理单元表示当前正在绘制的纹理
    ////如果需要切换纹理，可以在纹理单元间切换，切换太频繁，会导致渲染速度下降
    public void setUniforms(float[] matrix, int textureId) {
        // Pass the matrix into the shader program.
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        // Set the active texture unit to texture unit 0.
        // 把活动的纹理单元设置为纹理单元0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit.
        //把纹理绑定到这个单元
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read from texture unit 0.
        //把选定的纹理单元传递给片段着色器 "uniform sampler2D u_TextureUnit;"
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getTextureCoordinatesAttributeLocation() {
        return aTextureCoordinatesLocation;
    }
}
