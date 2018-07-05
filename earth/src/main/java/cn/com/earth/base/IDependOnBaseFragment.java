package cn.com.earth.base;

import android.content.Intent;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午2:03
 */

public interface IDependOnBaseFragment {
    void onNewIntent(Intent intent);

    boolean isSupportOnBackPressed();
}
