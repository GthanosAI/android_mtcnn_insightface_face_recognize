package cn.com.earth.permission;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import cn.com.earth.R;


/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  16/8/10 下午10:54
 */
public class RunTimePermissionUtils {
    public static final int PERMISSION_REQUEST_CODE = 100;

    /**
     * 文件读写权限
     *
     * @param activity
     * @param callback
     */
    public static void onStorage(final Activity activity, final PermissionCallback callback) {
        if (null != activity && !activity.isFinishing() && activity instanceof IPermission) {
            ((IPermission) activity).performCodeWithPermission(activity, activity.getString(R.string.app_name) + "请求获取读写文件码权限", new PermissionCallback() {
                @Override
                public void onPermissionSuccess() {
                    if (null != callback) {
                        callback.onPermissionSuccess();
                    }
                }

                @Override
                public void onPermissionFailure() {
                    if (null != callback) {
                        callback.onPermissionFailure();
                    }
                }
            }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    public static void onCamera(final Activity activity, final PermissionCallback callback) {
        if (null != activity && !activity.isFinishing() && activity instanceof IPermission) {
            ((IPermission) activity).performCodeWithPermission(activity, activity.getString(R.string.app_name) + "请求获取相机权限", new PermissionCallback() {
                @Override
                public void onPermissionSuccess() {
                    if (null != callback) {
                        callback.onPermissionSuccess();
                    }
                }

                @Override
                public void onPermissionFailure() {
                    if (null != callback) {
                        callback.onPermissionFailure();
                    }
                }
            }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 請求讀取聯繫人權限
     */
    public static void onContacts(final Activity activity, final PermissionCallback callback) {
        if (null != activity && !activity.isFinishing() && activity instanceof IPermission) {
            ((IPermission) activity).performCodeWithPermission(activity, activity.getString(R.string.app_name) + "请求获取读取联系人权限", new PermissionCallback() {
                @Override
                public void onPermissionSuccess() {
                    if (null != callback) {
                        callback.onPermissionSuccess();
                    }
                }

                @Override
                public void onPermissionFailure() {
                    if (null != callback) {
                        callback.onPermissionFailure();
                    }
                }
            }, Manifest.permission.READ_CONTACTS);
        }
    }


    /**
     * 初始化获取手机号码/设备ID
     */
    public static void onPhoneNum(final Activity activity) {
        if (null != activity && !activity.isFinishing() && activity instanceof IPermission) {
            ((IPermission) activity).performCodeWithPermission(activity, activity.getString(R.string.app_name) + "请求获取手机号码权限", new PermissionCallback() {
                @Override
                public void onPermissionSuccess() {
                  /*  Constant.PHONE_NUM = DevUtils.getLine1Number();
                    Constant.DEVICE_ID = DevUtils.getDeviceId();*/
                }

                @Override
                public void onPermissionFailure() {

                }
            }, Manifest.permission.READ_PHONE_STATE);
        }
    }


    /**
     * 拨打电话
     *
     * @param activity
     * @param phoneNum
     */
    public static void onCall(final Activity activity, final String phoneNum) {

        if (null != activity && !activity.isFinishing() && activity instanceof IPermission && !TextUtils.isEmpty(phoneNum)) {
            ((IPermission) activity).performCodeWithPermission(activity, activity.getString(R.string.app_name) + "请求获取拨打电话权限", new PermissionCallback() {
                @Override
                public void onPermissionSuccess() {
                    new AlertDialog.Builder(activity)
                            .setMessage("拨打：" + phoneNum + "?")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + phoneNum));
                                    activity.startActivity(intent);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                }

                @Override
                public void onPermissionFailure() {

                }
            }, Manifest.permission.CALL_PHONE);
        }
    }


    /**
     * 定位
     *
     * @param activity
     * @param callback
     */
    public static void onLocation(final Activity activity, final PermissionCallback callback) {
        if (null != activity && !activity.isFinishing() && activity instanceof IPermission) {
            ((IPermission) activity).performCodeWithPermission(activity, activity.getString(R.string.app_name) + "请求定位权限", new PermissionCallback() {
                @Override
                public void onPermissionSuccess() {
                    if (null != callback) {
                        callback.onPermissionSuccess();
                    }
                }

                @Override
                public void onPermissionFailure() {
                    if (null != callback) {
                        callback.onPermissionFailure();
                    }
                }
            }, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }


    public static boolean checkPermissionGranted(Activity activity, String[] permissions) {
        boolean flag = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
        }
        return flag;
    }


    public static void requestPermission(final Activity activity, String permissionDes, final int requestCode, final String[] permissions) {
        if (shouldShowRequestPermissionRationale(activity, permissions)) {
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            new AlertDialog.Builder(activity)
                    .setMessage(permissionDes)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, permissions, requestCode);

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    private static boolean shouldShowRequestPermissionRationale(Activity activitiy, String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activitiy, p)) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 文件读写权限
     *
     * @param activity
     * @param callback
     */
    public static void checkOnRecord(final Activity activity, final PermissionCallback callback) {
        if (null != activity && !activity.isFinishing() && activity instanceof IPermission) {
            ((IPermission) activity).performCodeWithPermission(activity, activity.getString(R.string.app_name) + "请求获取录音权限", new PermissionCallback() {
                @Override
                public void onPermissionSuccess() {
                    if (null != callback) {
                        callback.onPermissionSuccess();
                    }
                }

                @Override
                public void onPermissionFailure() {
                    if (null != callback) {
                        callback.onPermissionFailure();
                    }
                }
            }, Manifest.permission.RECORD_AUDIO);
        }
    }

}
