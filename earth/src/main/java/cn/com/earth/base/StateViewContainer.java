package cn.com.earth.base;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午3:53
 */

public class StateViewContainer extends FrameLayout implements IStateViewContainer {
    private IExceptionFactory mExceptionView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private ILoadAnimation loadAnimation;

    public StateViewContainer(@NonNull Context context) {
        super(context);
        init(context);
    }

    public StateViewContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StateViewContainer(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public StateViewContainer(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        mExceptionView = new IExceptionFactory() {
            @Override
            public View getErrorView(int errorCode, String msg) {
                return new DefaultErrorView(context);
            }

            @Override
            public <T extends View & ILoadAnimation> T getLoadingView() {
                return (T) new DefaultLoadingView(context);
            }

            @Override
            public View getEmptyView() {
                return new DefaultEmptyView(context);
            }
        };
    }

    public void setExceptionFactory(final IExceptionFactory exceptionFactory) {
        if (exceptionFactory == null) {
            return;
        }
        mExceptionView = exceptionFactory;
    }

    @Override
    public void showLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = mExceptionView.getLoadingView();
            loadAnimation = (ILoadAnimation) mLoadingView;
            addView(mLoadingView);
        }

        setVisibility(mLoadingView, true);
        setVisibility(mEmptyView, false);
        setVisibility(mErrorView, false);

        if (loadAnimation != null) {
            loadAnimation.start();
        }
    }

    @Override
    public void showErrorView(int errorCode, String msg) {
        if (loadAnimation != null) {
            loadAnimation.stop();
        }

        if (mErrorView == null) {
            mErrorView = mExceptionView.getErrorView(errorCode, msg);
            ((IErrorView) mErrorView).setErrorInfo(errorCode, msg);
            addView(mErrorView);
        } else {
            ((IErrorView) mErrorView).setErrorInfo(errorCode, msg);
        }

        setVisibility(mLoadingView, false);
        setVisibility(mEmptyView, false);
        setVisibility(mErrorView, true);

        if (handler != null) {
            mErrorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.onClickError();
                }
            });
        }
    }

    @Override
    public void showEmptyView() {

        if (loadAnimation != null) {
            loadAnimation.stop();
        }

        if (mEmptyView == null) {
            mEmptyView = mExceptionView.getEmptyView();
            addView(mEmptyView);
        }

        setVisibility(mLoadingView, false);
        setVisibility(mEmptyView, true);
        setVisibility(mErrorView, false);

        if (handler != null) {
            mEmptyView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.onClickEmpty();
                }
            });
        }
    }

    @Override
    public void showSuccessView() {
        if (loadAnimation != null) {
            loadAnimation.stop();
        }

        setVisibility(mLoadingView, false);

        if (mEmptyView != null) {
            removeView(mEmptyView);
            mEmptyView = null;
        }
        if (mErrorView != null) {
            removeView(mErrorView);
            mErrorView = null;
        }
    }

    private void setVisibility(View view, boolean visible) {
        if (view != null) {
            if (visible) {
                if (view.getVisibility() != VISIBLE) {
                    view.setVisibility(VISIBLE);
                }
            } else {
                if (view.getVisibility() != GONE) {
                    view.setVisibility(GONE);
                }
            }
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private ExceptionViewHandler handler;

    public StateViewContainer setHandler(ExceptionViewHandler handler) {
        this.handler = handler;
        return this;
    }

    public interface ExceptionViewHandler {
        void onClickEmpty();

        void onClickError();
    }
}
