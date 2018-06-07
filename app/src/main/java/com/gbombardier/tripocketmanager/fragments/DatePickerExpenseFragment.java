package com.gbombardier.tripocketmanager.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.gbombardier.tripocketmanager.R;

import java.util.Calendar;

public class DatePickerExpenseFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView departureEdit = getActivity().findViewById(R.id.expense_date_title);
        String stringMonth, stringDay;
        month++;
        if(month<=9){
            stringMonth = "0"+month;
        }else{
            stringMonth = String.valueOf(month);
        }

        if(day<=9){
            stringDay = "0"+day;
        }else{
            stringDay = String.valueOf(day);
        }

        String date = year + "-" + stringMonth + "-" + stringDay;
        departureEdit.setText(date);
    }
}
