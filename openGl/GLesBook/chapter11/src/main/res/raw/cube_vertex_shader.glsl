uniform mat4 u_Matrix;
attribute vec3 a_Position;
varying vec3 v_Position;

void main()                    
{                                	  	          
    v_Position = a_Position;

    // Make sure to convert from the right-handed coordinate system of the
    // world to the left-handed coordinate system of the cube map, otherwise,
    // our cube map will still work but everything will be flipped.
    //把顶点位置传递给着色器，反转z向量
    //v_Position.z = -v_Position.z;
	           
    gl_Position = u_Matrix * vec4(a_Position, 1.0);
    //把z分量设置为与w分量相等，因为透视除法把一切除以w，z值最终为1的远平面上
    //gl_Position = gl_Position.xyww;
}    
