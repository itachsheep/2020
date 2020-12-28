/**
 * @ClassName: ObjectBuilder
 * @Description:
 * @author taowei
 * @version V1.0
 * @Date
 */

package com.tao.gldemo.chapter8;

import android.opengl.GLES20;
import android.util.FloatMath;

import java.util.ArrayList;
import java.util.List;

import com.tao.gldemo.chapter8.Geometry.*;

public class ObjectBuilder {
    private static final int FLOATS_PER_VERTEX = 3;

    static interface DrawCommand {
        void draw();
    }

    static class GeneratedData {
        final float[] vertexData;
        final List<DrawCommand> drawList;

        GeneratedData(float[] vertexData, List<DrawCommand> drawList) {
            this.vertexData = vertexData;
            this.drawList = drawList;
        }
    }

    //创建冰球
    static GeneratedData createPuck(Geometry.Cylinder puck, int numPoints) {
        int size = sizeOfCircleInVertices(numPoints)
                + sizeOfOpenCylinderInVertices(numPoints);

        ObjectBuilder builder = new ObjectBuilder(size);

        //向上移动的顶部
        Geometry.Circle puckTop = new Geometry.Circle(
                puck.center.translateY(puck.height / 2f),
                puck.radius);

        //冰球的顶部
        builder.appendCircle(puckTop, numPoints);
        //冰球的侧面
        builder.appendOpenCylinder(puck, numPoints);

        return builder.build();
    }

    static GeneratedData createMallet(
            Point center, float radius, float height, int numPoints) {
        int size = sizeOfCircleInVertices(numPoints) * 2
                + sizeOfOpenCylinderInVertices(numPoints) * 2;

        ObjectBuilder builder = new ObjectBuilder(size);

        // First, generate the mallet base.
        float baseHeight = height * 0.25f;

        Circle baseCircle = new Circle(
                center.translateY(-baseHeight),
                radius);
        Cylinder baseCylinder = new Cylinder(
                baseCircle.center.translateY(-baseHeight / 2f),
                radius, baseHeight);

        builder.appendCircle(baseCircle, numPoints);
        builder.appendOpenCylinder(baseCylinder, numPoints);

        // Now generate the mallet handle.
        float handleHeight = height * 0.75f;
        float handleRadius = radius / 3f;

        Circle handleCircle = new Circle(
                center.translateY(height * 0.5f),
                handleRadius);
        Cylinder handleCylinder = new Cylinder(
                handleCircle.center.translateY(-handleHeight / 2f),
                handleRadius, handleHeight);

        builder.appendCircle(handleCircle, numPoints);
        builder.appendOpenCylinder(handleCylinder, numPoints);

        return builder.build();
    }

    /**
     * 计算圆柱体顶部，顶点数量
     * 圆柱体顶部是由：三角形扇构造的圆，有一个顶点在圆心，围着圆的每个点都是顶点
     * 并且围着圆的第一个顶点需要重复两次才能闭合圆。
     */
    private static int sizeOfCircleInVertices(int numPoints) {
        return 1 + (numPoints + 1);
    }

    /**
     * 计算圆柱体侧面顶点的数量：
     * 圆柱体侧面是卷起来的长方形，由一个三角形带构造，围着顶部圆的每个点
     * 都需要两个顶点，并且前两个顶点要重复两次才能是这个管闭合
     */
    private static int sizeOfOpenCylinderInVertices(int numPoints) {
        return (numPoints + 1) * 2;
    }

    /**
     * 数组，保存顶点
     */
    private final float[] vertexData;

    private final List<DrawCommand> drawList = new ArrayList<DrawCommand>();
    private int offset = 0;

    private ObjectBuilder(int sizeInVertices) {
        vertexData = new float[sizeInVertices * FLOATS_PER_VERTEX];
    }

    /**
     * 用三角形扇构造冰球顶部：圆
     * @param circle
     * @param numPoints
     */
    private void appendCircle(Geometry.Circle circle, int numPoints) {
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfCircleInVertices(numPoints);

        // Center point of fan
        vertexData[offset++] = circle.center.x;
        vertexData[offset++] = circle.center.y;
        vertexData[offset++] = circle.center.z;

        // Fan around center point. <= is used because we want to generate
        // the point at the starting angle twice to complete the fan.
        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians =
                    ((float) i / (float) numPoints)
                            * ((float) Math.PI * 2f);

            vertexData[offset++] = (float) (circle.center.x +
                    circle.radius * Math.cos(angleInRadians));
            vertexData[offset++] = circle.center.y;
            vertexData[offset++] = (float) (circle.center.z +
                    circle.radius * Math.sin(angleInRadians));
        }
        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, startVertex, numVertices);
            }
        });
    }

    private void appendOpenCylinder(Cylinder cylinder, int numPoints) {
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfOpenCylinderInVertices(numPoints);
        final float yStart = cylinder.center.y - (cylinder.height / 2f);
        final float yEnd = cylinder.center.y + (cylinder.height / 2f);

        // Generate strip around center point. <= is used because we want to
        // generate the points at the starting angle twice, to complete the
        // strip.
        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians =
                    ((float) i / (float) numPoints)
                            * ((float) Math.PI * 2f);

            float xPosition = (float) (cylinder.center.x +
                    cylinder.radius * Math.cos(angleInRadians));

            float zPosition = (float) (cylinder.center.z +
                    cylinder.radius * Math.sin(angleInRadians));

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPosition;

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yEnd;
            vertexData[offset++] = zPosition;
        }
        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, startVertex, numVertices);
            }
        });
    }

    private GeneratedData build() {
        return new GeneratedData(vertexData, drawList);
    }
}
