package com.example.android.midnight_rajat.user.admin.FoodDataTab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.midnight_rajat.R;
import com.example.android.midnight_rajat.fooddata.FoodDetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static android.R.attr.path;

/**
 * Created by rajat on 20-01-2017.
 */

public class ShowItemsAdapter extends ArrayAdapter<FoodDetails> {

    public ShowItemsAdapter(Context context, ArrayList<FoodDetails> storage) {
        super(context, 0, storage);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater customInflater = LayoutInflater.from(getContext());

            convertView = customInflater.inflate(R.layout.custom_view_food_item, parent, false);
        }
        FoodDetails foodItem = getItem(position);
        /*
        getting the view id's
         */
        ImageView ifoodimage = (ImageView) convertView.findViewById(R.id.listimage);
        TextView ifoodName = (TextView) convertView.findViewById(R.id.listname);
        TextView ifoodPrice = (TextView) convertView.findViewById(R.id.selectedprice);

        try {
            if (foodItem.getImagePath().equals("")){
                ifoodimage.setImageResource(R.drawable.image_not_available_14);
            }
            else {
                File f=new File(foodItem.getImagePath());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

                ifoodimage.setImageBitmap(b);
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        ifoodName.setText(foodItem.getFoodName());
        ifoodPrice.setText(Integer.toString(foodItem.getPrice()));
        return convertView;
    }

}
