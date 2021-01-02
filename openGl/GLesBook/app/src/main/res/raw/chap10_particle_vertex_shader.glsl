uniform mat4 u_Matrix;
uniform float u_Time;

attribute vec3 a_Position;  
attribute vec3 a_Color;
attribute vec3 a_DirectionVector;
attribute float a_ParticleStartTime;

//传递给片段着色器
varying vec3 v_Color;
//运行时间
varying float v_ElapsedTime;

void main()                    
{                                	  	  
    v_Color = a_Color;
    //u_Time 喷泉从一开始喷到当前的总时间，一直在增长
    //a_ParticleStartTime 粒子生成时间，每个粒子是固定的。
    //粒子生成时间越早，运行时间越久  （一开始很难理解的算法，以为这两个值一直相同）
    v_ElapsedTime = u_Time - a_ParticleStartTime;    
    float gravityFactor = v_ElapsedTime * v_ElapsedTime / 8.0;
    vec3 currentPosition = a_Position + (a_DirectionVector * v_ElapsedTime);
    currentPosition.y -= gravityFactor;
    gl_Position = u_Matrix * vec4(currentPosition, 1.0);
    //gl_PointSize = 10.0;

    gl_PointSize = 25.0;
}   
