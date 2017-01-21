package com.example.android.midnight_rajat.user.admin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.midnight_rajat.R;
import com.example.android.midnight_rajat.fooddata.StorageClass;

/**
 * Created by rajat on 20-01-2017.
 */

public class AddItemsFragment extends Fragment {
    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_items, container, false);

        listview = (ListView) view.findViewById(R.id.fooditems);
        listview.setAdapter(new AddItemsAdapter(getContext(),new StorageClass().getFoodCart()));

        FloatingActionButton addItems = (FloatingActionButton) view.findViewById(R.id.additems);

        //listview.setEmptyView(view.findViewById(R.id.empty_view));



        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Add Some Items",Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        return view;
    }

}
