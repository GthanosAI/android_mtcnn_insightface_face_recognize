package cn.com.earth.tools.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import cn.com.earth.R;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  16/12/22 上午12:03
 */

public class Toast {
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static AppToast appToast;

    private static Context context;

    public static void setContext(Context context) {
        Toast.context = context;
    }

    /**
     * 初始化CstToast
     */
    private static boolean initCstToast() {
        if (null == appToast) {
            appToast = new AppToast(context);
        }
        return true;
    }

    /**
     * 这个方法任何5中类型的提示都可以调用，但是调用时需要传递type字段
     *
     * @param type             详见AppToast中的TYPE定义
     * @param imgRes           没有图片资源时请传递CstToast.NO_IMG(-1)
     * @param toastTextStr     第一排文字，没有则传递null
     * @param toastBelowImgStr 第二排文字，没有则传递null
     */
    public static void show(final int type,
                            final int imgRes,
                            final String toastTextStr,
                            final String toastBelowImgStr) {
        try {

            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (initCstToast()) {
                        appToast.setContent(type, imgRes, toastTextStr,
                                toastBelowImgStr);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***************** 下面5个show()方法分别为不同样式时的调用 ****************************/

    /**
     * 两排显示：第一排图片加文字 ，第二排文字，这个方法中所有内容都不能传递为null
     *
     */
    public static void show(final int imgRes,
                            final String toastTextStr,
                            final String toastBelowImgStr) {
        try {

            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (initCstToast()) {
                        appToast.setHorizontalContent(imgRes, toastTextStr,
                                toastBelowImgStr);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一排显示图片和文字，这个方法中所有内容都不能传递为null
     *
     */
    public static void show(final int imgRes, final String toastTextStr) {
        try {

            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (initCstToast()) {
                        appToast.setHorizontalContent(imgRes, toastTextStr);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 只显示一排文字，这个方法中所有内容都不能传递为null
     *
     */
    public static void show(final String toastTextStr) {
        try {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (initCstToast()) {
                        appToast.setHorizontalContent(toastTextStr);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showWarning(final String toastTextStr) {
        try {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (initCstToast()) {
                        appToast.setHorizontalContent(R.drawable.base_app_toast_failure, toastTextStr);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 只显示图片，这个方法中所有内容都不能传递为null
     *
     */
    public static void show(final int imgRes) {
        try {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (initCstToast()) {
                        appToast.setHorizontalContent(imgRes);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 竖排显示：上面图片，下面文字，这个方法中所有内容都不能传递为null
     *
     */
    public static void showUpImgDownText(final int imgRes, final String toastBelowImgStr) {
        try {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (initCstToast()) {
                        appToast.setVerticalContent(imgRes, toastBelowImgStr);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
