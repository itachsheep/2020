package com.tao.gldemo.solar;

/**
 * Created by user on 9/1/2015.
 */


import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SolarSystemRenderer implements GLSurfaceView.Renderer {

    public SolarSystemRenderer()
    {
        //mPlanet=new Planet(100,100,1.0f,1.0f);

        m_EyePosition[X_VALUE] = 0.0f;
        m_EyePosition[Y_VALUE] = 0.0f;
        m_EyePosition[Z_VALUE] = 5.0f;
        m_Earth = new Planet(50,50,0.3f,1.0f);
        m_Earth.setPosition(0.0f,0.0f,-2.0f);

        m_Sun = new Planet(50,50,1.0f,1.0f);
        m_Sun.setPosition(0.0f,0.0f,0.0f);
    }

    static float angle = 0.0f;

    public void onDrawFrame(GL10 gl)
    {

        /*
        Log.e("namit","onDraw ");
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, (float) Math.sin(mTransY), -4.0f);

      //  gl.glRotatef(mAngle,1,0,0);

       gl.glRotatef(mAngle,0,1,0);

        mPlanet.draw(gl);

        mTransY+=0.02f;
        mAngle+=0.4;

        */

        float paleYellow [] = {1.0f,1.0f,0.3f,1.0f};
        float white[] = {1.0f,1.0f,1.0f,1.0f};
        float cyan [] = {0.0f,1.0f,1.0f,1.0f};
        float black[] = {0.0f,0.0f,0.0f,0.0f};

        float orbitalIncrement = 1.25f;
        float[] sunPos = {0.0f,0.0f,0.0f,1.0f};

        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glPushMatrix();

        gl.glTranslatef(-m_EyePosition[X_VALUE], -m_EyePosition[Y_VALUE], -m_EyePosition[Z_VALUE]);


        gl.glLightfv(SS_SUNLIGHT, GL10.GL_POSITION, makeFloaBuffer(sunPos));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, makeFloaBuffer(cyan));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, makeFloaBuffer(white));

        gl.glPushMatrix();
        angle+=orbitalIncrement;
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
        executePlanet(m_Earth, gl);
        gl.glPopMatrix();

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, makeFloaBuffer(paleYellow));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR,makeFloaBuffer(black));

        executePlanet(m_Sun, gl);

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_EMISSION,makeFloaBuffer(black));

        gl.glPopMatrix();

    }

    private  void executePlanet(Planet _planet,GL10 gl)
    {
        float posX,posY,posZ;
        posX = _planet.m_Pos[0];
        posY = _planet.m_Pos[1];
        posZ = _planet.m_Pos[2];

        gl.glPushMatrix();
        gl.glTranslatef(posX, posX, posZ);

        _planet.draw(gl);
        gl.glPopMatrix();

    }
    public void onSurfaceChanged(GL10 gl,int width,int height)
    {
        Log.e("namit","onSurfaceChnaged width ="+width+"  height= "+height);
        gl.glViewport(0, 0, width, height);
        float ratio = (float) width/height;
        float aspectRatio ;
        float zNear=0.2f;
        float zfar=1000;
        float fieldOfView = 30.0f/57.3f;
        float size;
        gl.glEnable(GL10.GL_NORMALIZE);
        aspectRatio = (float) width /(float)height;

        gl.glMatrixMode(GL10.GL_PROJECTION);
        size= zNear * (float)(Math.tan((double)(fieldOfView/2.0f)));

        gl.glFrustumf(-size,size,-size/aspectRatio,size/aspectRatio,zNear,zfar);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    public void onSurfaceCreated(GL10 gl,EGLConfig config)
    {
        Log.e("namit", "onSurfaceCreated ");
       /*
        gl.glCullFace(GL10.GL_FRONT);
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
        if(mTranslucentBackgroung)
        {
            gl.glClearColor(0, 0, 0, 0);
        }
        else
        {
            gl.glClearColor(1,1,1,1);
        }
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        */

        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //gl.glDepthMask(false);

        gl.glDepthMask(true);


      //  initGeometry(gl);
        //initLightings(gl);
        //initLightings2(gl);
        ///initLightings3(gl);

        initLightings4(gl);


    }


    private void initLightings4(GL10 gl)
    {
        float [] sunPos = {0.0f,0.0f,0.0f,1.0f};
        float [] posFill1= {-15.0f,15.0f,0.0f,1.0f};
        float [] posFill2 = {-10.0f,-4.0f,1.0f,1.0f};
        float [] white = {1.0f,1.0f,1.0f,1.0f};
        float [] dimblue = {0.0f,0.0f,0.2f,1.0f};
        float [] cyan = {0.0f,1.0f,1.0f,1.0f};
        float [] yellow = {1.0f,1.0f,0.0f,1.0f};
        float [] magenta = {1.0f,0.0f,1.0f,1.0f};
        float [] dimmagenta = {0.75f,0.0f,0.25f,1.0f};
        float [] dimcyan = {0.0f,0.5f,0.5f,1.0f};

        // lights go here

        gl.glLightfv(SS_SUNLIGHT,GL10.GL_POSITION,makeFloaBuffer(sunPos));
        gl.glLightfv(SS_SUNLIGHT,GL10.GL_DIFFUSE,makeFloaBuffer(white));
        gl.glLightfv(SS_SUNLIGHT,GL10.GL_SPECULAR,makeFloaBuffer(yellow));


        gl.glLightfv(SS_FILLLIGHT1,GL10.GL_POSITION,makeFloaBuffer(posFill1));
        gl.glLightfv(SS_FILLLIGHT1,GL10.GL_DIFFUSE,makeFloaBuffer(dimblue));
        gl.glLightfv(SS_FILLLIGHT1,GL10.GL_SPECULAR,makeFloaBuffer(dimcyan));



        gl.glLightfv(SS_FILLLIGHT2,GL10.GL_POSITION,makeFloaBuffer(posFill2));
        gl.glLightfv(SS_FILLLIGHT2,GL10.GL_DIFFUSE,makeFloaBuffer(dimmagenta));
        gl.glLightfv(SS_FILLLIGHT2,GL10.GL_SPECULAR,makeFloaBuffer(dimblue));

        //materials go here

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE,makeFloaBuffer(cyan));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR,makeFloaBuffer(white));

        gl.glLightf(SS_SUNLIGHT, GL10.GL_QUADRATIC_ATTENUATION, 0.001f);
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 25);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, 0.0f);

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(SS_SUNLIGHT);
        gl.glEnable(SS_FILLLIGHT1);
        gl.glEnable(SS_FILLLIGHT2);


    }

    private void initLightings3(GL10 gl)
    {
        float [] posMain = {5.0f,4.0f,6.0f,1.0f};
        float [] posFill1 = {-15.0f,15.0f,0.0f,1.0f};
        float [] posFill2 = {-10.0f,-4.0f,1.0f,1.0f};

        float [] white = {1.0f,1.0f,1.0f,1.0f};
        float [] red = {1.0f,0.0f,0.0f,1.0f};
        float [] dimred = {0.5f,0.0f,0.0f,1.0f};

        float [] green = {0.0f,1.0f,0.0f,0.0f};
        float [] dimgreen = {0.0f,0.5f,0.0f,0.0f};

        float [] blue = {0.0f,0.0f,1.0f,1.0f};
        float [] dimblue = {0.0f,0.0f,0.2f,1.0f};

        float [] cyan = {0.0f,1.0f,1.0f,1.0f};
        float [] yellow = {1.0f,1.0f,0.0f,1.0f};
        float [] magenta = {1.0f,0.0f,1.0f,1.0f};
        float [] dimmagenta = {0.75f,0.0f,0.25f,1.0f};

        float [] dimcyan = {0.0f,0.5f,0.5f,1.0f};

        // loght go here.

        gl.glLightfv(SS_SUNLIGHT,GL10.GL_POSITION,makeFloaBuffer(posMain));
        gl.glLightfv(SS_SUNLIGHT,GL10.GL_DIFFUSE,makeFloaBuffer(white));
        gl.glLightfv(SS_SUNLIGHT,GL10.GL_SPECULAR,makeFloaBuffer(yellow));



        gl.glLightfv(SS_FILLLIGHT1,GL10.GL_POSITION,makeFloaBuffer(posFill1));
        gl.glLightfv(SS_FILLLIGHT1,GL10.GL_DIFFUSE,makeFloaBuffer(dimblue));
        gl.glLightfv(SS_FILLLIGHT1,GL10.GL_SPECULAR,makeFloaBuffer(dimcyan));




        gl.glLightfv(SS_FILLLIGHT2,GL10.GL_POSITION,makeFloaBuffer(posFill2));
        gl.glLightfv(SS_FILLLIGHT2,GL10.GL_DIFFUSE,makeFloaBuffer(dimmagenta));
        gl.glLightfv(SS_FILLLIGHT2,GL10.GL_SPECULAR,makeFloaBuffer(dimblue));

        gl.glLightf(SS_SUNLIGHT, GL10.GL_QUADRATIC_ATTENUATION, 0.005f);

        //materials go here

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE,makeFloaBuffer(cyan));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR,makeFloaBuffer(white));
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 25);

        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, 1.0f);

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(SS_SUNLIGHT);
        gl.glEnable(SS_FILLLIGHT1);
        gl.glEnable(SS_FILLLIGHT2);

        gl.glLoadIdentity();



    }
    private  void initLightings2(GL10 gl)
    {
        float [] diffuse = {1.0f,1.0f,1.0f,1.0f};
        float [] pos = { 50.0f,0.0f,3.0f,1.0f };
        float [] white = { 1.0f,1.0f,1.0f,1.0f };
        float [] red = { 1.0f,0.0f,0.0f,1.0f };
        float [] green = { 0.0f,1.0f,0.0f,1.0f };
        float [] blue = { 0.0f,0.0f,1.0f,1.0f };
        float [] cyan = { 0.0f,1.0f,1.0f,1.0f };
        float [] yellow = { 0.5f,0.5f,0.0f,1.0f };
        float [] magenta = { 1.0f,0.0f,1.0f,1.0f };
        float [] halfcyan = { 0.0f,0.5f,0.5f,1.0f };

        gl.glLightfv(SS_SUNLIGHT,GL10.GL_POSITION,makeFloaBuffer(pos));
        gl.glLightfv(SS_SUNLIGHT,GL10.GL_DIFFUSE,makeFloaBuffer(diffuse));
        gl.glLightfv(SS_SUNLIGHT,GL10.GL_SPECULAR,makeFloaBuffer(red));
        gl.glLightfv(SS_SUNLIGHT,GL10.GL_AMBIENT,makeFloaBuffer(blue));
        gl.glLightf(SS_SUNLIGHT,GL10.GL_LINEAR_ATTENUATION,0.025f);



        gl.glMaterialf(GL10.GL_FRONT_AND_BACK,GL10.GL_SHININESS,25);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, makeFloaBuffer(green));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, makeFloaBuffer(red));
        //gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT,makeFloaBuffer(blue));

     //   gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_EMISSION,makeFloaBuffer(yellow));

        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(SS_SUNLIGHT);
        gl.glLoadIdentity();
    }

    private  void initLightings(GL10 gl)
    {
        float [] diffuse= {0.0f,1.0f,0.0f,1.0f};
        float [] pos = {0.0f,5.0f,0.0f,1.0f};
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_POSITION, makeFloaBuffer(pos));
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_DIFFUSE, makeFloaBuffer(diffuse));
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(SS_SUNLIGHT);

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
    public final static int SS_SUNLIGHT =  GL10.GL_LIGHT0;
    // 5-September-2015
    public final static int SS_FILLLIGHT1 =  GL10.GL_LIGHT1;
    public final static int SS_FILLLIGHT2 =  GL10.GL_LIGHT2;
    private boolean mTranslucentBackgroung;
    private Planet mPlanet;
    private float mTransY;
    private float mAngle;

    public final static int X_VALUE =0;
    public final  static  int Y_VALUE = 1;
    public final  static  int Z_VALUE = 2;

    Planet m_Earth;
    Planet m_Sun;

    float [] m_EyePosition = {0.0f,0.0f,0.0f};
}
