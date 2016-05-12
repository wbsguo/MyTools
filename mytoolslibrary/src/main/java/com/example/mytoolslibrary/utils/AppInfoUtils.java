package com.example.mytoolslibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * 获取屏幕宽度
 * 获取屏幕高度
 * 获取屏幕密度
 * 获取应用名称
 * 获取应用包名
 * 获取应用版本名称
 * 判断本机是否安装了指定包名对应的应用
 * 获取应用版本
 * 获取手机mac地址
 * 获取product
 * 获取设备系统版本
 * 获取设备使用的语言
 * 获取设备所在的国家地区 
 * 获取设备build模式
 * 获取设备唯一标示（IMEI）
 * 获取设备唯一标示（AndroidId）
 */
public class AppInfoUtils {
	private static AppInfoUtils instance;
	public static AppInfoUtils getInstance() {
		if (instance == null) {
			instance = new AppInfoUtils();
		}
		return instance;
	}
	/** 获取屏幕宽度 */
	private int SCREEN_WIDTH = 0;
	public int getScreenWidth(Activity activity) {
		if (SCREEN_WIDTH == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			SCREEN_WIDTH = dm.widthPixels;
		}
		return SCREEN_WIDTH;
	}
	/** 获取屏幕高度 */
	private int SCREEN_HEIGHT = 0;
	public int getScreenHeight(Activity activity) {
		if (SCREEN_HEIGHT == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			SCREEN_HEIGHT = dm.heightPixels;
		}
		return SCREEN_HEIGHT;
	}
	/** 获取屏幕密度 */
	private float SCREEN_DENSITY = 0;
	public float getScreenDensity(Activity activity) {
		if (SCREEN_DENSITY == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			SCREEN_DENSITY = dm.density;
		}
		return SCREEN_DENSITY;
	}
	/** 获取应用名称 */
	public String getAppName(Context context) {
		try {
			ApplicationInfo applicationInfo = context.getPackageManager()
					.getApplicationInfo(getPageName(context),
							PackageManager.GET_META_DATA);
			return context.getPackageManager()
					.getApplicationLabel(applicationInfo).toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
	/** 获取应用包名 */
	public String getPageName(Context context) {
		return context.getApplicationContext().getPackageName();
	}
	/** 获取应用版本名称 */
	public String getAppVersionName(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(getPageName(context),
							ApplicationInfo.FLAG_SYSTEM);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
	/** 获取应用版本 */
	public int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(getPageName(context),
							ApplicationInfo.FLAG_SYSTEM);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 判断本机是否安装了指定包名对应的应用
	 * @param packageName 指定包名
	 * */
	public boolean apkInstalled(Context context, String packageName) {
		PackageInfo packageInfo = null;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					packageName, 0);
			if (packageInfo != null) {
				packageInfo = null;
				return true;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			packageInfo = null;
		}
		return false;
	}
	/** 获取手机mac地址 */
	public String getMac(Context context) {
		String MAC_ADDRESS="";
		WifiManager wifiMgr = null;
		WifiInfo info = null;
		try {
			wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
			if (null != info) {
				MAC_ADDRESS = info.getMacAddress();
				MAC_ADDRESS = MAC_ADDRESS.replaceAll(":", "-");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			wifiMgr = null;
			info = null;
		}
		return MAC_ADDRESS;
	}
	/** 获取product */
	public String getProduct() {
		return Build.PRODUCT;
	}
	/** 获取设备系统版本 */
	public String getSysVersion() {
		return Build.VERSION.RELEASE;
	}
	/** 获取设备使用的语言 */
	public String getLanguage() {
		return Locale.getDefault().getLanguage();
	}
	/** 获取设备所在的国家地区 */
	public String getLocale() {
		return Locale.getDefault().getCountry();
	}
	/** 获取设备build模式 Device*/
	public String getModel() {
		return Build.MODEL;
	}
	/** 获取设备唯一标示（IMEI） */
	public String getIMEI(Context context) {
		String IMEI="";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (telephonyManager != null) {
				IMEI = telephonyManager.getDeviceId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return IMEI;
	}
	/** 获取设备唯一标示（AndroidId） */
	public String getAndroidId(Context context) {
		return Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ANDROID_ID);
	}
}
