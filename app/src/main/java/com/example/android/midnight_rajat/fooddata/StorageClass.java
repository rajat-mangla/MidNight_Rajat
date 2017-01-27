package com.example.android.midnight_rajat.fooddata;

import java.util.ArrayList;

/**
 * Created by rajat on 20-01-2017.
 */

public class StorageClass {
    private static ArrayList<FoodDetails> foodItems;
    private static ArrayList<FoodDetails> foodCart;

    public StorageClass() {}

    public void setCatalogData(FoodDetails item) {
        foodItems.add(item);
    }

    public ArrayList<FoodDetails> getCatalogData() {
        if (foodItems == null) {
            foodItems = new ArrayList<>();
        }
        return foodItems;
    }

    public ArrayList<FoodDetails> getFoodCart() {
        if (foodCart == null) {
            foodCart = new ArrayList<>();
        }
        return foodCart;
    }
}
