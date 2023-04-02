package com.powernode.mybatis.POJO;
//封装汽车相关信息的POJO类，普通的java类

public class Car {
    //成员变量应该与数据库表的字段名对应
    //采用包装类，数据库有些字段的值null，防止出息空指针异常。
    private  Long id;
    private String carNum;
    private String brand;
    private Double guidePrice;
    private String produceTime;
    private String catType;

    public Car(Long id, String carNum, String brand, Double guidePrice, String produceTime, String catType) {
        this.id = id;
        this.carNum = carNum;
        this.brand = brand;
        this.guidePrice = guidePrice;
        this.produceTime = produceTime;
        this.catType = catType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Double guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(String produceTime) {
        this.produceTime = produceTime;
    }

    public String getCatType() {
        return catType;
    }

    public void setCatType(String catType) {
        this.catType = catType;
    }

    public Car() {
    }
}
