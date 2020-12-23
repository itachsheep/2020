precision mediump float;

//接受实际的纹理数据，sampler2D 是二维纹理数组
uniform sampler2D u_TextureUnit;

//接受纹理坐标
varying vec2 v_TextureCoordinates;

void main()
{
    //纹理坐标和纹理数据被传递给着色器函数 texture2D，读入纹理中特定坐标的颜色，
    //赋值给gl_FragColor，设置片段颜色
    gl_FragColor = texture2D(u_TextureUnit,v_TextureCoordinates);
}