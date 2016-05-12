package com.example.mytools.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.mytools.BaseActivity;
import com.example.mytools.R;

import butterknife.ButterKnife;

/**
 * 保存状态数据
 * Created by wangbs on 16/4/8.
 */
public class SaveStateActivity extends BaseActivity {
    private String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_state);
        ButterKnife.bind(this);
        // 从savedInstanceState中恢复数据, 如果没有数据需要恢复savedInstanceState为null
        if (savedInstanceState != null) {
            temp = savedInstanceState.getString("temp");
            Log.e("TestActivity", "TestActivity:" + "onCreate: temp = " + temp);
        }
    }
    public void onResume() {
        super.onResume();
        temp = "xing";
        Log.e("TestActivity", "TestActivity:" + "onResume: temp = " + temp);
        // 切换屏幕方向会导致activity的摧毁和重建
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Log.e("TestActivity", "屏幕切换");
        }
    }

    // 将数据保存到outState对象中, 该对象会在重建activity时传递给onCreate方法
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("temp", temp);
    }
}
