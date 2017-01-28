package com.example.android.midnight_rajat.user.admin.FoodDataTab;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.android.midnight_rajat.R;
import com.example.android.midnight_rajat.fooddata.FoodDetails;
import com.example.android.midnight_rajat.fooddata.localstorage.SqlData;
import com.example.android.midnight_rajat.fooddata.StorageClass;

/**
 * Created by rajat on 20-01-2017.
 */


/*
Using Catalog Data As The Data To Represent the Food Items ....
 */
public class ShowItemsFragment extends Fragment implements AddOrEditDialog.getDataInterface {

    ShowItemsAdapter showItemsAdapter;
    int index=-1;
    SqlData sqlData;  // initiated in onCreate Method

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
         sqlData = new SqlData(getContext());

        View view = inflater.inflate(R.layout.fragment_add_items, container, false);
        ListView listview = (ListView) view.findViewById(R.id.fooditems);
        showItemsAdapter = new ShowItemsAdapter(getContext(),new StorageClass().getCatalogData());
        listview.setAdapter(showItemsAdapter);

        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                showEditDialog(showItemsAdapter.getItem(i));

            }
        });*/
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                showEditDialog(showItemsAdapter.getItem(position));
                return false;
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
        DialogFragment dialogFragment = new AddOrEditDialog();
        dialogFragment.setTargetFragment(ShowItemsFragment.this,1);
        dialogFragment.show(getFragmentManager(),"AddItem");
    }

    /*
        * Function to perform tasks after add button of add dialog is clicked ...
     */
    @Override
    public void onAddDialogClick(FoodDetails foodDetails) {
        StorageClass storageClass = new StorageClass();
        int index = storageClass.getCatalogData().size();
        storageClass.getCatalogData().add(foodDetails);

        sqlData.insertFoodDetail(foodDetails.getFoodName(),foodDetails.getPrice(),index,foodDetails.getImagePath());

        showItemsAdapter.notifyDataSetChanged();
    }

    /*
        Edit Dialog Functions Implementation
     */
    private void showEditDialog(FoodDetails foodDetail){
        DialogFragment dialogFragment = new AddOrEditDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("FOOD",foodDetail);
        dialogFragment.setArguments(bundle);
        dialogFragment.setTargetFragment(ShowItemsFragment.this,1);
        dialogFragment.show(getFragmentManager(),"AddItem");
    }

    @Override
    public void onEditDialogClick(FoodDetails foodDetails) {
        if (index != -1){
            sqlData.updateFoodDetail(foodDetails.getFoodName(),foodDetails.getPrice(),index,foodDetails.getImagePath());
        }
        showItemsAdapter.notifyDataSetChanged();
    }

}
