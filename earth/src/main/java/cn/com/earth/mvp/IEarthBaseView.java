package cn.com.earth.mvp;

import cn.com.earth.dialog.IWaitDialogView;
import cn.com.earth.base.IStateViewContainer;
import io.reactivex.disposables.CompositeDisposable;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/23 下午11:02
 */

public interface IEarthBaseView extends IStateViewContainer, IWaitDialogView {
    CompositeDisposable getCompositeDisposable();
}
