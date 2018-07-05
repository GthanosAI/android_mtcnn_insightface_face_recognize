package cn.com.earth.dialog;

import android.app.Activity;
import android.os.Handler;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/1/3 下午4:38
 */

public interface IWaitDialogView {
    void showWaitDialog(String title);

    void closeDialog();

    void delayCloseWithAction(String title, int delayMs, AppWaitDialog.DelayCancelListener l);

    void delayCloseWithAction(String title, int delayMs, AppWaitDialog.DelayCancelListener l, boolean isCancelAble);


    void delayCloseWithActionCancelAble(String title, int delayMs, boolean isCancelAble);

    void delayCloseWithSuccess(String title);

    void delayCloseWithFail(String title);

    Activity getHostActivity();

    Handler getHandler();
}
