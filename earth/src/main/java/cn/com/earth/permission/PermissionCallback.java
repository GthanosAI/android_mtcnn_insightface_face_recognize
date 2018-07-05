package cn.com.earth.permission;

/**
 * 运行时权限申请
 */
public interface PermissionCallback {
    void onPermissionSuccess();

    void onPermissionFailure();
}