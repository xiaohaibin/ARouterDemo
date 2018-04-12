package com.stx.xhb.arouterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.stx.xhb.module_basecore.Constants;
import com.stx.xhb.module_basecore.RouterManger;

@Route(path = RouterManger.Path.MAIN_TEST_ACTIVITY)
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        String value = getIntent().getStringExtra("key");
        if (!TextUtils.isEmpty(value)) {
            Toast.makeText(this, "exist param :" + value, Toast.LENGTH_SHORT).show();
        }
    }
}
