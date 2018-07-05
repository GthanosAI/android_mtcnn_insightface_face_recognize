package cn.com.earth.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * 介绍:
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/8/17 上午11:12
 */

public class DevUtils {

    private static Context context;

    public static void setContext(Context context) {
        DevUtils.context = context;
    }

    /**
     * 获取屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     * <p/>
     * 单位：无
     *
     * @return
     */
    public static float getDensity() {
        if (null != context) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.density;
        }
        return 0;
    }


    /**
     * 获取屏幕密度（每寸像素：120/160/240/320）
     * <p/>
     * 单位：dpi
     *
     * @return
     */
    public static float getDensityDpi() {
        if (null != context) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.densityDpi;
        }
        return 0f;
    }

    /**
     * 获取屏幕宽度
     * <p/>
     * 单位：px
     *
     * @return
     */
    public static int getScreenWidth() {
        if (null != context) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.widthPixels;
        }
        return 0;
    }

    /**
     * 获取屏幕高度
     * <p/>
     * 单位：px
     *
     * @return
     */
    public static int getScreenHeight() {
        if (null != context) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.heightPixels;
        }
        return 0;
    }

    /**
     * 获取手机状态栏高度
     * <p/>
     * 单位：px
     */
    public static int getStatusBarHeight() {
        if (null != context) {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int statusBarHeight = 0;
            int x;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(x);
                return statusBarHeight;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 是否橫屏
     *
     * @param activity
     * @return
     */
    public static boolean isLandscape(final Activity activity) {
        if (null != activity) {
            int orientation = activity.getRequestedOrientation();
            switch (orientation) {
                case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                case ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE:
                case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                    return true;
                case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                case ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT:
                case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                    return false;
            }
        }
        return false;
    }

    /**
     * 是否竖屏
     *
     * @param activity
     * @return
     */
    public static boolean isPortrait(final Activity activity) {
        if (null != activity) {
            int orientation = activity.getRequestedOrientation();
            switch (orientation) {
                case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                case ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE:
                case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                    return false;
                case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                case ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT:
                case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                    return true;
            }
        }
        return true;
    }

    /**
     * 获移动终端的唯一标识.如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID
     *
     * @return
     */
    public static String getDeviceId() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取移动终端的软件版本，例如：GSM手机的IMEI/SV码
     *
     * @return
     */
    public static String getDeviceSoftwareVersion() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getDeviceSoftwareVersion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取手机号码，对于GSM网络来说即MSISDN
     *
     * @return
     */
    public static String getLine1Number() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getLine1Number();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取ISO标准的国家码，即国际长途区号
     *
     * @return
     */
    public static String getNetworkCountryIso() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getLine1Number();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取MCC+MNC代码 (SIM卡运营商国家代码和运营商网络代码)(IMSI)
     *
     * @return
     */
    public static String getNetworkOperator() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getNetworkOperator();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取移动网络运营商的名字(SPN)
     *
     * @return
     */
    public static String getNetworkOperatorName() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getNetworkOperatorName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取网络类型
     * <p/>
     * NETWORK_TYPE_CDMA   网络类型为CDMA
     * NETWORK_TYPE_EDGE   网络类型为EDGE
     * NETWORK_TYPE_EVDO_0 网络类型为EVDO0
     * NETWORK_TYPE_EVDO_A 网络类型为EVDOA
     * NETWORK_TYPE_GPRS   网络类型为GPRS
     * NETWORK_TYPE_HSDPA  网络类型为HSDPA
     * NETWORK_TYPE_HSPA   网络类型为HSPA
     * NETWORK_TYPE_HSUPA  网络类型为HSUPA
     * NETWORK_TYPE_UMTS   网络类型为UMTS
     * <p/>
     * 在中国，联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
     *
     * @return
     */
    public static int getNetworkType() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getNetworkType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TelephonyManager.NETWORK_TYPE_GPRS;
    }

    /**
     * 获取移动终端的类型
     * <p/>
     * PHONE_TYPE_CDMA  手机制式为CDMA，电信
     * PHONE_TYPE_GSM   手机制式为GSM，移动和联通
     * PHONE_TYPE_NONE  手机制式未知
     *
     * @return
     */
    public static int getPhoneType() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getPhoneType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取SIM卡提供商的国家代码
     *
     * @return
     */
    public static String getSimCountryIso() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getSimCountryIso();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取MCC+MNC代码 (SIM卡运营商国家代码和运营商网络代码)(IMSI)
     *
     * @return
     */
    public static String getSimOperator() {
        if (null != context) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSimOperator();
        }
        return null;
    }

    /**
     * 获取SIM卡运营商名称
     *
     * @return
     */
    public static String getSimOperatorName() {

        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getSimOperatorName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取SIM卡的序列号(IMEI)
     *
     * @return
     */
    public static String getSimSerialNumber() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getSimSerialNumber();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取移动终端
     * SIM_STATE_ABSENT         SIM卡未找到
     * SIM_STATE_NETWORK_LOCKED SIM卡网络被锁定，需要Network PIN解锁
     * SIM_STATE_PIN_REQUIRED   SIM卡PIN被锁定，需要User PIN解锁
     * SIM_STATE_PUK_REQUIRED   SIM卡PUK被锁定，需要User PUK解锁
     * SIM_STATE_READY          SIM卡可用
     * SIM_STATE_UNKNOWN        SIM卡未知
     *
     * @return
     */
    public static int getSimState() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getSimState();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TelephonyManager.SIM_STATE_ABSENT;
    }

    /**
     * 获取用户唯一标识，比如GSM网络的IMSI编号
     *
     * @return
     */
    public static String getSubscriberId() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getSubscriberId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取语音邮件号码
     *
     * @return
     */
    public static String getVoiceMailNumber() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getVoiceMailNumber();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否处于漫游状态
     *
     * @return
     */
    public static boolean isNetworkRoaming() {
        try {
            if (null != context) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.isNetworkRoaming();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前网络状态(是否可用)
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean netWorkStatus = false;
        try {
            if (null != context) {
                ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (null != connManager.getActiveNetworkInfo()) {
                    netWorkStatus = connManager.getActiveNetworkInfo().isAvailable();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netWorkStatus;
    }

    /**
     * 获取当前设备ip
     *
     * @return
     */
    public static String getIP() {
        String IP = null;
        StringBuilder IPStringBuilder = new StringBuilder();
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
                Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
                while (inetAddressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = inetAddressEnumeration.nextElement();
                    if (!inetAddress.isLoopbackAddress() &&
                            !inetAddress.isLinkLocalAddress() &&
                            inetAddress.isSiteLocalAddress()) {
                        IPStringBuilder.append(inetAddress.getHostAddress().toString() + "\n");
                    }
                }
            }
        } catch (SocketException ex) {

        }

        IP = IPStringBuilder.toString();
        return IP;
    }


    /**
     * 最靠谱获取mac地址
     *
     * @re
     */
    public static String getMacAddress() {
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig", "HWaddr");

        if (result == null) {
            return "网络出错，请检查网络";
        }
        if (result.length() > 0 && result.contains("HWaddr")) {
            Mac = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);
            if (Mac.length() > 1) {
                result = Mac.toLowerCase();
            }
        }
        return result.trim();
    }


    public static String callCmd(String cmd, String filter) {
        String result = "";
        String line = "";
        InputStreamReader is = null;
        BufferedReader br = null;

        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            is = new InputStreamReader(proc.getInputStream());
            br = new BufferedReader(is);

            //执行命令cmd，只取结果中含有filter的这一行
            while ((line = br.readLine()) != null && !line.contains(filter)) {
                //result += line;
            }

            result = line;

            if (br != null) {
                br.close();
            }

            if (is != null) {
                is.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDevModel() {
        return Build.MODEL;
    }

    /**
     * 是否三星曲面手机，有几个地方需要特殊处理
     *
     * @return
     */
    public static boolean isSmEdge() {
        String model = getDevModel();
        return "SM-G9250".equals(model) || "SM-G9280".equals(model);
    }


    //UUID+设备号序列号 唯一识别码（不可变）
    public static String getUUID() {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    public static String getSystemVersion() {
        return Build.VERSION.SDK;
    }


    /**
     * [获取cpu类型和架构]
     *
     * @return 三个参数类型的数组，第一个参数标识是不是ARM架构，第二个参数标识是V6还是V7架构，第三个参数标识是不是neon指令集
     */
    public static Object[] getCpuArchitecture() {
        Object[] mArmArchitecture = new Object[3];
        try {
            InputStream is = new FileInputStream("/proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            try {
                String nameProcessor = "Processor";
                String nameFeatures = "Features";
                String nameModel = "model name";
                String nameCpuFamily = "cpu family";
                while (true) {
                    String line = br.readLine();
                    String[] pair = null;
                    if (line == null) {
                        break;
                    }
                    pair = line.split(":");
                    if (pair.length != 2)
                        continue;
                    String key = pair[0].trim();
                    String val = pair[1].trim();
                    if (key.compareTo(nameProcessor) == 0) {
                        String n = "";
                        for (int i = val.indexOf("ARMv") + 4; i < val.length(); i++) {
                            String temp = val.charAt(i) + "";
                            if (temp.matches("\\d")) {
                                n += temp;
                            } else {
                                break;
                            }
                        }
                        mArmArchitecture[0] = "ARM";
                        mArmArchitecture[1] = Integer.parseInt(n);
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameFeatures) == 0) {
                        if (val.contains("neon")) {
                            mArmArchitecture[2] = "neon";
                        }
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameModel) == 0) {
                        if (val.contains("Intel")) {
                            mArmArchitecture[0] = "INTEL";
                            mArmArchitecture[2] = "atom";
                        }
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameCpuFamily) == 0) {
                        mArmArchitecture[1] = Integer.parseInt(val);
                        continue;
                    }
                }
            } finally {
                br.close();
                ir.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mArmArchitecture;
    }

    /**
     * 硬件架构是够支持，为了解决友盟热修复so文件找不到的问题
     *
     * @return
     */
    public static boolean isSupport() {
        try {
            Object[] objects = getCpuArchitecture();
            if (objects != null && objects.length == 3 && objects[0] != null) {
                String arch = (String) objects[0];
                if (arch.startsWith("ARM") || arch.startsWith("X86")) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {

        }

        return true;
    }

    /**
     * 是否ZTE
     *
     * @return
     */
    public static boolean isZTE() {
        return Build.MODEL.toLowerCase().contains("zte");
    }


    /**
     * 获取虚拟按键栏高度
     *
     * @return
     */
    public static int getNavigationBarHeight() {
        int result = 0;
        if (hasNavBar()) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     * @return
     */
    private static boolean hasNavBar() {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    /**
     * 判断是否meizu手机
     *
     * @return
     */
    public static boolean isMeizu() {
        return Build.BRAND.equals("Meizu");
    }

    /**
     * 获取魅族手机底部虚拟键盘高度
     *
     * @return
     */
    public static int getSmartBarHeight() {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("mz_action_button_min_height");
            int height = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
