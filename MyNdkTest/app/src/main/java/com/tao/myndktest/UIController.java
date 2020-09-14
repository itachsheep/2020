package com.tao.myndktest;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UIController {
    private static final String TAG = "UIController";
    private static UIController uiController = new UIController();
    private static int capacity = 10;
    private static float LOAD_FACTOR = 0.9f;
    private static Set<String> mlist =  Collections.newSetFromMap(
            new ConcurrentHashMap<String,Boolean>(capacity,LOAD_FACTOR,1));
    private ArrayList<String> mArrayList = new ArrayList<>(10);

    private UIController() {

    }

    public static UIController getInstance() {
        return uiController;
    }

    public void init() {
        ////todo:
        //mlist.clear();
        LogUtils.d(TAG,"int 111---->");
    }
}
