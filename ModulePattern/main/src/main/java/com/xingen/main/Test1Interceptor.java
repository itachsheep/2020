package com.xingen.main;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.xingen.commonlibrary.LogUtils;
import com.xingen.commonlibrary.router.RouterPath;

@Interceptor(priority = 1)
public class Test1Interceptor implements IInterceptor {
    private static final String TAG = "Test1Interceptor";
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        LogUtils.d(TAG,"process");
        if (postcard.getPath().equals(RouterPath.PATH_MOVIE_TEST_ACTIVITY)) {
            LogUtils.d(TAG, " 进行了拦截处理！");
            //callback.onInterrupt(new Exception("----test just-----"));
        }
        //callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        LogUtils.d(TAG,"init");
    }
}
