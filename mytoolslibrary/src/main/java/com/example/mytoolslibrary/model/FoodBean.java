package com.example.mytoolslibrary.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 食物
 *
 * @author Administrator
 */
public class FoodBean implements Serializable {
    private String objectId;//唯一标识
    private String foodId = "";//食物id,自定义食物没有食物id,只有objectId哈
    private String ficationId;//分类id
    private String ficationName;//分类名
    private String ficationImag = "";//分类图片
    private double foodKcal;//食物kcal值,
    private double metsKcal;//运动的mets
    private double eatKcal;//食物吃的kcal值,
    private String foodImag;//食物图片
    private String foodName = "";//食物名
    private String foodDescript = "";//食物描述
    private int heard_id;
    private int foodType;//食物类型,int值；1:一般食;2:外食;3:运动;4:自定义
    private int eatDay;//吃食物的日期
    private int eatTime;//1:早;2:中;3:晚;4:间;5:运动
    private int num;//用户调节的值
    private String unitType;
    private double lastUpdateTime;//最近一次更新时间
    private int delFlag;//是否删除的标示

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFicationId() {
        return ficationId;
    }

    public void setFicationId(String ficationId) {
        this.ficationId = ficationId;
    }

    public String getFicationImag() {
        return ficationImag;
    }

    public void setFicationImag(String ficationImag) {
        this.ficationImag = ficationImag;
    }

    public double getFoodKcal() {
        return foodKcal;
    }

    public void setFoodKcal(double foodKcal) {
        this.foodKcal = foodKcal;
    }

    public String getFoodImag() {
        return foodImag;
    }

    public void setFoodImag(String foodImag) {
        this.foodImag = foodImag;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescript() {
        return foodDescript;
    }

    public void setFoodDescript(String foodDescript) {
        this.foodDescript = foodDescript;
    }

    public int getHeard_id() {
        return heard_id;
    }

    public void setHeard_id(int heard_id) {
        this.heard_id = heard_id;
    }

    public String getFicationName() {
        return ficationName;
    }

    public void setFicationName(String ficationName) {
        this.ficationName = ficationName;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public int getFoodType() {
        return foodType;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

    public int getEatDay() {
        return eatDay;
    }

    public void setEatDay(int eatDay) {
        this.eatDay = eatDay;
    }

    public int getEatTime() {
        return eatTime;
    }

    public void setEatTime(int eatTime) {
        this.eatTime = eatTime;
    }

    public double getEatKcal() {
        return eatKcal;
    }

    public void setEatKcal(double eatKcal) {
        this.eatKcal = eatKcal;
    }

    public double getMetsKcal() {
        return metsKcal;
    }

    public void setMetsKcal(double metsKcal) {
        this.metsKcal = metsKcal;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public double getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(double lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public static class foodComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            FoodBean s1 = (FoodBean) o1;
            FoodBean s2 = (FoodBean) o2;
            int result = s2.eatDay > s1.eatDay ? 1 : (s2.eatDay == s1.eatDay ? 0 : -1);
            return result;
        }
    }

    @Override
    public String toString() {
        return "FoodBean{" +
                "objectId='" + objectId + '\'' +
                ", ficationId='" + ficationId + '\'' +
                ", ficationName='" + ficationName + '\'' +
                ", foodName='" + foodName + '\'' +
                ", foodType=" + foodType +
                ", delFlag=" + delFlag +
                ", lastUpdateTime=" + lastUpdateTime +
                '}' + "\n";
    }
}
