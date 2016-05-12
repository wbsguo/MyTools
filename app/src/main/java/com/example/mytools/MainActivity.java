package com.example.mytools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mytools.activity.SaveStateActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.button1)
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        Intent intent=null;
        switch (view.getId()) {
            case R.id.button1:
                intent=new Intent(MainActivity.this,SaveStateActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                break;
        }
    }
}
