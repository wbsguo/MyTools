package com.example.mytoolslibrary.utils;

import android.util.Log;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by wangbs on 16/4/22.
 * JSONArray的remove方法是API19以上,此接口兼容低版本
 */
public class UtileTools {
    private static final String TAG="UtileTools";
    private static UtileTools tools;
    public static UtileTools getInstance() {
        if (tools == null) {
            tools = new UtileTools();
        }
        return tools;
    }
    /**
     * remove JSONArray的remove方法是API19以上,此接口兼容低版本
     * @param indext
     * @param jsonArray
     */
    private void JSONArrayRemove(int indext, JSONArray jsonArray){
        try {
            if(indext < 0)
                return;
            Field valuesField=JSONArray.class.getDeclaredField("values");
            valuesField.setAccessible(true);
            List<Object> values=(List<Object>)valuesField.get(jsonArray);
            if(indext >= values.size())
                return;
            values.remove(indext);
        } catch (NoSuchFieldException e) {
            Log.e(TAG,"JSONArray_remove-NoSuchFieldException"+e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e(TAG,"JSONArray_remove-IllegalAccessException"+e.getMessage());
            e.printStackTrace();
        }
    }
}
