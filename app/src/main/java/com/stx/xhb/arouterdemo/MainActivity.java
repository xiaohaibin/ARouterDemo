package com.stx.xhb.arouterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                ARouter.getInstance()
                        .build("/test/activity2")
                        .withString("key1", "小猪佩奇身上纹，掌声送给社会人")
                        .navigation(this);
                break;
            case R.id.btn_2:
                ARouter.getInstance().build("/module_user/login").navigation();
                break;
            default:
                break;
        }
    }
}
