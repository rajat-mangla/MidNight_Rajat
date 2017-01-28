package com.example.android.midnight_rajat.fooddata.localstorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.midnight_rajat.fooddata.localstorage.FoodReader;

/**
 * Created by rajat on 26-01-2017.
 */

public class SqlData extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FoodReader.FoodEntry.TABLE_NAME + " (" +
                    FoodReader.FoodEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodReader.FoodEntry.COLUMN_FOOD_NAME + " TEXT," +
                    FoodReader.FoodEntry.COLUMN_FOOD_PRICE + " INTEGER," + FoodReader.FoodEntry.COLUMN_FOOD_IMG_PATH + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FoodReader.FoodEntry.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FoodData.db";


    /*
     * constructor ....
     */
    public SqlData(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);

    }

    public boolean insertFoodDetail(String foodName,int foodPrice,int index,String imgPath){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FoodReader.FoodEntry.COLUMN_FOOD_NAME,foodName);
        contentValues.put(FoodReader.FoodEntry.COLUMN_FOOD_PRICE,foodPrice);
        contentValues.put(FoodReader.FoodEntry._ID,index);
        contentValues.put(FoodReader.FoodEntry.COLUMN_FOOD_IMG_PATH,imgPath);
        long rowId = sqLiteDatabase.insert(FoodReader.FoodEntry.TABLE_NAME,null,contentValues);
        if (rowId == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean updateFoodDetail(String foodName,int foodPrice,int index,String imgPath){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FoodReader.FoodEntry.COLUMN_FOOD_NAME,foodName);
        contentValues.put(FoodReader.FoodEntry.COLUMN_FOOD_PRICE,foodPrice);
        contentValues.put(FoodReader.FoodEntry._ID,index);
        contentValues.put(FoodReader.FoodEntry.COLUMN_FOOD_IMG_PATH,imgPath);

        long rowId = sqLiteDatabase.update(FoodReader.FoodEntry.TABLE_NAME,contentValues,FoodReader.FoodEntry._ID +" = ?",new String[] {Integer.toString(index)});
        if (rowId == -1){
            return false;
        }
        return true;
    }

    public Cursor getCursorFoodData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor demo = sqLiteDatabase.rawQuery(" select * from "+ FoodReader.FoodEntry.TABLE_NAME,null);
        return demo;
    }
}

