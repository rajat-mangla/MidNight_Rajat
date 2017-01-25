package com.example.android.midnight_rajat.user.admin.FoodItems;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.midnight_rajat.R;
import com.example.android.midnight_rajat.fooddata.FoodDetails;
import com.example.android.midnight_rajat.fooddata.StorageClass;

/**
 * Created by rajat on 20-01-2017.
 */


/*
Using Catalog Data As The Data To Represent the Food Items ....
 */
public class ShowItemsFragment extends Fragment implements AddOrEditItem.getDataInterface {
    ListView listview;
    ShowItemsAdapter showItemsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_items, container, false);

        listview = (ListView) view.findViewById(R.id.fooditems);
        showItemsAdapter = new ShowItemsAdapter(getContext(),new StorageClass().getCatalogData());
        listview.setAdapter(showItemsAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showEditDialog(i,showItemsAdapter.getItem(i));
            }
        });


        ImageButton addItems = (ImageButton) view.findViewById(R.id.additems);
        //listview.setEmptyView(view.findViewById(R.id.empty_view));
        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
        return view;
    }


    /*
    function to pop up the Add dialog box
     */
    private void showAddDialog(){
        DialogFragment dialogFragment = new AddOrEditItem();
        dialogFragment.setTargetFragment(ShowItemsFragment.this,1);
        dialogFragment.show(getFragmentManager(),"AddItem");
    }
    @Override
    public void onAddDialogClick(FoodDetails foodDetails) {
        new StorageClass().getCatalogData().add(foodDetails);
        showItemsAdapter.notifyDataSetChanged();
    }

    private void showEditDialog(int index,FoodDetails foodDetail){
        DialogFragment dialogFragment = new AddOrEditItem();
        Bundle bundle = new Bundle();
        bundle.putInt("INDEX_OF_FOOD",index);
        dialogFragment.setArguments(bundle);
        dialogFragment.setTargetFragment(ShowItemsFragment.this,1);
        dialogFragment.show(getFragmentManager(),"AddItem");

    }




}
