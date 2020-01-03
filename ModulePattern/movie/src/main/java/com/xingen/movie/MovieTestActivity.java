package com.xingen.movie;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xingen.commonlibrary.router.RouterPath;

@Route(path = RouterPath.PATH_MOVIE_TEST_ACTIVITY)
public class MovieTestActivity extends Activity {
    @Autowired
    String name;

    @Autowired
    int age;


    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_test);
        ARouter.getInstance().inject(this);

        Toast.makeText(this,"name: "+ name + ", age: " + age,Toast.LENGTH_LONG).show();
    }
}
