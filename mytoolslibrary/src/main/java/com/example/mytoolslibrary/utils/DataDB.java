package com.example.mytoolslibrary.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mytoolslibrary.Constant.SysTableConstant;
import com.example.mytoolslibrary.model.FoodBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbs on 16/4/19.
 */
public class DataDB extends SQLiteOpenHelper {
    private static final String TAG = "DataDB";
    private Context context;
    private static final int VERSION = 1;
    //数据库名
    private static String DB_NAME = "weight_data";
    private static final String SQLite_Name = "MusicwaveDB";
    /**
     * 表的id
     */
    private static final String id = "id";
    private static final String music_table = "music_table";
    private static final String music_id = "music_id";
    private static final String music_source = "music_source";

    public DataDB(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String T2_FOOD_SQL = "create table " + SysTableConstant.T2_FOOD
                + "("
                + id + " INTEGER primary key autoincrement,"
                + SysTableConstant.objectId + " TEXT,"
                + SysTableConstant.createdBy + " TEXT,"
                + SysTableConstant.kcal + " DOUBLE,"
                + SysTableConstant.unitKcal + " DOUBLE,"
                + SysTableConstant.num + " INTEGER,"
                + SysTableConstant.day + " INTEGER,"
                + SysTableConstant.unitType + " TEXT,"
                + SysTableConstant.eatTime + " INTEGER,"
                + SysTableConstant.foodName + " TEXT,"
                + SysTableConstant.sectionImage + " TEXT,"
                + SysTableConstant.foodType + " INTEGER,"
                + SysTableConstant.ficationId + " TEXT,"
                + SysTableConstant.foodId + " TEXT,"
                + SysTableConstant.delFlag + " INTEGER,"
                + SysTableConstant.updataAt + " DOUBLE,"
                + SysTableConstant.reserve1 + " TEXT"
                + ")";
        db.execSQL(T2_FOOD_SQL);
        String musicSql = "create table " + music_table
                + "("
                + id + " integer primary key autoincrement,"
                + music_id + " TEXT,"
                + music_source + " integer"
                + ")";
        db.execSQL(musicSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    /*===================T2_FOOD====================*/
    private boolean isFoodExit(String objectIdValue) {
        synchronized (DataDB.class) {
            SQLiteDatabase db = null;
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    String where = SysTableConstant.objectId + "='" + objectIdValue + "'";
                    Cursor nCursor = null;
                    nCursor = db.query(SysTableConstant.T2_FOOD, null, where,
                            null, null, null, null);
                    db.setTransactionSuccessful();
                    if (nCursor != null && nCursor.getCount() > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                if (null != db) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
        }
    }

    public void addFoodData(FoodBean foodBean, String userId) {
        synchronized (DataDB.class) {
            SQLiteDatabase db = null;
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    db.insert(SysTableConstant.T2_FOOD,null,addFoodContentValues(foodBean,userId));
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != db) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
        }
    }
    public void isExitaddFoodData(FoodBean foodBean, String userId){
        if(isFoodExit(foodBean.getObjectId())){
            updataFood(foodBean,userId);
        }else{
            addFoodData(foodBean,userId);
        }
    }
    public void addFoodData(List<FoodBean> foodBeans, String userId) {
        synchronized (DataDB.class) {
            SQLiteDatabase db = null;
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    for (FoodBean foodBean : foodBeans) {
                        db.insert(SysTableConstant.T2_FOOD,null,addFoodContentValues(foodBean,userId));
                    }
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != db) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
        }
    }

    public void updataFood(List<FoodBean> foodBeans, String userId) {
        for (FoodBean foodBean : foodBeans) {
            if (isFoodExit(foodBean.getObjectId())) {
                addFoodData(foodBean, userId);
            } else {
                updataFood(foodBean, userId);
            }
        }
    }
    private ContentValues updataFoodContentValues(FoodBean foodBean, String userId){
        ContentValues cv = new ContentValues();
        cv.put(SysTableConstant.createdBy, userId);
        cv.put(SysTableConstant.kcal, foodBean.getEatKcal());
        cv.put(SysTableConstant.unitKcal, foodBean.getFoodKcal());
        cv.put(SysTableConstant.num, foodBean.getNum());
        cv.put(SysTableConstant.day, foodBean.getEatDay());
        cv.put(SysTableConstant.unitType, foodBean.getUnitType());
        cv.put(SysTableConstant.eatTime, foodBean.getEatTime());
        cv.put(SysTableConstant.foodName, foodBean.getFoodName());
        cv.put(SysTableConstant.sectionImage, foodBean.getFicationImag());
        cv.put(SysTableConstant.foodType, foodBean.getFoodType());
        cv.put(SysTableConstant.ficationId, foodBean.getFicationId());
        cv.put(SysTableConstant.foodId, foodBean.getFoodId());
        cv.put(SysTableConstant.updataAt, foodBean.getLastUpdateTime());
        cv.put(SysTableConstant.delFlag, foodBean.getDelFlag());
        return cv;
    }
    private ContentValues addFoodContentValues(FoodBean foodBean, String userId){
        ContentValues cv=updataFoodContentValues(foodBean,userId);
        cv.put(SysTableConstant.objectId, foodBean.getObjectId());
        cv.put(SysTableConstant.reserve1, "");
        return cv;
    }
    public void updataFood(FoodBean foodBean, String userId) {
        synchronized (DataDB.class) {
            SQLiteDatabase db = null;
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    String where = SysTableConstant.objectId + " = ?";
                    String[] whereValue = {foodBean.getObjectId()};
                    db.update(SysTableConstant.T2_FOOD, updataFoodContentValues(foodBean,userId), where, whereValue);
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != db) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
        }
    }

