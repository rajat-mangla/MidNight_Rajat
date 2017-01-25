package com.example.android.midnight_rajat.user.admin.FoodItems;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.midnight_rajat.R;
import com.example.android.midnight_rajat.fooddata.FoodDetails;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AddOrEditItem extends DialogFragment {

    String[] storagePermission = {"android.permission.WRITE_EXTERNAL_STORAGE"};
    private final int STORAGE_PERMISSION = 2;
    private final int PICK_IMAGE = 1;


    /*
     * Layout VARIABLES
    */
    View view;
    ImageView foodImage;
    EditText foodName;
    EditText foodPrice;


    /*
    An interface to send the data back to the host activity
     */
    public interface getDataInterface {
        void onAddDialogClick(FoodDetails foodDetails);
        void onEditDialogClick();
    }

    getDataInterface callback;
    /*
    An interface to send the data back to the host activity
     */


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (getDataInterface) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() + "  must implement this fucking interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /*
        working the Xml layout
            * retrieving
            * assigning to the variables
         */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_add_or_edit_item, null);
        foodImage = (ImageView) view.findViewById(R.id.fimage);
        foodName = (EditText) view.findViewById(R.id.fname);
        foodPrice = (EditText) view.findViewById(R.id.fprice);
        /*
            * Ends
        */

        if (getArguments()==null) {
            builder.setView(view)
                    .setTitle("Add Item")
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();


                            callback.onAddDialogClick(getEnteredData());

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            /*
            Displaying the Food Data
             */
            final FoodDetails foodDetails = (FoodDetails) getArguments().getSerializable("FOOD");
            builder.setView(view)
                    .setTitle("Edit Item")
                    .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                            changeEnteredData(foodDetails);


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();

                        }
                    });


            foodName.setText(foodDetails.getFoodName());
            foodPrice.setText(Integer.toString(foodDetails.getPrice()));
            foodImage.setImageResource(foodDetails.getFoodImage());

            /*
                * ENDS
            */
        }

        foodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForPermissions();
            }
        });
        return builder.create();
    }

    private void changeEnteredData(FoodDetails foodDetails){
        foodDetails.setFoodName(foodName.getText().toString());
        foodDetails.setPrice(Integer.parseInt(foodPrice.getText().toString()));
    }

    private FoodDetails getEnteredData() {

        String fname = foodName.getText().toString();

        Integer fprice = Integer.parseInt(foodPrice.getText().toString());

        //ImageView imageView = (ImageView) view.findViewById(R.id.fimage);
        return new FoodDetails(fprice, fname, R.mipmap.ic_launcher);
    }

    /*
    A function to pick images ...
     */
    private void imageSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                foodImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                Toast.makeText(getContext(), "Maki ", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }


    /*
    Below Function to check if it is Android 6.0 Marshmallow or above ...
     */
    private void checkForPermissions() {
        if (requirePermissions() && !hasPermissions()) {
            requestPermissions(storagePermission, STORAGE_PERMISSION);
        } else {
            imageSelector();
        }
    }

    private boolean requirePermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private boolean hasPermissions() {
        return getContext().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageSelector();
            } else {
                Toast.makeText(getContext(), "Permission Required To Add Image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /*
    Above Related with the User Permission For Android Version 6.0 and Above ...
     */
}
