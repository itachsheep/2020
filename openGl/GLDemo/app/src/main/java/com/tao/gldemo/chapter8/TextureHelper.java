package com.tao.gldemo.chapter8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.tao.gldemo.LogUtils;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;

public class TextureHelper {

    private static final String TAG = "TextureHelper";

    /**
     * Loads a texture from a resource ID, returning the OpenGL ID for that
     * texture. Returns 0 if the load failed.
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static int loadTexture(Context context, int resourceId) {
        final int[] textureObjectIds = new int[1];
        //创建一个纹理对象，生成的ID 保存在textureObjectIds
        GLES20.glGenTextures(1, textureObjectIds, 0);

        if (textureObjectIds[0] == 0) {
            if (true) {
                LogUtils.w(TAG, "Could not generate a new OpenGL texture object.");
            }
            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        // Read in the resource
        final Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(), resourceId, options);

        if (bitmap == null) {
            if (true) {
                LogUtils.w(TAG, "Resource ID " + resourceId + " could not be decoded.");
            }

            GLES20.glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }
        // Bind to the texture in OpenGL
        //告诉opengl后面纹理调用应用与这个纹理对象，作为二维纹理对待，绑定到的纹理对象ID
        GLES20.glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);

        // Set filtering: a default must be set, or the texture will be
        // black.
        //当纹理被扩大或者缩小，需要过滤器
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        // Load the bitmap into the bound texture.
        //加载位图数据到opengl，并复制到到当前绑定的纹理中，这个时候可以释放bitmap了
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        // Note: Following code may cause an error to be reported in the
        // ADB log as follows: E/IMGSRV(20095): :0: HardwareMipGen:
        // Failed to generate texture mipmap levels (error=3)
        // No OpenGL error will be encountered (glGetError() will return
        // 0). If this happens, just squash the source image to be
        // square. It will look the same because of texture coordinates,
        // and mipmap generation will work.
        //生成mip贴图
        GLES20.glGenerateMipmap(GL_TEXTURE_2D);

        // Recycle the bitmap, since its data has been loaded into
        // OpenGL.
        bitmap.recycle();

        // Unbind from the texture.
        //传递0，表示与当前纹理 解除绑定
        GLES20.glBindTexture(GL_TEXTURE_2D, 0);
        //返回纹理对象ID，用于纹理引用
        return textureObjectIds[0];
    }
}
