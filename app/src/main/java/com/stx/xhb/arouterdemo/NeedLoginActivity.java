package com.stx.xhb.arouterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.stx.xhb.module_basecore.Constants;
import com.stx.xhb.module_basecore.RouterManger;

/**
 * 跳转需要登录页面
 */
@Route(path = RouterManger.Path.MAIN_NEED_LOGIN_ACTIVITY,extras = Constants.NEED_LOGIN)
public class NeedLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_login);
    }
}
