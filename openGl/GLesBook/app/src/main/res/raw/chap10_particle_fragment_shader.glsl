precision mediump float; 
//uniform sampler2D u_TextureUnit;
varying vec3 v_Color;
varying float v_ElapsedTime;     	 							    	   								
void main()                    		
{
    //颜色除以运行时间，会使得粒子随着时间，越来越暗淡
    //gl_FragColor = vec4(v_Color / v_ElapsedTime, 1.0);

    //对于每个片段，使用勾股定理计算与圆心的距离，距离大于0.5半径的，
    //就不是圆的一部分，discard丢弃
    float xDistance = 0.5 - gl_PointCoord.x;
    float yDistance = 0.5 - gl_PointCoord.y;
    float distanceFromCenter = 
        sqrt(xDistance * xDistance + yDistance * yDistance);
    gl_FragColor = vec4(v_Color / v_ElapsedTime, 1.0);
    
    if (distanceFromCenter > 0.5) {
        discard;
    } else {            
        gl_FragColor = vec4(v_Color / v_ElapsedTime, 1.0);        
    }


    /*
    gl_FragColor = vec4(v_Color / v_ElapsedTime, 1.0)
                 * texture2D(u_TextureUnit, gl_PointCoord);
    */
}
