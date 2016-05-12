package com.example.mytools;

import android.app.Application;

import com.example.mytoolslibrary.utils.CrashHandler;


/**
 * Created by wangbs on 16/3/31.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initCrash();
    }
    //捕获崩溃
    private void initCrash(){
        CrashHandler.getInstance().init(getApplicationContext());
    }
}
