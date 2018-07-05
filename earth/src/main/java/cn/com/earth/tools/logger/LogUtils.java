package cn.com.earth.tools.logger;


import cn.com.earth.tools.EAppEnv;

/**
 * 介绍: 对第三方库 com.orhanobut:logger:2.1.1 包装
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/8/16 下午5:22
 */

public class LogUtils {
    public static boolean isDebug = EAppEnv.isDebug;
    private static boolean isInit = false;

    private static void checkInit() {
        if (!isInit) {
            isInit = true;
            Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy
                    .newBuilder()
                    .methodCount(0)
                    .showThreadInfo(false)
                    .build()) {
                @Override
                public boolean isLoggable(int priority, String tag) {
                    return isDebug;
                }
            });
        }
    }

    public static void addDiskLoggger() {
        Logger.addLogAdapter(new DiskLogAdapter());
    }

    public static void reset(){
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isDebug;
            }
        });
    }

    public static void clearLogAdapters() {
        checkInit();
        Logger.clearLogAdapters();
    }


    public static Printer t(String tag) {
        checkInit();
        return Logger.t(tag);
    }

    public static void log(int priority, String tag, String message, Throwable throwable) {
        checkInit();
        Logger.log(priority, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        checkInit();
        Logger.d(message, args);
    }

    public static void d(Object object) {
        checkInit();
        Logger.d(object);
    }

    public static void e(String message, Object... args) {
        checkInit();
        Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        checkInit();
        Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        checkInit();
        Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        checkInit();
        Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        checkInit();
        Logger.w(message, args);
    }


    public static void wtf(String message, Object... args) {
        checkInit();
        Logger.wtf(message, args);
    }


    public static void json(String json) {
        checkInit();
        Logger.json(json);
    }

    public static void xml(String xml) {
        checkInit();
        Logger.xml(xml);
    }
}
