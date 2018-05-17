package com.gbombardier.tripocketmanager.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.database.DatabaseProfile;
import com.gbombardier.tripocketmanager.fragments.DatePickerFragment;
import com.gbombardier.tripocketmanager.models.DayInfo;
import com.gbombardier.tripocketmanager.models.Trip;

import java.util.Date;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{
    private Button createButton;
    private Trip currentTrip;
    private EditText destinationEdit, budgetEdit, nbrDaysEdit, planePriceEdit, planeDaysEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        createButton = findViewById(R.id.btn_validateTrip);
        destinationEdit = findViewById(R.id.destination_edit);
        budgetEdit = findViewById(R.id.totalBudget_edit);
        nbrDaysEdit = findViewById(R.id.tripDays_edit);
        planePriceEdit = findViewById(R.id.mainPlaneCost_edit);
        planeDaysEdit = findViewById(R.id.mainPlaneDays_edit);

        createButton.setOnClickListener(this);
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "timePicker");
    }

    public void writeTrip(){
        currentTrip = new Trip(destinationEdit.getText().toString(), Float.parseFloat(planePriceEdit.getText().toString()), Integer.parseInt(planeDaysEdit.getText().toString()), Integer.parseInt(nbrDaysEdit.getText().toString()), null, Integer.parseInt(nbrDaysEdit.getText().toString()), Float.parseFloat(budgetEdit.getText().toString()), Float.parseFloat(budgetEdit.getText().toString()), null, 0);
        currentTrip.setDeparture(new Date());
        DatabaseProfile.getInstance(this).writeTrip(currentTrip);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if(id==R.id.btn_validateTrip){
            writeTrip();
            Intent i = new Intent(CreateActivity.this, HomeActivity.class);
            startActivity(i);
        }
    }
}
