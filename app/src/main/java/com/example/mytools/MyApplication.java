package com.example.mytools;

import android.app.Application;

import com.example.mytoolslibrary.utils.CrashHandler;
import com.example.mytoolslibrary.utils.FontsOverride;


/**
 * Created by wangbs on 16/3/31.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
    }
    //捕获崩溃
    private void initCrash(){
        CrashHandler.getInstance().init(getApplicationContext());
    }
    //改变默认字体
    private void initFont(){
      FontsOverride.setDefaultFont(this,"MONOSPACE","fonts/xxx.ttf");
    }
}
