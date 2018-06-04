package com.gbombardier.tripocketmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.models.Trip;

public class ExpenseActivity extends AppCompatActivity {
    private EditText descriptionView, amountView;
    private Spinner categorySpinner;
    private ImageView cancelButton;
    private Button saveButton;
    private Trip currentTrip = new Trip();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        descriptionView = findViewById(R.id.expense_name);
        amountView = findViewById(R.id.expense_value);
        categorySpinner = findViewById(R.id.expense_category);
        cancelButton = findViewById(R.id.expense_cancel);
        saveButton = findViewById(R.id.expense_validate);

        if( getIntent().getExtras()!=null){
            currentTrip = (Trip)getIntent().getSerializableExtra("trip");
        }

        //Pour le spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.style_array, R.layout.spinner_design);
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
                //Enregistrer les valeurs
                finish();
            }
        });
    }
}