    /**
     * @param dayValue 格式:20160203
     * @return
     */
    public List<FoodBean> getFood(int dayValue) {
        synchronized (DataDB.class) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                List<FoodBean> foodBeans = new ArrayList<>();
                if (db.isOpen()) {
                    db.beginTransaction();
                    String sql = "select * from " + SysTableConstant.T2_FOOD
                            + " where " + SysTableConstant.day + " = " + dayValue + " and " + SysTableConstant.delFlag + "=0";
                    cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        foodBeans.add(getFoodCursor(cursor));
                    }
                    db.setTransactionSuccessful();
                }
                return foodBeans;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
        }
    }
    private FoodBean getFoodCursor(Cursor cursor){
        FoodBean foodBean = new FoodBean();
        String eatIdValue = cursor.getString(cursor.getColumnIndex(SysTableConstant.objectId));
        foodBean.setObjectId(eatIdValue);
        double kcalValue = cursor.getDouble(cursor.getColumnIndex(SysTableConstant.kcal));
        foodBean.setEatKcal(kcalValue);
        double unitKcalValue = cursor.getDouble(cursor.getColumnIndex(SysTableConstant.unitKcal));
        foodBean.setFoodKcal(unitKcalValue);
        int numValue = cursor.getInt(cursor.getColumnIndex(SysTableConstant.num));
        foodBean.setNum(numValue);
        int dayValue = cursor.getInt(cursor.getColumnIndex(SysTableConstant.day));
        foodBean.setEatDay(dayValue);
        String unitTypeValue = cursor.getString(cursor.getColumnIndex(SysTableConstant.unitType));
        foodBean.setUnitType(unitTypeValue);
        int eatTimeValue = cursor.getInt(cursor.getColumnIndex(SysTableConstant.eatTime));
        foodBean.setEatTime(eatTimeValue);
        String foodNameValue = cursor.getString(cursor.getColumnIndex(SysTableConstant.foodName));
        foodBean.setFoodName(foodNameValue);
        String sectionImageValue = cursor.getString(cursor.getColumnIndex(SysTableConstant.sectionImage));
        foodBean.setFicationImag(sectionImageValue);
        int foodTypeValue = cursor.getInt(cursor.getColumnIndex(SysTableConstant.foodType));
        foodBean.setFoodType(foodTypeValue);
        String ficationIdValue = cursor.getString(cursor.getColumnIndex(SysTableConstant.ficationId));
        foodBean.setFicationId(ficationIdValue);
        String foodIdValue = cursor.getString(cursor.getColumnIndex(SysTableConstant.foodId));
        foodBean.setFoodId(foodIdValue);
        double updataAtValue = cursor.getDouble(cursor.getColumnIndex(SysTableConstant.updataAt));
        foodBean.setLastUpdateTime(updataAtValue);
        return foodBean;
    }
    public List<FoodBean> getFood(int dayValue, int eatTimeValue) {
        synchronized (DataDB.class) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                List<FoodBean> foodBeans = new ArrayList<>();
                if (db.isOpen()) {
                    db.beginTransaction();
                    String sql = "select * from " + SysTableConstant.T2_FOOD
                            + " where " + SysTableConstant.day + " = " + dayValue + " and " + SysTableConstant.delFlag + "=0" + " and " + SysTableConstant.eatTime + " = " + eatTimeValue;
                    cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        foodBeans.add(getFoodCursor(cursor));
                    }
                    db.setTransactionSuccessful();
                }
                return foodBeans;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
        }
    }

    /**
     * sum语句,累加值
     * @param dayValue
     * @param eatTimeValue
     * @return
     */
    public double getFoodAllKcal(int dayValue, int eatTimeValue) {
        synchronized (DataDB.class) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            double sum = 0;
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    String[] c = {"sum(" + SysTableConstant.kcal + ")"};
                    cursor = db.query(SysTableConstant.T2_FOOD, c, SysTableConstant.day + " = " + dayValue + " and " + SysTableConstant.delFlag + "=0 and " + SysTableConstant.eatTime + "=" + eatTimeValue, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        sum = cursor.getDouble(0);
                    }
                    db.setTransactionSuccessful();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
            return sum;
        }
    }

    public double getFoodAllKcal(int dayValue) {
        synchronized (DataDB.class) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            double sum = 0;
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    String[] c = {"sum(" + SysTableConstant.kcal + ")"};
                    cursor = db.query(SysTableConstant.T2_FOOD, c, SysTableConstant.day + " = " + dayValue + " and " + SysTableConstant.delFlag + "=0", null, null, null, null);
                    if (cursor.moveToFirst()) {
                        sum = cursor.getDouble(0);
                    }
                    db.setTransactionSuccessful();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
            return sum;
        }
    }

    public List<FoodBean> getFood() {
        synchronized (DataDB.class) {
            List<FoodBean> foodBeans = new ArrayList<>();
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    String sql = "select * from " + SysTableConstant.T2_FOOD
                            + " where " + SysTableConstant.delFlag + "=0";
                    cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        foodBeans.add(getFoodCursor(cursor));
                    }
                    db.setTransactionSuccessful();
                }
                return foodBeans;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
        }
    }

    /*===================T2_FOOD====================*/

    //1.数据库没得记录2.有记录,但记录体重都是0;有记录,有体重大于0
    public double getLatelyValue2(String columnName) {
        synchronized (DataDB.class) {
            double weightDouble = 0;
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    String sql = "select * from " + SysTableConstant.T2_FOOD
                            + " where " + SysTableConstant.delFlag + "=0" + " order by " + SysTableConstant.day + " DESC";
                    cursor = db.rawQuery(sql, null);
                    do {
                        if (cursor.moveToNext()) {
                            weightDouble = cursor.getDouble(cursor.getColumnIndex(columnName));
                        } else {
                            break;
                        }
                    } while (weightDouble <= 0);
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
            return weightDouble;
        }
    }

    //1.数据库没得记录2.有记录,但记录体重都是0;有记录,有体重大于0

    /**
     * 获取某条数据库里某个大于0的值
     * @param columnName
     * @return
     */
    public double getLatelyValue(String columnName) {
        synchronized (DataDB.class) {
            double doubleValue = 0;
            SQLiteDatabase db = null;
            Cursor cursor = null;
            db = this.getWritableDatabase();
            try {
                if (db.isOpen()) {
                    db.beginTransaction();
                    String sql = "select * from " + SysTableConstant.T2_FOOD
                            + " where " + SysTableConstant.delFlag + "=0" + " and " + columnName + ">0 " + " order by " + SysTableConstant.day + " DESC";
                    cursor = db.rawQuery(sql, null);
                    if (cursor.moveToNext()) {
                        doubleValue = cursor.getDouble(cursor.getColumnIndex(columnName));
                    }
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
            return doubleValue;
        }
    }

    /**
     * 删除表数据
     */
    public void deleteDataAll(){
        synchronized (DataDB.class) {
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    String sql="delete from "+SysTableConstant.T2_FOOD;
                    db.execSQL(sql);
                    db.setTransactionSuccessful();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (null != db) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
        }
    }

    /**
     * 删除表
     */
    public void deleteAll(){
        synchronized (DataDB.class) {
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    String sql="DROP TABLE "+SysTableConstant.T2_FOOD;
                    db.execSQL(sql);
                    db.setTransactionSuccessful();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (null != db) {
                    if (db.isOpen()) {
                        db.endTransaction();
                    }
                    db.close();
                }
            }
        }
    }
    public void addMusicTable(String musicId, int source) {
        synchronized (DataDB.class) {
            SQLiteDatabase db = null;
            try {
                db = this.getWritableDatabase();
                if (db.isOpen()) {
                    db.beginTransaction();
                    Object[] obj = {musicId, source};
                    String sql = "insert into " + music_table
                            + " ("
                            + music_id + ","
                            + music_source
                            + ") "
                            + "values (?,?)";
                    db.execSQL(sql, obj);
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != db) {
                    if(db.isOpen()){
                        db.endTransaction();
                    }
                    db.close();
                }
            }
        }
    }
    /**
     * 根据两个字段删除
     *
     * @param music_idValue
     * @param source
     */
    public void deleteMusic(String music_idValue, int source) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = music_id + " = ?" + " and " + music_source + "=?";
        String[] whereValue = new String[]{music_idValue, source + ""};
        db.delete(music_table, where, whereValue);
        db.close();
    }

    /**
     * @return 音乐总条数
     */
    public long getMusicCount(int source) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select count(*) from " + music_table + " where " + music_source + "=" + source;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    /**
     * 模糊搜索,歌手名
     */
    public void getkeyList_music_author(String searchValue, int source) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + music_table + " where " + music_id + " like'%" + searchValue + "%'" + " and " + music_source + "=" + source;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String musicIdValue = cursor.getString(cursor.getColumnIndex(music_id));
        }
        cursor.close();
        db.close();
    }
    /**
     * 排序-降序
     *
     * @param source
     */
    public void getMusicHistory(int source) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "";
        sql = "select * from " + music_table + " where " + music_source + "=" + source + " order by id DESC";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
        }
        cursor.close();
        db.close();
    }

    /**
     * 排序-升序
     *
     * @param source
     */
    public void getMusicPaixu(int source) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "";
        sql = "select * from " + music_table + " where " + music_source + "=" + source + " order by id ASC";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
        }
        cursor.close();
        db.close();
    }
    /**
     * 查询某个字段的一部分值
     *
     * @param src
     * @param source
     */
    public void getNeedAddMusicLists(List<String> src, int source) {
        SQLiteDatabase db = this.getWritableDatabase();
        String ids = "";
        for (int i = 0; i < src.size(); i++) {
            String id = src.get(i);
            if (i == 0) {
                ids += id;
            } else {
                ids += "," + id;
            }
        }
        String sql = "select * from " + music_table
                + " where " + music_id + " in(" + ids + ")"
                + " and " + music_source + "=" + source;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

        }
    }
    /**
     * 更改表名
     *
     * @return
     */
    public boolean updataTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db.isOpen()) {
                String sql = "ALTER TABLE TB_SHENTI_QINGBAO RENAME TO BODY_INFO";
                db.execSQL(sql);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    /**
     * 增加字段
     *
     * @return
     */
    public boolean addTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db.isOpen()) {
                String sql = "ALTER TABLE 表名  ADD COLUMN 新的字段名 BLOB(类型)";
                db.execSQL(sql);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }
    /**
     * 删除数据库
     */
    public void deleteDB() {
        boolean flag = context.deleteDatabase(SQLite_Name);
    }

    public void isexitDB() {
        File file = new File(SQLite_Name);
    }

    /**
     * 方法2：检查表中某列是否存在
     *
     * @param db
     * @param tableName  表名
     * @param columnName 列名
     * @return
     */
    private boolean checkColumnExists2(SQLiteDatabase db, String tableName
            , String columnName) {
        boolean result = false;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from sqlite_master where name = ? and sql like ?"
                    , new String[]{tableName, "%" + columnName + "%"});
            result = null != cursor && cursor.moveToFirst();
        } catch (Exception e) {
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return result;
    }
    /**
     * 方法1：检查某表列是否存在
     *
     * @param db
     * @param tableName  表名
     * @param columnName 列名
     * @return
     */
    private boolean checkColumnExist1(SQLiteDatabase db, String tableName
            , String columnName) {
        boolean result = false;
        Cursor cursor = null;
        try {
            //查询一行
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0"
                    , null);
            result = cursor != null && cursor.getColumnIndex(columnName) != -1;
        } catch (Exception e) {
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }
}
