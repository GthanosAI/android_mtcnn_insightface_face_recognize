package cn.com.earth.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import cn.com.earth.R;

/**
 * 介绍: base fragment
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午1:06
 */

public abstract class BaseFragment extends Fragment implements IStateViewContainer, IDependOnBaseFragment,IKey {
    protected FragmentManager mChildFragmentManager;
    protected Activity mActivity;
    protected LayoutInflater mInflater;
    protected View rootView;
    private StateViewContainer mStateView;
    protected View contentView;
    private boolean isBindData = false;
    protected Handler handler = new Handler();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChildFragmentManager = getChildFragmentManager();
    }

    // 不管缓存策略是怎么样的，保证这个oncreateView ->onActivityCreated->onStart->onResume生命周期正常的执行
    protected void onCreateView(Bundle savedInstanceState) {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (bindDataEveryOnce()) {
            bindDataAndListener();
        } else {
            if (!isBindData) {
                isBindData = true;
                bindDataAndListener();
            }
        }

        this.isViewInitiated = true;
        preparedToLoadData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null || !needCacheRootView()) {
            rootView = initView(inflater, container);
            initView(savedInstanceState);
        } else {
            // 当多个fragment 切换的时候会重复调用 onCreateView， 为了避免不断重复的重新创建view
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        onCreateView(savedInstanceState);
        return rootView;
    }

    protected int getContainerLayout() {
        return R.layout.earth_fragment_base;
    }

    private View initView(LayoutInflater inflater, ViewGroup parent) {
        View rootView = inflater.inflate(getContainerLayout(), parent, false);
        FrameLayout titleLayout = (FrameLayout) rootView.findViewById(R.id.titleContainer);

        @LayoutRes int titleLayoutId = getTitleLayoutId();
        if (titleLayoutId != 0) {
            View view = inflater.inflate(titleLayoutId, titleLayout);
            initToolBarView(view);
        }

        mStateView = (StateViewContainer) rootView.findViewById(R.id.stateView);
        mStateView.setExceptionFactory(configExceptionView());
        mStateView.setHandler(getExceptionViewHandler());

        int successLayoutId = getLayoutId();
        if (successLayoutId != 0) {
            contentView = inflater.inflate(successLayoutId, mStateView, false);
            mStateView.addView(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        } else {
            contentView = getLayoutView();
            if (contentView != null) {
                mStateView.addView(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            }
        }

        return rootView;
    }

    @LayoutRes
    protected abstract int getTitleLayoutId();

    protected
    @LayoutRes
    abstract int getLayoutId();

    protected View getLayoutView() {
        return null;
    }

    protected abstract void initToolBarView(View view);

    protected void initView(Bundle savedInstance) {
    }

    protected IExceptionFactory configExceptionView() {
        return null;
    }

    protected boolean needCacheRootView() {
        return true;
    }

    @Override
    public void showLoadingView() {
        if (mStateView != null) {
            mStateView.showLoadingView();
        }
    }

    @Override
    public void showErrorView(int errorCode, String msg) {
        if (mStateView != null) {
            mStateView.showErrorView(errorCode, msg);
        }
    }

    @Override
    public void showEmptyView() {
        if (mStateView != null) {
            mStateView.showEmptyView();
        }
    }

    @Override
    public void showSuccessView() {
        if (mStateView != null) {
            mStateView.showSuccessView();
        }
    }

    // lazyload
    private boolean isVisibleToUser;
    private boolean isViewInitiated;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        preparedToLoadData();
    }

    private void preparedToLoadData() {
        if (isViewInitiated && isViewInitiated) {
            onLazyLoad();
        }
    }

    protected void onLazyLoad() {
    }

    protected boolean bindDataEveryOnce() {
        return false;
    }

    protected abstract void bindDataAndListener();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mChildFragmentManager != null) {
            List<Fragment> fragments = mChildFragmentManager.getFragments();
            if (fragments != null && fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    if (fragment != null && fragment.isAdded() && fragment.isVisible() && fragment.getUserVisibleHint()) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
        }
    }


    @Override
    public void onNewIntent(Intent intent) {
        if (mChildFragmentManager != null) {
            List<Fragment> fragments = mChildFragmentManager.getFragments();
            if (fragments != null && fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    if (fragment != null && fragment.isAdded() && fragment.isVisible() && fragment.getUserVisibleHint()) {
                        ((BaseFragment) fragment).onNewIntent(intent);
                    }
                }
            }
        }
    }

    @Override
    public boolean isSupportOnBackPressed() {
        return false;
    }

    public final void finishAll() {
        if (mActivity instanceof FragmentActivity) {
            ((FragmentActivity) mActivity).supportFinishAfterTransition();
            ((FragmentActivity) mActivity).overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }


    public final void finishAllWithResult(int resultCode, Intent data) {
        mActivity.setResult(resultCode, data);
        finishAll();

    }

    public <T extends View> T findViewById(int rid){
        return (T)rootView.findViewById(rid);
    }

    public void bindViewListener(int rId, View.OnClickListener l){
        View view = findViewById(rId);
        if (view != null){
            view.setOnClickListener(l);
        }
    }

    protected StateViewContainer.ExceptionViewHandler getExceptionViewHandler(){
        return null;
    }


}
