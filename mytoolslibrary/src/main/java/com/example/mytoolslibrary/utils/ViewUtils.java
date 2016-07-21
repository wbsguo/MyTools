package com.example.mytoolslibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;

/**
 * 监测是否拥有某权限
 * 获取某权限
 * 检测网络连接
 * 检测网络连接是否是wifi
 * Created by wangbs on 16/5/16.
 */
public class ViewUtils {
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private static final String TAG="ViewUtils";
    private static ViewUtils tools;
    public static ViewUtils getInstance() {
        if (tools == null) {
            tools = new ViewUtils();
        }
        return tools;
    }
    /**
     * 监测是否拥有某权限
     * @param activity
     * @param permission
     * @return
     */
    public boolean checkPermission(final Activity activity, final String permission) {
        int storagePermission = ActivityCompat.checkSelfPermission(activity, permission);
        if (storagePermission == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取某权限
     * @param activity
     * @param permission
     */
    public void showPermissionDialog(final Activity activity, String permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_CODE_ASK_PERMISSIONS);
    }
    // 检测网络连接
    public static boolean checkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getTypeName().equals("WIFI")) {
            return true;
        }
        return false;
    }
}
