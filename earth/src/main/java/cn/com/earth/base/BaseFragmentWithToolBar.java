package cn.com.earth.base;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

import cn.com.earth.dialog.AppWaitDialog;
import cn.com.earth.R;
import cn.com.earth.mvp.IEarthBaseView;
import cn.com.earth.widget.AppToolBar;
import io.reactivex.disposables.CompositeDisposable;

/**
 * 介绍: 带头部的
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午5:38
 */

public abstract class BaseFragmentWithToolBar extends BaseFragment implements IEarthBaseView {
    protected AppToolBar toolBar;
    private CompositeDisposable compositeDisposable;


    @Override
    protected int getTitleLayoutId() {
        return R.layout.earth_toolbar_layout;
    }

    @Override
    protected void initToolBarView(View view) {
        toolBar = (AppToolBar) view.findViewById(R.id.app_tool_bar);
        AppToolBar.AppToolbarConfig config = getAppToolBarConfig();
        if (config != null) {
            toolBar.setConfig(config);
        }
    }

    protected AppToolBar.AppToolbarConfig getAppToolBarConfig() {
        return null;
    }


    @Override
    public CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (compositeDisposable != null) {
            compositeDisposable.isDisposed();
        }
    }

    protected AppWaitDialog appWaitDialog;

    private void checkAppWaitDialogNull() {
        if (appWaitDialog == null) {
            appWaitDialog = new AppWaitDialog(mActivity);
        }
    }

    @Override
    public void showWaitDialog(String title) {
        checkAppWaitDialogNull();
        appWaitDialog.showWaitDialog(title);
    }

    @Override
    public void closeDialog() {
        checkAppWaitDialogNull();
        if (appWaitDialog != null && appWaitDialog.isShowing()) {
            appWaitDialog.closeDialog();
        }
    }

    @Override
    public void delayCloseWithAction(String title, int delayMs, AppWaitDialog.DelayCancelListener l) {
        checkAppWaitDialogNull();
        if (appWaitDialog != null && appWaitDialog.isShowing()) {
            appWaitDialog.delayCloseWithAction(title, delayMs, l);
        }
    }

    @Override
    public void delayCloseWithAction(String title, int delayMs, AppWaitDialog.DelayCancelListener l, boolean isCancelAble) {
        checkAppWaitDialogNull();
        if (appWaitDialog != null && appWaitDialog.isShowing()) {
            appWaitDialog.delayCloseWithAction(title, delayMs, l, isCancelAble);
        }
    }

    @Override
    public void delayCloseWithActionCancelAble(String title, int delayMs, boolean isCancelAble) {
        checkAppWaitDialogNull();
        if (appWaitDialog != null && appWaitDialog.isShowing()) {
            appWaitDialog.delayCloseWithActionCancelAble(title, delayMs, isCancelAble);
        }
    }

    @Override
    public void delayCloseWithSuccess(String title) {
        checkAppWaitDialogNull();
        if (appWaitDialog != null && appWaitDialog.isShowing()) {
            appWaitDialog.delayCloseWithSuccess(title);
        }
    }

    @Override
    public void delayCloseWithFail(String title) {
        checkAppWaitDialogNull();
        if (appWaitDialog != null && appWaitDialog.isShowing()) {
            appWaitDialog.delayCloseWithFail(title);
        }
    }

    @Override
    public Activity getHostActivity() {
        return mActivity;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }
}
