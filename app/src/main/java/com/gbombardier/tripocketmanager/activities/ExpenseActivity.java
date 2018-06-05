package com.gbombardier.tripocketmanager.activities;

import android.app.Activity;
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

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.database.DatabaseProfile;
import com.gbombardier.tripocketmanager.models.Expense;
import com.gbombardier.tripocketmanager.models.Trip;

public class ExpenseActivity extends AppCompatActivity {
    private EditText descriptionView, amountView;
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
                boolean valid = true;

                if(currentExpense.equals("none")){
                    valid = false;
                }else if(String.valueOf(descriptionView.getText()).trim().length() == 0){
                    valid = false;
                }else if(String.valueOf(amountView.getText()).trim().length() == 0){
                    valid = false;
                }

                if(valid == true){
                    currentExpense.setTitle(descriptionView.getText().toString());
                    currentExpense.setValue(Float.parseFloat(amountView.getText().toString()));

                    Intent data = new Intent();
                    data.putExtra("expense", currentExpense);
                    setResult(RESULT_OK, data);

                    //Enregistrer les valeurs
                    DatabaseProfile.getInstance(getApplicationContext()).writeExpense(currentExpense, currentTrip, currentTrip.getDaysList());

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
}
