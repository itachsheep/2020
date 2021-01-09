uniform mat4 u_Matrix;
attribute vec3 a_Position;
varying vec3 v_Color;

//存储方向光源的归一化向量
uniform vec3 u_VectorToLight;
//高度图法线 属性
attribute vec3 a_Normal;

void main()                    
{
    //主要作用：调整表面法线，高等数学原理
    vec3 scaledNormal = a_Normal;
    scaledNormal.y *= 10.0;
    scaledNormal = normalize(scaledNormal);

    //计算反射，计算指向光源的向量和表面法线的点积，点积就是他们的夹角
    float diffuse = max(dot(scaledNormal, u_VectorToLight), 0.0);
    v_Color *= diffuse;

    //mix 两个不同颜色做平滑插值
    v_Color = mix(vec3(0.180, 0.467, 0.153),    // A dark green 
                  vec3(0.660, 0.670, 0.680),    // A stony gray 
                  a_Position.y);
		
    gl_Position = u_Matrix * vec4(a_Position, 1.0);    
}
