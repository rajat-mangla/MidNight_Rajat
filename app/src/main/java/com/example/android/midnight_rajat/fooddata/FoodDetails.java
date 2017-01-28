package com.example.android.midnight_rajat.fooddata;

import java.io.Serializable;

/**
 * Created by rajat on 20-01-2017.
 */


public class FoodDetails implements Serializable {
    private int price;
    private String foodName;
    private int foodQuantity;
    private String imagePath;

    public FoodDetails(String mFoodName, int mPrice,String imgPath) {
        price = mPrice;
        foodName = mFoodName;
        foodQuantity = 0;
        imagePath=imgPath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int i) {
        foodQuantity = i;
    }
}


