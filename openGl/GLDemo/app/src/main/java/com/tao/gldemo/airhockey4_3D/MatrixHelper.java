package com.tao.gldemo.airhockey4_3D;

public class MatrixHelper {

    /**
     * 在Android 3.x 以上的版本，不需要这个方法，
     * 直接使用 Matrix.perspectiveM 就可以
     * @param m
     * @param yFovInDegrees
     * @param aspect
     * @param n 到近处平面的距离，必须正值，如果值为 1，那么近处平面的z值为-1
     * @param f 到远处平面的距离，必须正值，且大于 到近处的距离
     */
    public static void perspectiveM(float[] m, float yFovInDegrees, float aspect,
                                    float n, float f) {
        final float angleInRadians = (float) (yFovInDegrees * Math.PI / 180.0);

        final float a = (float) (1.0 / Math.tan(angleInRadians / 2.0));
        m[0] = a / aspect;
        m[1] = 0f;
        m[2] = 0f;
        m[3] = 0f;

        m[4] = 0f;
        m[5] = a;
        m[6] = 0f;
        m[7] = 0f;

        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((f + n) / (f - n));
        m[11] = -1f;

        m[12] = 0f;
        m[13] = 0f;
        m[14] = -((2f * f * n) / (f - n));
        m[15] = 0f;
    }
}
