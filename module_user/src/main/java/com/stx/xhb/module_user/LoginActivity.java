package com.stx.xhb.module_user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.stx.xhb.module_basecore.RouterManger;

@Route(path = RouterManger.Path.USER_LOGIN_ACTIVITY)
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
    }
}
