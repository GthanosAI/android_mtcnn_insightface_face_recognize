package cn.com.earth.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * 介绍:
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/8/17 下午1:06
 */

public class DisplayUtils {
    private static Context context;

    public static void setContext(Context Context) {
        DisplayUtils.context = Context;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        if (null == context) return (int) dpValue;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        if (null == context) return (int) pxValue;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px（正常字体下，1sp=1dp）
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        if (null == context) return (int) spValue;
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * sp转dp
     *
     * @param spValue
     * @return
     */
    public static int sp2dp(float spValue) {
        int sp2Px = sp2px(spValue);
        return px2dip(sp2Px);
    }


    public static int getQToPx(int rid) {
        return (int) context.getResources().getDimension(rid);
    }

    public static Drawable getDrawable(int drawable) {
        return ContextCompat.getDrawable(context, drawable);
    }
}
