package com.example.mytoolslibrary.Constant;

/**
 * Created by wangbs on 16/5/3.
 */
public class SysTableConstant {
    public static final String T2_FOOD = "food";//吃食物表
    public static final String objectId = "objId";//唯一字段
    public static final String createdBy = "createdBy";//创建人
    public static final String day = "day";//食物的日期,int,比如：yyyymmdd
    public static final String eatTime = "eatTime";//int;1:早;2:中;3:晚;4:间
    public static final String ficationId = "ficationId";//分类id,二级分类id,String类型
    public static final String foodId = "foodId";//具体食物的id
    public static final String foodName = "foodName";//食物名字,String
    //注:一般食，外食，运动id是纯数字，自定义食物我们用的parser的ObjectId,有字母
    public static final String foodType = "foodType";//食物类型,int值；1:一般食;2:外食;3:运动;4:自定义
    public static final String kcal = "kcal";//用户调节后获取的卡路里即吃食物的kcal，int，98kcal
    public static final String num = "num";//用户调节的值,int,比如：98
    public static final String sectionImage = "sectionImage";//食物分组图片,String
    public static final String unitKcal = "unitKcal";//单位卡路里即食物本身的kcal,int,100kcal
    public static final String unitType = "unitType";//单位类型,String;比如%
    public static final String delFlag = "delFlag";//int;0:未删除数据;1删除了的数据
    public static final String updataAt = "updateAt";//更新时间
    public static final String reserve1 = "reserve1";//预留字段
}
