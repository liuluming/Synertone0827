package com.my51c.see51.yzxvoip;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络工具类
 *
 * @author xiaozhenhua
 */
public class NetWorkTools {

    /**
     * 无网络联接
     */
    public static final int NETWORK_ON = 0;
    /**
     * WIFI网络
     */
    public static final int NETWORK_WIFI = 1;
    /**
     * 2G网络(包含:2.75G  2.5G 2G)
     */
    public static final int NETWORK_EDGE = 2;
    /**
     * 3G网络(包含:3G  3.5G  3.75G)
     */
    public static final int NETWORK_3G = 3;

    /**
     * 获取当前的网络类型
     *
     * @param
     * @author: xiaozhenhua
     * @data:2014-4-9 下午3:15:07
     */
    public static int getCurrentNetWorkType(Context mContext) {
        int currentNetWorkType = NETWORK_ON;
        NetworkInfo activeNetInfo = getNetworkInfo(mContext);
        int netSubtype = -1;
        if (activeNetInfo != null) {
            netSubtype = activeNetInfo.getSubtype();
        }
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            if ("WIFI".equalsIgnoreCase(activeNetInfo.getTypeName())) {
                currentNetWorkType = NETWORK_WIFI;
            } else if (activeNetInfo.getTypeName() != null
                    && activeNetInfo.getTypeName().toLowerCase().contains("mobile")) {// 3g,双卡手机有时为mobile2
                if (netSubtype == TelephonyManager.NETWORK_TYPE_UMTS
                        || netSubtype == TelephonyManager.NETWORK_TYPE_EVDO_0
                        || netSubtype == TelephonyManager.NETWORK_TYPE_EVDO_A
                        || netSubtype == TelephonyManager.NETWORK_TYPE_EVDO_B
                        || netSubtype == TelephonyManager.NETWORK_TYPE_EHRPD
                        || netSubtype == TelephonyManager.NETWORK_TYPE_HSDPA
                        || netSubtype == TelephonyManager.NETWORK_TYPE_HSUPA
                        || netSubtype == TelephonyManager.NETWORK_TYPE_HSPA
                        || netSubtype == TelephonyManager.NETWORK_TYPE_LTE
                        // 4.0系统 H+网络为15 TelephonyManager.NETWORK_TYPE_HSPAP
                        || netSubtype == 15) {
                    currentNetWorkType = NETWORK_3G;
                } else {
                    currentNetWorkType = NETWORK_EDGE;
                }
            }
        }
        return currentNetWorkType;
    }

    private static NetworkInfo getNetworkInfo(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    public static boolean isNetWorkConnect(Context mContext) {
        NetworkInfo activeNetInfo = getNetworkInfo(mContext);
        return (activeNetInfo != null && activeNetInfo.isConnected());
    }




    /**
     * 当前是否wifi
     *
     * @param mcontext
     * @return
     * @author: xiaozhenhua
     * @data:2014-10-17 下午5:12:17
     */
    public static boolean isConnectWifi(Context mcontext) {
        ConnectivityManager connMng = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInf = connMng.getActiveNetworkInfo();
        return netInf != null && "WIFI".equalsIgnoreCase(netInf.getTypeName());
    }
}
