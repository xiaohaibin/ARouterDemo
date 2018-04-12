package com.stx.xhb.arouterdemo;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.stx.xhb.module_basecore.RouterManger;

/**
 * @author: xiaohaibin.
 * @time: 2018/4/4
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化路由
        RouterManger.initRouter(this);
    }
}
