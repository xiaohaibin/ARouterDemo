package com.stx.xhb.arouterdemo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.stx.xhb.module_basecore.Constants;
import com.stx.xhb.module_basecore.MainLooper;
import com.stx.xhb.module_basecore.RouterManger;

/**
 * @author: xiaohaibin.
 * @time: 2018/4/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: 登录跳转拦截器
 */
@Interceptor(priority = 8, name = "登录跳转拦截器")
public class LoginInterceptor implements IInterceptor {
    Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (Constants.NEED_LOGIN == postcard.getExtra()) {
            ARouter.getInstance().build(RouterManger.Path.USER_LOGIN_ACTIVITY).navigation();
        } else {
            //处理完成，交还控制权
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        mContext = context;
        //此处做一些初始化的工作
    }
}