precision mediump float;

//接受实际的纹理数据，sampler2D 是二维纹理数组
//opengl使用纹理绘制，不需要直接给着色器传递纹理，只使用纹理单元(texture unit)保存纹理，
//之所以这样，一个gpu只能同时绘制数量有限的纹理，使用纹理单元表示当前正在绘制的纹理
//如果需要切换纹理，可以在纹理单元间切换，切换太频繁，会导致渲染速度下降
uniform sampler2D u_TextureUnit;

//接受纹理坐标
varying vec2 v_TextureCoordinates;

void main()
{
    //纹理坐标和纹理数据被传递给着色器函数 texture2D，读入纹理中特定坐标的颜色，
    //赋值给gl_FragColor，设置片段颜色
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
}