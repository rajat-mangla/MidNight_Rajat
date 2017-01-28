package com.example.android.midnight_rajat.fooddata.localstorage;

import android.provider.BaseColumns;

/**
 * Created by rajat on 26-01-2017.
 */

public final class FoodReader{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FoodReader() {
    }

    /* Inner class that defines the table contents */
    public static class FoodEntry implements BaseColumns {
        public static final String TABLE_NAME = "FoodDetails";
        public static final String COLUMN_FOOD_NAME = "FoodName";
        public static final String COLUMN_FOOD_PRICE = "FoodPrice";
        public static final String COLUMN_FOOD_IMG_PATH = "FoodImgPath";
    }
}
