package com.stx.xhb.module_basecore;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author: xiaohaibin.
 * @time: 2018/4/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: 路由管理工具类
 */
public class RouterManger {

    public static class Path {
        public static final String MAIN_TEST_ACTIVITY = "/main/test2";
        public static final String USER_LOGIN_ACTIVITY = "/user/login";
        public static final String MAIN_NEED_LOGIN_ACTIVITY = "/main/need_login";
    }

    /**
     * 初始化路由框架
     * @param application
     */
    public static void initRouter(Application application) {
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openDebug();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openLog();
        }
        ARouter.init(application);
    }
}
