package com.example.mytoolslibrary.utils;

import android.app.Activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Activity的管理器，用map集合可以指定finish某个activity
 * @author Administrator
 *
 */
public class ActivitysUtils {
	private static final String TAG = "ActivitysUtils";
	private HashMap<String, Activity> acHashMap = new HashMap<String, Activity>();
	private static ActivitysUtils activitys;
	public static ActivitysUtils getInstance() {
		if (activitys == null) {
			activitys = new ActivitysUtils();
		}
		return activitys;
	}
	public Activity getActivity(String tag){
		if(acHashMap.containsKey(tag)){
			return acHashMap.get(tag);
		}else {
			return null;
		}
	}
	public void addActivity(String tag, Activity activity) {
		//重复的会自动覆盖以前的
		acHashMap.put(tag, activity);
	}
	public void finshActivity(List<String> activitys){
		if(activitys.size()>0){
			for(String string:activitys){
				if(acHashMap.containsKey(string)){
					acHashMap.get(string).finish();
				}
			}
			for(String string:activitys){
				if(acHashMap.containsKey(string)){
					acHashMap.remove(string);
				}
			}
		}
	}
	public void destory() {
		Iterator iter = acHashMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			Activity activity = (Activity) entry.getValue();
			activity.finish();
		}
		acHashMap.clear();
	}
}
