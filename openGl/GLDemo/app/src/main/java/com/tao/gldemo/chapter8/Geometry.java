package com.tao.gldemo.chapter8;

public class Geometry {
    public static class Point {
        public final float x, y, z;
        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        //把这个点沿y轴平移
        public Point translateY(float distance) {
            return new Point(x, y + distance, z);
        }
    }

    public static class Circle {
        public final Point center;
        public final float radius;

        public Circle(Point center, float radius) {
            this.center = center;
            this.radius = radius;
        }

        //缩放圆的半径
        public Circle scale(float scale) {
            return new Circle(center, radius * scale);
        }
    }

    /**
     * 圆柱体：看作扩展的圆，一个中心，一个半径，一个高度
     */
    public static class Cylinder {
        public final Point center;
        public final float radius;
        public final float height;

        public Cylinder(Point center, float radius, float height) {
            this.center = center;
            this.radius = radius;
            this.height = height;
        }
    }
}
