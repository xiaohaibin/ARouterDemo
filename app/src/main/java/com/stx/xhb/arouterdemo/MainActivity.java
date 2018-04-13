package com.stx.xhb.arouterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.stx.xhb.module_basecore.RouterManger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1=findViewById(R.id.btn_1);
        Button btn2=findViewById(R.id.btn_2);
        Button btn3=findViewById(R.id.btn_3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1://携带参数跳转操作
                ARouter.getInstance()
                        .build(RouterManger.Path.MAIN_TEST_ACTIVITY)
                        .withString("key", "小猪佩奇身上纹，掌声送给社会人")
                        .navigation(this);
                break;
            case R.id.btn_2://跨模块跳转
                ARouter.getInstance().build(RouterManger.Path.USER_LOGIN_ACTIVITY).navigation();
                break;
            case R.id.btn_3://
                ARouter.getInstance().build(RouterManger.Path.MAIN_NEED_LOGIN_ACTIVITY).navigation();
                break;
            default:
                break;
        }
    }
}
