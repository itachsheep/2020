//uniform mat4 u_Matrix;
//模型视图矩阵
uniform mat4 u_MVMatrix;
//倒置矩阵转置
uniform mat4 u_IT_MVMatrix;
//合并后的模型视图投影矩阵
uniform mat4 u_MVPMatrix;

//存储方向光源的归一化向量
uniform vec3 u_VectorToLight;// In eye space
// 点光源位置，定义为数组，传递多个向量
uniform vec4 u_PointLightPositions[3];// In eye space
// 点光源颜色，定义为数组，传递多个向量
uniform vec3 u_PointLightColors[3];

//用vec4表示其位置，减少vec3 和 vec4 之间的转化，不需要改变顶点数据，
//opengl默认1为第四个分量，但是小心uniform，必须指定所有分量
attribute vec4 a_Position;
//高度图法线 属性
attribute vec3 a_Normal;

varying vec3 v_Color;
vec3 materialColor;
vec4 eyeSpacePosition;
vec3 eyeSpaceNormal;

vec3 getAmbientLighting();
vec3 getDirectionalLighting();
vec3 getPointLighting();
void main()                    
{
    /*
    //主要作用：调整表面法线，高等数学原理
    vec3 scaledNormal = a_Normal;
    scaledNormal.y *= 10.0;
    scaledNormal = normalize(scaledNormal);

    //计算反射，计算指向光源的向量和表面法线的点积，点积就是他们的夹角
    float diffuse = max(dot(scaledNormal, u_VectorToLight), 0.0);
    //调低光的强度
    diffuse *= 0.3;
    //漫反射与v_Color相乘
    v_Color *= diffuse;


    //伪造漫反射，避免山是全黑的，实际在太阳照射下，不可能全黑
    float ambient = 0.1;
    v_Color += ambient;
    */

    //mix 两个不同颜色做平滑插值
    /*
    v_Color = mix(vec3(0.180, 0.467, 0.153),    // A dark green
                  vec3(0.660, 0.670, 0.680),    // A stony gray
                  a_Position.y);
    */

    materialColor = mix(vec3(0.180, 0.467, 0.153),    // A dark green
                            vec3(0.660, 0.670, 0.680),    // A stony gray
                            a_Position.y);
    //在眼空间中计算当前位置和法向量
    eyeSpacePosition = u_MVMatrix * a_Position;
    eyeSpaceNormal = normalize(vec3(u_IT_MVMatrix * vec4(a_Normal, 0.0)));

    //计算每种光的类型，把结果颜色累加
    v_Color = getAmbientLighting();//环境光
    v_Color += getDirectionalLighting();//方向光
    v_Color += getPointLighting();//点光

    gl_Position = u_MVPMatrix * a_Position;
}

//环境光
vec3 getAmbientLighting()
{
    return materialColor * 0.1;
}

//方向光
vec3 getDirectionalLighting()
{
    return materialColor * 0.3
         * max(dot(eyeSpaceNormal, u_VectorToLight), 0.0);
}

//点光
vec3 getPointLighting()
{
    vec3 lightingSum = vec3(0.0);

    for (int i = 0; i < 3; i++) {
        //当前位置到点光源的向量
        vec3 toPointLight = vec3(u_PointLightPositions[i])
                          - vec3(eyeSpacePosition);
        //当前位置到点光源的距离
        float distance = length(toPointLight);
        //归一化向量
        toPointLight = normalize(toPointLight);
        //计算漫反射
        float cosine = max(dot(eyeSpaceNormal, toPointLight), 0.0);
        //累加前除以距离，结果累加到 lightingSum
        lightingSum += (materialColor * u_PointLightColors[i] * 5.0 * cosine)
                       / distance;
    }

    return lightingSum;
}
