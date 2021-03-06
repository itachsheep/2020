/**
 * @ClassName: Mollet 用颜色着色器绘制木槌
 * @Description: (用一句话描述该文件做什么)
 * @author taowei
 * @version V1.0
 * @Date
 */

package com.tao.gldemo.chapter8;

import com.tao.gldemo.chapter8.Geometry;
import com.tao.gldemo.chapter8.ObjectBuilder.DrawCommand;
import com.tao.gldemo.chapter8.ObjectBuilder.GeneratedData;
import android.opengl.GLES20;

import java.util.List;

import static com.tao.gldemo.chapter7_airhockey5_textured.VertexArray.BYTES_PER_FLOAT;

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius;
    public final float height;

    private final VertexArray vertexArray;
    private final List<DrawCommand> drawList;

    public Mallet(float radius, float height, int numPointsAroundMallet) {
        GeneratedData generatedData = ObjectBuilder.createMallet(new Geometry.Point(0f,
                0f, 0f), radius, height, numPointsAroundMallet);

        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }
    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }
    public void draw() {
        for (DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}

