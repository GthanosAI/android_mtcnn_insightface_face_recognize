package cn.com.earth.permission;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/26 下午7:33
 */

public interface IPermission {

    void performCodeWithPermission(@NonNull Activity activity, @NonNull String permissionDes, PermissionCallback runnable, @NonNull String... permissions);
}
