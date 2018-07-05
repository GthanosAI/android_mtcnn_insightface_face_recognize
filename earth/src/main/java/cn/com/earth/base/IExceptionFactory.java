package cn.com.earth.base;

import android.view.View;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午3:54
 */

public interface IExceptionFactory {
    <T extends View & IErrorView>T getErrorView(int errorCode, String msg);

    <T extends View & ILoadAnimation> T getLoadingView();

    View getEmptyView();
}
