/**
 * @ClassName: Puck
 * @Description:
 * @author taowei
 * @version V1.0
 * @Date
 */

package com.tao.gldemo.chapter9;

import com.tao.gldemo.chapter9.ObjectBuilder.GeneratedData;

import java.util.List;

/**
 * 绘制冰球
 */
public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius, height;

    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsAroundPuck) {
        GeneratedData generatedData = ObjectBuilder.createPuck(new Geometry.Cylinder(
                new Geometry.Point(0f, 0f, 0f), radius, height), numPointsAroundPuck);
        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    /**
     * 顶点数据绑定到着色器
     * @param colorProgram
     */
    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }
    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
