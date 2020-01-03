package com.xingen.movie;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xingen.commonlibrary.HelloService;
import com.xingen.commonlibrary.router.RouterPath;

@Route(path = RouterPath.SERVICE_HELLO, name = "test11 service")
public class HelloServiceImpl implements HelloService {
    private Context mContext;
    @Override
    public void sayHello(String name) {
        Toast.makeText(mContext,this.getClass().getSimpleName()+": sayHello"+" "+name,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
