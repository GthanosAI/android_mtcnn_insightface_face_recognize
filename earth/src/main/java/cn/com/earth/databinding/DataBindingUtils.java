package cn.com.earth.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import cn.com.earth.tools.LoadImageUtils;

/**
 * 介绍: databinding的工具类
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午5:52
 */

public class DataBindingUtils {
    @BindingAdapter(("circlerImageUrl"))
    public static void loadCirclerImage(ImageView imageView, String url) {
        LoadImageUtils.loadCircleAvatar(imageView, url);
    }

    @BindingAdapter(("iconUrl"))
    public static void loadIconImage(ImageView imageView, String url) {
        LoadImageUtils.loadIcon(imageView, url);
    }
}
