package com.gbombardier.tripocketmanager.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.database.DatabaseProfile;
import com.gbombardier.tripocketmanager.fragments.DatePickerFragment;
import com.gbombardier.tripocketmanager.models.DayInfo;
import com.gbombardier.tripocketmanager.models.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{
    private Button createButton;
    private Trip currentTrip;
    private EditText destinationEdit, budgetEdit, nbrDaysEdit, planePriceEdit, planeDaysEdit;
    private TextView departureDateView;

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
        departureDateView = findViewById(R.id.departure_view);

        createButton.setOnClickListener(this);
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "timePicker");
    }

    public void writeTrip(){
        currentTrip = new Trip(destinationEdit.getText().toString(), Float.parseFloat(planePriceEdit.getText().toString()), Integer.parseInt(planeDaysEdit.getText().toString()), Integer.parseInt(nbrDaysEdit.getText().toString()), null, Integer.parseInt(nbrDaysEdit.getText().toString()), Float.parseFloat(budgetEdit.getText().toString()), Float.parseFloat(budgetEdit.getText().toString()), null, 0);

        //Pour transformer la date
        /*Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(departureDateView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur dans le format de la date",Toast.LENGTH_LONG).show();
        }*/
        currentTrip.setDeparture(departureDateView.getText().toString());
        currentTrip.setFood(25);
        currentTrip.setActivity(25);
        currentTrip.setLodging(25);
        currentTrip.setTransport(25);

        DatabaseProfile.getInstance(this).writeTrip(currentTrip);
    }

    public boolean tripValide(){
        boolean valide = true;

        if(departureDateView.getText().equals("Pas de date")){
            valide = false;
            Toast.makeText(this, "Veuillez choisir une date de départ.",Toast.LENGTH_LONG).show();
        }

        return valide;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if(id==R.id.btn_validateTrip){
            if(tripValide()){
                writeTrip();
                Intent i = new Intent(CreateActivity.this, HomeActivity.class);
                startActivity(i);
            }

        }
    }
}
