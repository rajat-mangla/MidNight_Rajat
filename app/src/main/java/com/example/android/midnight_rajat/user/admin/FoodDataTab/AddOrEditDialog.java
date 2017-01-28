package com.example.android.midnight_rajat.user.admin.FoodDataTab;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.midnight_rajat.R;
import com.example.android.midnight_rajat.fooddata.FoodDetails;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AddOrEditDialog extends DialogFragment {

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
    Bitmap bitmap;
    /*
    An interface to send the data back to the host activity
     */
    public interface getDataInterface {
        void onAddDialogClick(FoodDetails foodDetails);

        void onEditDialogClick(FoodDetails foodDetails);
    }
    getDataInterface callback;
    /*
    An interface to send the data back to the host activity
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            /*
                * Assigning Parent Fragment to the Interface ...
             */
            callback = (getDataInterface) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() + "  must implement this fucking interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*
            working the Xml layout
            * retrieving
            * assigning to the variables
        */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_add_or_edit_item, null);
        foodImage = (ImageView) view.findViewById(R.id.fimage);
        foodName = (EditText) view.findViewById(R.id.fname);
        foodName.requestFocus();
        foodPrice = (EditText) view.findViewById(R.id.fprice);
        /*
            * Ends
        */
        AlertDialog.Builder builder = setBuilder();

        foodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForPermissions();
            }
        });
        return builder.create();
    }

    /*
        * A fUNCTION tO Check if there is input or Not ...
     */
    private boolean noInput() {
        if (foodName.getText().toString().equals("") || foodPrice.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog alertDialog = (AlertDialog) getDialog();

        //alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        foodName.setSelection(foodName.getText().length());

        Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noInput()) {
                    Toast.makeText(getContext(), "PLEASE ENTER DETAILS", Toast.LENGTH_SHORT).show();
                } else {

                    // save Image method called from this ....

                    String imgPath = saveImage();

                    if (getArguments() == null) {
                        Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                        callback.onAddDialogClick(getEnteredData(imgPath));
                    } else {
                        final FoodDetails foodDetails = (FoodDetails) getArguments().getSerializable("FOOD");
                        Toast.makeText(getContext(), "Edited", Toast.LENGTH_SHORT).show();
                        changeEnteredData(foodDetails,imgPath);

                        callback.onEditDialogClick(foodDetails);
                    }
                    alertDialog.dismiss();
                }
            }
        });
    }


    /*
         * Function to get builder of Alert Dialog
            * it also checks whether it Should be Edit Dialog or Add Dialog ...
     */
    private AlertDialog.Builder setBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"Canceled",Toast.LENGTH_SHORT).show();
                    }
                });
        if (getArguments() == null) {
            builder.setTitle("Add Item")
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

        } else {
            /*
                Displaying the Edit Food Dialog box
             */
            builder.setTitle("Edit Item")
                    .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            FoodDetails foodDetails = (FoodDetails) getArguments().getSerializable("FOOD");
            foodName.setText(foodDetails.getFoodName());
            foodPrice.setText(Integer.toString(foodDetails.getPrice()));
            foodImage.setImageResource(R.mipmap.ic_launcher);
            /*
                * ENDS
            */
        }
        return builder;
    }


    /*
        * Function to change Data of Existing Food Item
            * Used For Edit Dialog Box
     */
    private void changeEnteredData(FoodDetails foodDetails,String imgPath) {
        foodDetails.setFoodName(foodName.getText().toString());
        foodDetails.setPrice(Integer.parseInt(foodPrice.getText().toString()));
        foodDetails.setImagePath(imgPath);
    }

    /*
        * Function to Add Data of a Food Item
            * Used For Add Dialog Box
     */
    private FoodDetails getEnteredData(String imgPath) {

        String fname = foodName.getText().toString();
        Integer fprice = Integer.parseInt(foodPrice.getText().toString());

        FoodDetails foodDetails =new FoodDetails(fname, fprice,imgPath);


        //ImageView imageView = (ImageView) view.findViewById(R.id.fimage);
        return foodDetails;
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

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                foodImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                Toast.makeText(getContext(), "Maki ", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
    /*
        * Pick Images Ends
     */

    private String saveImage(){
        ContextWrapper contextWrapper = new ContextWrapper(getContext());
        File directory = contextWrapper.getDir("foodData",Context.MODE_PRIVATE);

        File myPath = new File(directory, foodName.getText().toString()+".jpg");
        String imgPath = myPath.getAbsolutePath();

        FileOutputStream fos = null;
        try {
            if (bitmap == null){
                return "";
            }
            fos = new FileOutputStream(myPath);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            Toast.makeText(getContext(), "Image Added ", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("SAVE_IMAGE", e.getMessage(), e);
        }
        return imgPath;
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
