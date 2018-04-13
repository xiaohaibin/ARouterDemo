# ARouterDemo
Android 模块化ARouter  Demo

## 前言

随着公司项目越来越大，人员增多，项目会出现难维护、新人入职后，需要花费很多时间去熟悉项目。最重要的是，每次编译时间真的太久了！！！为了解决这个问题，项目重构、模块化需要提上日程。项目拆分成模块之后，页面的跳转就不能直接startActivity 调用具体的activity了，因为这个Activity已经在另外一个模块中，直接用显示引用是提示不出来的，这时需要通过借助路由库来实现页面的跳转，当然通过反射的方式也是可以跳转到对应页面的。通过这样的路由跳转，而不是显示引用，就达到了模块之间解耦的目的，在不需要的时候，可以不需要引入这个模块编译，提高开发速度，发布的时候又能很方便的集成进来，功能不受影响，这就实现了模块化的第一步。路由框架推荐使用阿里开源的ARouter路由框架，毕竟是大厂开源的框架，稳定性、可靠性也相对较高。


## ARouter框架介绍

###### [ARouter框架地址](https://github.com/alibaba/ARouter)

1. **支持直接解析标准URL进行跳转，并自动注入参数到目标页面中**
2. **支持多模块工程使用**
3. **支持添加多个拦截器，自定义拦截顺序**
4. **支持依赖注入，可单独作为依赖注入框架使用**
5. **支持InstantRun**
6. **支持MultiDex**(Google方案)
7. 映射关系按组分类、多级管理，按需初始化
8. 支持用户指定全局降级与局部降级策略
9. 页面、拦截器、服务等组件均自动注册到框架
10. 支持多种方式配置转场动画
11. 支持获取Fragment
12. 完全支持Kotlin以及混编(配置见文末 其他#5)
13. **支持第三方 App 加固**(使用 arouter-register 实现自动注册)

## 基础使用

 1. **添加依赖和配置**
``` gradle
android {
    defaultConfig {
	...
	javaCompileOptions {
	    annotationProcessorOptions {
		arguments = [ moduleName : project.getName() ]
	    }
	}
    }
}

dependencies {
    // 替换成最新版本, 需要注意的是api
    // 要与compiler匹配使用，均使用最新版可以保证兼容
    compile 'com.alibaba:arouter-api:x.x.x'
    annotationProcessor 'com.alibaba:arouter-compiler:x.x.x'
    ...
}
```

2. **添加注解**
``` java
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/test/activity")
public class YourActivity extend Activity {
    ...
}
```
路由路径，我们可以封装一个`RouterManger`工具类放在`moudle_base`模块中，统一管理整个项目的 `Path`及路由框架的初始化操作, 示例如下：

```
/**
 * @author: xiaohaibin.
 * @time: 2018/4/12
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: 路由框架管理工具类
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

```

3. **初始化SDK**
``` java
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化路由框架
        RouterManger.initRouter(this);
    }
}
```

4. **发起路由操作**
``` java
// 1. 应用内简单的跳转
ARouter.getInstance().build("/test/activity").navigation();

// 2. 跳转并携带参数
  ARouter.getInstance()
                        .build(RouterManger.Path.MAIN_TEST_ACTIVITY)
                        .withString("key", "小猪佩奇身上纹，掌声送给社会人")
                        .navigation(this);
```

5. **声明拦截器(拦截跳转过程，面向切面编程)**
这个是我觉得ARouter框架中比较实用的功能了。通常我们跳转一个需要登录的页面时，每次跳转前都要重复做登陆检查。现在我们只需要声明一个登录拦截器，拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行。是不是很Nice！！！

```
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
    public void process(final Postcard postcard, final InterceptorCallback callback) {
        if (Constants.NEED_LOGIN == postcard.getExtra()) {
           //如果需要再界面展示东西，需要切换到主线程进行caoz
            MainLooper.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT).show();
                    ARouter.getInstance().build(RouterManger.Path.USER_LOGIN_ACTIVITY).navigation();
                    //处理完成，交还控制权
                    callback.onInterrupt(null);
                }
            });
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
```
6. **为目标页面声明更多信息**

 我们经常需要在目标页面中配置一些属性，比方说"是否需要登陆"之类的
可以通过 Route 注解中的 extras 属性进行扩展，这个属性是一个 int值，换句话说，单个int有4字节，也就是32位，可以配置32个开关
 剩下的可以自行发挥，通过字节操作可以标识32个开关，通过开关标记目标页面的一些属性，在拦截器中可以拿到这个标记进行业务逻辑判断

```
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
```

**ARouter更加详细的使用教程可以前往官方GitHub查阅，上述为个人使用过程中觉得需要记录下来东西，不足之处，敬请见谅**

###### [ARouterDemo地址](https://github.com/xiaohaibin/ARouterDemo)

## 如果觉得文章帮到你，喜欢我的文章可以关注我和朋友一起运营的微信公众号，将会定期推送优质技术文章，求关注~~~

![欢迎关注“大话安卓”微信公众号](http://upload-images.jianshu.io/upload_images/1956769-2f49dcb0dc5195b6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 欢迎加入“大话安卓”技术交流群，一起分享，共同进步
![欢迎加入“大话安卓”技术交流群，互相学习提升](http://upload-images.jianshu.io/upload_images/1956769-326c166b86ed8e94.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

