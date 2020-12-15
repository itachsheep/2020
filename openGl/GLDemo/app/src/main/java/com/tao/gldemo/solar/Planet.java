package com.tao.gldemo.solar;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by user on 8/30/2015.
 */
public class Planet {
    FloatBuffer m_VertexData;
    FloatBuffer m_NormalData;
    FloatBuffer m_ColorData;

    float m_Scale;
    float m_Squash;
    float m_Radius;
    int m_Stacks,m_Slices;

    public float [] m_Pos = {0.0f,0.0f,0.0f};

    public Planet(int stacks,int slices,float radius,float squash)
    {
        this.m_Stacks=stacks;
        this.m_Slices=slices;
        this.m_Radius=radius;
        this.m_Squash=squash;

        init(m_Stacks,m_Slices,radius,squash,"dummy");
    }

    public void setPosition(float a,float b,float c)
    {
        m_Pos[0]=a;
        m_Pos[1]=b;
        m_Pos[2]=c;

    }

    private void init(int stacks,int slices,float radius,float sqaush,String textureFile)
    {
        float[] VertexData;
        float[] colorData;
        float[] normalData;
        float colorIncrement=0f;

        float blue=0f;
        float red=1.0f;
        int numVertices=0;
        int vIndex=0;
        int cIndex=0;
        int nIndex=0;

        m_Scale=radius;
        m_Squash=sqaush;
        colorIncrement=1.0f/(float)stacks;
        {
            m_Stacks=stacks;
            m_Slices=slices;

            VertexData=new float[3*((m_Slices*2+2)*m_Stacks)];

            colorData= new float[(4*(m_Slices*2+2)*m_Slices)];

            normalData=new float[3*((m_Slices*2+2)*m_Stacks)];

            int phiIdx,thetaIdx;
            //latitude
            for(phiIdx=0;phiIdx<m_Stacks;phiIdx++)
            {
                //
                float phi0= (float)Math.PI*((float)(phiIdx+0)*(1.0f/(float)(m_Stacks))-0.5f);

                float phi1 = (float)Math.PI * ((float)(phiIdx+1)*(1.0f/(float)(m_Stacks))-0.5f);

                float cosPhi0 = (float)Math.cos(phi0);
                float sinPhi0 = (float)Math.sin(phi0);
                float cosPhi1=(float)Math.cos(phi1);
                float sinPhi1=(float)Math.sin(phi1);

                float cosTheta,sinTheta;

                //longitude

                for(thetaIdx=0;thetaIdx< m_Slices;thetaIdx++)
                {
                    //increment aong

                    float theta =(float)(-2.0f*(float)Math.PI*((float)thetaIdx)*(1.0/(float)(m_Slices-1)));
                    cosTheta=(float)Math.cos(theta);
                    sinTheta=(float)Math.sin(theta);

                    VertexData[vIndex+0]=m_Scale*cosPhi0*cosTheta;
                    VertexData[vIndex+1]=m_Scale*(sinPhi0*m_Squash);
                    VertexData[vIndex+2]=m_Scale*(cosPhi0*sinTheta);

                    VertexData[vIndex+3]=m_Scale*cosPhi1*cosTheta;
                    VertexData[vIndex+4]=m_Scale*(sinPhi1*m_Squash);

                    VertexData[vIndex+5]=m_Scale*(cosPhi1*sinTheta);

                    colorData[cIndex+0]=(float)red;
                    colorData[cIndex+1]=(float)0f;
                    colorData[cIndex+2]=(float)blue;
                    colorData[cIndex+3]=(float)red;
                    colorData[cIndex+4]=(float)0f;
                    colorData[cIndex+5]=(float)blue;
                    colorData[cIndex+6]=(float)1.0;
                    colorData[cIndex+7]=(float)1.0f;

                    normalData[nIndex + 0] = cosPhi0*cosTheta;
                    normalData[nIndex + 1] = sinPhi0;
                    normalData[nIndex + 2] = cosPhi0*sinTheta;
                    normalData[nIndex + 3] = cosPhi1*cosTheta;
                    normalData[nIndex + 4] = sinPhi1;
                    normalData[nIndex + 5] = cosPhi1*sinTheta;

                    cIndex+=2*4;
                    vIndex+=2*3;
                    nIndex+=2*3;
                }
              //  blue+=colorIncrement;
                red-=colorIncrement;

                //create a degene

                VertexData[vIndex+0]=VertexData[vIndex+3]=VertexData[vIndex-3];
                VertexData[vIndex+1]=VertexData[vIndex+4]=VertexData[vIndex-2];
                VertexData[vIndex+2]=VertexData[vIndex+5]=VertexData[vIndex-1];
            }

        }
        m_VertexData=makeFloaBuffer(VertexData);
        m_ColorData=makeFloaBuffer(colorData);
        m_NormalData=makeFloaBuffer(normalData);
    }

    protected static FloatBuffer makeFloaBuffer(float[] arr)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }

    public void draw(GL10 gl)
    {
        /*
        gl.glFrontFace(GL10.GL_CW);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_VertexData);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, m_ColorData);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

       gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,(m_Slices+1)*2*(m_Stacks-1)+2);
       // gl.glDrawArrays(GL10.GL_LINE_STRIP,0,(m_Slices+1)*2*(m_Stacks-1)+2);
      //  gl.glDrawArrays(GL10.GL_POINTS,0,(m_Slices+1)*2*(m_Stacks-1)+2);

      */
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, m_NormalData);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_VertexData);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glColorPointer(4, GL10.GL_FLOAT, 0, m_ColorData);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,(m_Slices+1)*2*(m_Stacks-1)+2);
    }
}
