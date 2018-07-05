package cn.com.earth.tools;

import android.content.Context;

import cn.com.earth.tools.toast.Toast;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  16/12/22 上午12:15
 */

public class EAppEnv {
    public static Context context;
    public static boolean isDebug;
    public static String appVersionName;
    public static String imgUrl;


    private EAppEnv() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */

    public static void init(Context context, boolean isDebug) {
        EAppEnv.context = context.getApplicationContext();
        EAppEnv.isDebug = isDebug;

        DevUtils.setContext(context);
        Toast.setContext(context);
        SharedPreferencesUtils.setContext(context);
        DisplayUtils.setContext(context);
        NetworkUtils.setContext(context);
    }


    public static void setParam(String appVersionName, String imgUrl) {
        EAppEnv.appVersionName = appVersionName;
        EAppEnv.imgUrl = imgUrl;
    }
}
