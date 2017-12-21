package com.example.kalyan.didyoufeel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by KALYAN on 01-06-2017.
 */

public class MyAlert extends DialogFragment {

    private String title;
    private String[] array;
    private String orderby = "time",limit  = "5",minmagnitude = "1";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(title.contains("orderby")){
                    orderby = array[which];
                }
                if(title.contains("limit")){
                    limit = array[which];
                }if(title.contains("minmagnitude")){
                    minmagnitude = array[which];
                }
            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog dialog  = builder.create();
        return dialog;
    }

    public void Title(String title){
        this.title = title;
    }

    public void Array(String[] array){
        this.array = array;
    }

    public String getOrderby(){
        return orderby;
    }

    public String getLimit(){
        return limit;
    }

    public String getMinmagnitude(){
        return minmagnitude;
    }
}

