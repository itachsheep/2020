/**
 * @ClassName: CubeBox
 * @Description:
 * @author taowei
 * @version V1.0
 * @Date
 */

package com.tao.cube;

import com.tao.utils.VertexArray;

import java.nio.ByteBuffer;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.glDrawElements;

public class CubeBox {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private final VertexArray vertexArray;
    private final ByteBuffer indexArray;

    public CubeBox() {
        float ratio = 0.5f;
        /**
         * 立方体的8个顶点
         */
        vertexArray = new VertexArray(new float[] {
                -1 * ratio,  1 * ratio,  1 * ratio,     // (0) Top-left near
                1 * ratio,  1 * ratio,  1 * ratio,     // (1) Top-right near
                -1 * ratio, -1 * ratio,  1 * ratio,     // (2) Bottom-left near
                1 * ratio, -1 * ratio,  1 * ratio,     // (3) Bottom-right near
                -1 * ratio,  1 * ratio, -1 * ratio,     // (4) Top-left far
                1 * ratio,  1 * ratio, -1 * ratio,     // (5) Top-right far
                -1 * ratio, -1 * ratio, -1 * ratio,     // (6) Bottom-left far
                1 * ratio, -1 * ratio, -1 * ratio      // (7) Bottom-right far
        });

        // 6 indices per cube side
        /**
         *  使用三角形绘制每个面，一个面需要两个三角形，需要 2 * 3 * 6 = 36个顶点
         *  使用索引数组，不用重复顶点数据。
         */
        indexArray =  ByteBuffer.allocateDirect(6 * 6)
                .put(new byte[] {
                        // Front
                        1, 3, 0,
                        0, 3, 2,

                        // Back
                        4, 6, 5,
                        5, 6, 7,

                        // Left
                        0, 2, 4,
                        4, 2, 6,

                        // Right
                        5, 7, 1,
                        1, 7, 3,

                        // Top
                        5, 1, 4,
                        4, 1, 0,

                        // Bottom
                        6, 2, 7,
                        7, 2, 3
                });
        indexArray.position(0);
    }

    public void bindData(CubeShaderProgram cubeShaderProgram) {
        vertexArray.setVertexAttribPointer(0,
                cubeShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_BYTE, indexArray);
    }
}
