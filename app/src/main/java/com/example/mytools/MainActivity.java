package com.example.mytools;

import android.app.Activity;
import android.os.Bundle;

import com.example.mytoolslibrary.MyToolsTest;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MyToolsTest().myTools();
    }
}
