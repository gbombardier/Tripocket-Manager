package com.gbombardier.tripocketmanager.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.database.DatabaseProfile;
import com.gbombardier.tripocketmanager.fragments.DatePickerExpenseFragment;
import com.gbombardier.tripocketmanager.fragments.DatePickerFragment;
import com.gbombardier.tripocketmanager.models.DaysInfos;
import com.gbombardier.tripocketmanager.models.Expense;
import com.gbombardier.tripocketmanager.models.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseActivity extends AppCompatActivity {
    private EditText descriptionView, amountView;
    private TextView dateView;
    private Spinner categorySpinner;
    private ImageView cancelButton;
    private Button saveButton;
    private Trip currentTrip = new Trip();
    private Expense currentExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        descriptionView = findViewById(R.id.expense_name);
        amountView = findViewById(R.id.expense_value);
        categorySpinner = findViewById(R.id.expense_category);
        cancelButton = findViewById(R.id.expense_cancel);
        saveButton = findViewById(R.id.expense_validate);
        dateView = findViewById(R.id.expense_date_title);

        currentExpense = new Expense();
        currentExpense.setCategory("food"); //Parce que dans la liste nourriture est celui en premier

        if( getIntent().getExtras()!=null){
            currentTrip = (Trip)getIntent().getSerializableExtra("trip");
        }

        //Pour le spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, R.layout.spinner_design);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        //Pour gérer ce qui se passe si on appuie sur les boutons
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true, dateValid = false;
                Date date = new Date();
                Date now = new Date();

                if(currentExpense.equals("none")){
                    valid = false;
                }else if(String.valueOf(descriptionView.getText()).trim().length() == 0){
                    valid = false;
                }else if(String.valueOf(amountView.getText()).trim().length() == 0){
                    valid = false;
                }

                if(dateView.getText().equals("Pas de date")){
                    valid = false;
                }else{
                    //Vérifier si la date est dans le voyage

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    for(DaysInfos day : currentTrip.getDaysList()){
                        try {
                            date = format.parse(currentTrip.getDeparture());
                            now = format.parse(dateView.getText().toString());
                            if(now.after(date) || now.equals(date)){
                                dateValid = true;
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if(valid && dateValid){
                    currentExpense.setTitle(descriptionView.getText().toString());
                    currentExpense.setValue(Float.parseFloat(amountView.getText().toString()));

                    Intent data = new Intent();
                    data.putExtra("expense", currentExpense);
                    setResult(RESULT_OK, data);

                    //Enregistrer les valeurs
                    DatabaseProfile.getInstance(getApplicationContext()).writeExpense(currentExpense, currentTrip, currentTrip.getDaysList(), dateView.getText().toString());

                    finish();
                }
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(categorySpinner.getItemAtPosition(position).toString().equals("Nourriture")){
                    currentExpense.setCategory("food");
                }else if(categorySpinner.getItemAtPosition(position).toString().equals("Hébergement")){
                    currentExpense.setCategory("lodging");
                }else if(categorySpinner.getItemAtPosition(position).toString().equals("Activités")){
                    currentExpense.setCategory("activity");
                }else if(categorySpinner.getItemAtPosition(position).toString().equals("Transport")){
                    currentExpense.setCategory("transport");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerExpenseFragment();
        newFragment.show(this.getFragmentManager(), "timePicker");
    }

    //Pour le modele de date
    public String formatDate(Date date){
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear()+1900;

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

        String dateString = year + "-" + stringMonth + "-" + stringDay;

        return dateString;
    }
}
