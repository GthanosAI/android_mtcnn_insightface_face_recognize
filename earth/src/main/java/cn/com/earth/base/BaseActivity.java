package cn.com.earth.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import java.util.UUID;

import cn.com.earth.R;
import cn.com.earth.permission.IPermission;
import cn.com.earth.permission.PermissionCallback;
import cn.com.earth.permission.RunTimePermissionUtils;
import cn.com.earth.tools.toast.Toast;

/**
 * 介绍: BaseActivity
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午1:06
 */

public abstract class BaseActivity extends FragmentActivity implements IPermission, IKey {
    private static final String FIRST_FRAGMENT_TAG = "FIRST_FRAGMENT_TAG";
    private final static int ANI_VERTICAL = 1;
    private final static int ANI_HORIZONTAL = 0;
    private final static int ANI_NONE = -1;
    protected FragmentManager mFragmentManager;
    protected Fragment currentFragment;
    private long lastTime;

    private int resultCode = RESULT_CANCELED;
    private Bundle data;
    int requestCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.beforeSetContentView();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mFragmentManager = getSupportFragmentManager();
        setContentView(getLayoutId());

        if (savedInstanceState == null && getFirstFragment() != null) {
            replace(getFirstFragment(), FIRST_FRAGMENT_TAG, false, ANI_NONE);
        }
    }

    protected
    @LayoutRes
    int getLayoutId() {
        return R.layout.earth_activity_base;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentFragment = getCurrentFragment();
        if (currentFragment != null &&
                ((currentFragment instanceof IDependOnBaseFragment) &&
                        ((IDependOnBaseFragment) currentFragment).isSupportOnBackPressed())) {
            return;
        }
        superOnBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        currentFragment = getCurrentFragment();
        if (currentFragment != null && currentFragment instanceof IDependOnBaseFragment) {
            ((IDependOnBaseFragment) currentFragment).onNewIntent(intent);
        }
    }

    protected void superOnBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() == 0) {
            if (!isExitImmediate()) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTime > 2000) {
                    showToast("再按一次将退出应用");
                    lastTime = currentTime;
                    return;
                } else {
                    System.exit(0);
                }
            }

            if (requestCode != RESULT_CANCELED) {
                if (data == null) {
                    setResult(resultCode);
                } else {
                    Intent intent = new Intent();
                    intent.putExtras(data);
                    setResult(resultCode, intent);
                }
            }
        }

        super.onBackPressed();

    }

    @Override
    public void finish() {
        super.finish();
    }

    protected void replace(Fragment fragment, String tag, boolean isCanBack, int animationType) {
        if (fragment == null) return;
        if (TextUtils.isEmpty(tag)) {
            tag = UUID.randomUUID().toString();
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (animationType == ANI_HORIZONTAL) {
            ft.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);
        } else if (animationType == ANI_VERTICAL) {
            ft.setCustomAnimations(R.anim.bottom_in, R.anim.top_out, R.anim.top_in, R.anim.bottom_out);
        }

        ft.replace(R.id.base_activity_container, fragment, tag);
        if (isCanBack) {
            ft.addToBackStack(tag);
        }

        ft.commitAllowingStateLoss();
    }

    public void replace(final Fragment fragment, String tag, int rid) {
        if (null == fragment) {
            return;
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.base_right_in, R.anim.base_left_out, R.anim.base_left_in, R.anim.base_right_out);
        if (TextUtils.isEmpty(tag)) {
            tag = UUID.randomUUID().toString();
        }
        ft.replace(rid, fragment, tag);

        ft.commitAllowingStateLoss();
    }

    protected abstract Fragment getFirstFragment();

    protected Fragment instanceFragment(Class<? extends Fragment> fragmentClass, Bundle bundle) {
        return Fragment.instantiate(this, fragmentClass.getName(), bundle);
    }

    protected Fragment instanceFragment(Class<? extends Fragment> fragmentClass) {
        return instanceFragment(fragmentClass, getIntent().getExtras());
    }

    protected Fragment instanceFragment(String fragmentName, Bundle bundle) {
        return Fragment.instantiate(this, fragmentName, bundle);
    }

    protected void replace(Fragment fragment) {
        replace(fragment, null);
    }

    protected void replace(Fragment fragment, String tag) {
        replace(fragment, tag, false, ANI_HORIZONTAL);
    }

    public Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(R.id.base_activity_container);
    }

    protected boolean isExitImmediate() {
        return true;
    }

    public void showToast(String msg) {
        Toast.show(msg);
    }

    private PermissionCallback permissionCallback;
    private String permissionHint;

    public void setPermissionHint(String permissionHint) {
        this.permissionHint = permissionHint;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RunTimePermissionUtils.PERMISSION_REQUEST_CODE) {
            if (RunTimePermissionUtils.verifyPermissions(grantResults)) {
                if (permissionCallback != null) {
                    permissionCallback.onPermissionSuccess();
                    permissionCallback = null;
                }
            } else {
                Toast.show(R.drawable.base_app_toast_failure, permissionHint + "失败, 请到手机设置里设置该权限");
                if (permissionCallback != null) {
                    permissionCallback.onPermissionFailure();
                    permissionCallback = null;
                }
                permissionHint = null;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Android M运行时权限请求封装
     *
     * @param permissionDes 权限描述
     * @param runnable      请求权限回调
     * @param permissions   请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull Activity activity, @NonNull String permissionDes, PermissionCallback runnable, @NonNull String... permissions) {
        if (permissions == null || permissions.length == 0) return;
        permissionCallback = runnable;
        permissionHint = permissionDes;
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || RunTimePermissionUtils.checkPermissionGranted(activity, permissions)) {
            if (runnable != null) {
                runnable.onPermissionSuccess();
            }
        } else {
            //permission has not been granted.
            RunTimePermissionUtils.requestPermission(activity, permissionDes, RunTimePermissionUtils.PERMISSION_REQUEST_CODE, permissions);
        }
    }

    public void beforeSetContentView() {
//        hideSystemNavigationBar();
    }

    protected void hideSystemNavigationBar() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View view = this.getWindow().getDecorView();
            view.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}

