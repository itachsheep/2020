package com.xingen.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xingen.commonlibrary.HelloService;
import com.xingen.commonlibrary.LogUtils;
import com.xingen.commonlibrary.router.RouterPath;

public class MainTestActivity extends Activity {
    private static final String TAG = "MainTestActivity";
    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }

    public void go_to_movie_test(View view) {
        ARouter.getInstance().build(RouterPath.PATH_MOVIE_TEST_ACTIVITY)
                .withString("name","taowei")
                .withInt("age",28)
                .navigation(this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        LogUtils.d(TAG,"onArrival");
                    }

                    @Override
                    public void onFound(Postcard postcard) {
                        super.onFound(postcard);
                        LogUtils.d(TAG,"onFound");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        super.onLost(postcard);
                        LogUtils.d(TAG,"onLost");
                    }


                    @Override
                    public void onInterrupt(Postcard postcard) {
                        super.onInterrupt(postcard);
                        LogUtils.d(TAG,"onInterrupt");
                    }
                });
    }

    public void call_hello(View view) {
        HelloService helloService = (HelloService) ARouter.getInstance()
                .build(RouterPath.SERVICE_HELLO).navigation();
        helloService.sayHello("taowei ");
    }
}
