package com.example.mytoolslibrary.utils;

import android.content.Context;
import android.util.Log;

/**
 * 捕获崩溃
 * Created by Administrator on 2016/3/4.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler crashHandler;
    //程序的Context对象
    private Context mContext;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("", "异常喽");
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            Log.e("", "系统的处理");
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            //退出程序
            Log.e("", "用户自己的处理");
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        return true;
    }
}
