package com.example.android.midnight_rajat.user.admin;

import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.android.midnight_rajat.R;
import com.example.android.midnight_rajat.fooddata.FoodDetails;
import com.example.android.midnight_rajat.fooddata.localstorage.SqlData;
import com.example.android.midnight_rajat.fooddata.StorageClass;
import com.example.android.midnight_rajat.user.admin.FoodDataTab.ShowItemsFragment;

public class AdminMainActivity extends AppCompatActivity {


    public static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    public SqlData sqlData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        setToolbar();
        setViewPager();
        setTabLayout();


        getFoodDataFromDatabase();


    }
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setViewPager(){
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }
    private void setTabLayout(){
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getFoodDataFromDatabase(){
        sqlData=new SqlData(this);
        Cursor cursor = sqlData.getCursorFoodData();
        if (cursor.getCount()==0){
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
        else {
            /*
                * Column 1 gives _ID
                * Column 2 gives FoodName
                * Column 3 gives FoodPrice
            */

            StorageClass storageClass = new StorageClass();
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
            while (cursor.moveToNext()){

                FoodDetails foodDetails = new FoodDetails(cursor.getString(1),cursor.getInt(2),cursor.getString(3));
                storageClass.getCatalogData().add(foodDetails);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(0);
        }
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return new ShowItemsFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "ORDERS";
                case 1:
                    return "FOODDATA";
                case 2:
                    return "PROFILE";
                default:
                    return "MC-BC";
            }
        }
    }


}



