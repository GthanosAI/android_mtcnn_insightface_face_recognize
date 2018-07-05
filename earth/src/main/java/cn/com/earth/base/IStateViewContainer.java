package cn.com.earth.base;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午4:24
 */

public interface IStateViewContainer {
    void showLoadingView();

    void showErrorView(int errorCode, String msg);

    void showEmptyView();

    void showSuccessView();
}
