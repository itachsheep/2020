
uniform mat4 u_Matrix;
attribute vec4 a_Position;

//接受纹理坐标，因为有s t两个分量，所以定义vec2
attribute vec2 a_TextureCoordinates

//把纹理坐标传递给顶点着色器被插值的varying
varying vec2 v_TextureCoordinates;

void main()
{
    v_TextureCoordinates = a_TextureCoordinates;

    gl_Position = u_Matrix * a_Position;
}