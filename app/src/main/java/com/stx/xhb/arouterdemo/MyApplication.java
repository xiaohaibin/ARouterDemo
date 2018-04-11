package com.stx.xhb.arouterdemo;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

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
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
