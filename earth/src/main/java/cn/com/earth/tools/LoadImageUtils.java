package cn.com.earth.tools;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import cn.com.earth.R;

/**
 * 介绍: 显示图片工具类
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午6:37
 */

public class LoadImageUtils {
    public static void loadCircleAvatar(ImageView imageView, String url) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(url)) {

                RequestOptions requestOptions = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.app_icon_avatar_default)
                        .dontAnimate()
                        .placeholder(R.drawable.app_icon_avatar_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
//                        .transform(new CircleTransform(imageView.getContext()));

                Glide.with(imageView.getContext())
                        .applyDefaultRequestOptions(requestOptions)
                        .load(url)
                        .thumbnail(0.1f)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.app_icon_avatar_default);
            }
        }
    }

    public static void loadIcon(ImageView imageView, String url) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(url)) {
                RequestOptions requestOptions = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.app_icon_avatar_default)
                        .dontAnimate()
                        .placeholder(R.drawable.app_icon_avatar_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
//                        .transform(new CircleTransform(imageView.getContext()));
                Glide.with(imageView.getContext())
                        .applyDefaultRequestOptions(requestOptions)
                        .load(url)
                        .thumbnail(0.1f)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.app_icon_avatar_default);
            }
        }
    }
}
