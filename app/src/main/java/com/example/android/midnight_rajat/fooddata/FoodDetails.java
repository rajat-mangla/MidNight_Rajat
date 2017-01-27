package com.example.android.midnight_rajat.fooddata;

import java.io.Serializable;

/**
 * Created by rajat on 20-01-2017.
 */


public class FoodDetails implements Serializable {
    private int price;
    private String foodName;
    private int foodImage;
    private int foodQuantity;


    public FoodDetails(String mFoodName, int mPrice, int mFoodImage) {
        price = mPrice;
        foodName = mFoodName;
        foodImage = mFoodImage;
        foodQuantity = 0;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
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

    public int getFoodImage() {
        return foodImage;
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


