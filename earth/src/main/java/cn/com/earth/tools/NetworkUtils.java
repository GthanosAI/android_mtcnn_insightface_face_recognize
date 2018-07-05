package cn.com.earth.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 介绍: 网络工具类
 * 作者: jacky
 * 邮箱: none
 * 时间:  16/7/28 下午8:45
 */
public class NetworkUtils {
    private static Context context;

    public static void setContext(Context Context) {
        NetworkUtils.context = Context;
    }

    /*网络状态，下载只需要区分wifi和非wifi即可*/
    public static final int NETWORK_NONE = 1;
    public static final int NETWORK_WIFI = 2;
    public static final int NETWORK_OTHER = 3;

    /**
     * 是否联网
     *
     * @return
     */
    public static boolean isNetwork() {
        if (null != context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null == cm || (null != cm && null == cm.getActiveNetworkInfo())) {
                return false;
            }
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return false;
            }
            return info.isConnected();
        }
        return false;
    }

    /**
     * 是否联网
     *
     * @return
     */
    public static boolean isNetwork(Context context) {
        if (null != context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null == cm || (null != cm && null == cm.getActiveNetworkInfo())) {
                return false;
            }
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return false;
            }
            return info.isAvailable();
        }
        return false;
    }



    /**
     * 是否连接WIFI
     *
     * @return
     */
    public static boolean isWifiNetwork() {
        if (null != context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetInfo != null
                    && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前是否网络状态－WIFI/非WIFI/无网络
     *
     * @return
     */
    public static int getNetworkStatus() {
        if (isNetwork(context)) {
            if (isWifiNetwork()) {
                return NETWORK_WIFI;
            } else {
                return NETWORK_OTHER;
            }
        }
        return NETWORK_NONE;

    }


    // 判断移动网络
    public static boolean isMobileConnected() {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }



    public static String getMacAddress(){
        //在wifi未开启状态下，仍然可以获取MAC地址，但是IP地址必须在已连接状态下否则为0
        String macAddress = "default", ip = null;
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
        if (null != info) {
            macAddress = info.getMacAddress();

        }
        return macAddress;
    }
}
