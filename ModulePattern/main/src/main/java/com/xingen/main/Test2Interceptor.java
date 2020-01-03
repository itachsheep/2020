package com.xingen.main;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.xingen.commonlibrary.LogUtils;
import com.xingen.commonlibrary.router.RouterPath;

@Interceptor(priority = 2)
public class Test2Interceptor implements IInterceptor {
    private static final String TAG = "Test2Interceptor";
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        LogUtils.d(TAG,"process");
        if (postcard.getPath().equals(RouterPath.PATH_MOVIE_TEST_ACTIVITY)) {
            LogUtils.d(TAG, " 进行了拦截处理！");
        }
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        LogUtils.d(TAG,"init");
    }
}
