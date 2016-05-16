package com.example.mytoolslibrary.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * 监测是否拥有某权限
 * 获取某权限
 * Created by wangbs on 16/5/16.
 */
public class ViewUtils {

    public static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

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
}
