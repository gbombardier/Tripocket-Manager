package com.gbombardier.tripocketmanager.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.fragments.DatePickerFragment;
import com.gbombardier.tripocketmanager.fragments.DayExpensesFragment;
import com.gbombardier.tripocketmanager.models.DaysInfos;
import com.gbombardier.tripocketmanager.models.Trip;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OneDayWatcherActivity extends AppCompatActivity {
    private TextView titleView;
    private DaysInfos currentDay;
    private Trip currentTrip;
    private TextView foodObjectiveView, foodExpenseView;
    private TextView lodgingObjectiveView, lodgingExpenseView;
    private TextView activityObjectiveView, activityExpenseView;
    private TextView transportObjectiveView, transportExpenseView;
    private ImageView backButton;
    private LinearLayout foodLayout, lodgingLayout, activityLayout, transportLayout;
    private int daysRemaining;
    private NumberFormat formatter = NumberFormat.getNumberInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day_watcher);

        titleView = findViewById(R.id.oneDay_Title);
        foodObjectiveView = findViewById(R.id.oneDay_foodObjectif);
        lodgingObjectiveView = findViewById(R.id.oneDay_lodgingObjectif);
        activityObjectiveView = findViewById(R.id.oneDay_activityObjectif);
        transportObjectiveView = findViewById(R.id.oneDay_transportObjectif);
        foodExpenseView = findViewById(R.id.oneDay_foodExpense);
        lodgingExpenseView = findViewById(R.id.oneDay_lodgingExpense);
        activityExpenseView = findViewById(R.id.oneDay_activityExpense);
        transportExpenseView = findViewById(R.id.oneDay_transportExpense);
        backButton = findViewById(R.id.oneDay_back_button);
        foodLayout = findViewById(R.id.food_layout);
        lodgingLayout = findViewById(R.id.lodging_layout);
        activityLayout = findViewById(R.id.activity_layout);
        transportLayout = findViewById(R.id.transport_layout);

        if( getIntent().getSerializableExtra("day")!=null){
            currentDay= (DaysInfos)getIntent().getSerializableExtra("day");
            currentTrip= (Trip)getIntent().getSerializableExtra("trip");
        }else{
            currentDay = new DaysInfos();
            currentTrip = new Trip();
        }

        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        getDaysRemaining();

        //Pour gérer ce qui se passe si on appuie sur les boutons
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setPressListeners();
        updateUI();

    }

    //Pour afficher le fragment des infos de dépense
    public void showExpensesFragment(String category) {
        DialogFragment newFragment = new DayExpensesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("day", currentDay);
        newFragment.setArguments(bundle);
        newFragment.show(this.getFragmentManager(), "timePicker");
    }

    //Pour les on long long click listener
    public void setPressListeners(){
        foodLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showExpensesFragment("Nourriture");
                return true;
            }
        });

        lodgingLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showExpensesFragment("Hébergement");
                return true;
            }
        });

        activityLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showExpensesFragment("Activités");
                return true;
            }
        });

        transportLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showExpensesFragment("Transport");
                return true;
            }
        });
    }

    //Pour avoir le nombre de jours restant au voyage
    public void getDaysRemaining(){
        Date date = new Date();
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(currentTrip.getDeparture());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        daysRemaining = 0;
        if(date.getTime()> now.getTime()){
            daysRemaining = currentTrip.getTotalTripDays();
        }else{
            daysRemaining = currentTrip.getTotalTripDays() - Math.round(-(date.getTime()- now.getTime())/(1000*60*60*24));
        }
    }

    public void updateUI(){
        titleView.setText("Dépenses du " + currentDay.getDate());

        float objFood = currentTrip.getRemainingMoney()*(currentTrip.getFood()/100)/daysRemaining;
        float objLodging = currentTrip.getRemainingMoney()*(currentTrip.getLodging()/100)/daysRemaining;
        float objActivity = currentTrip.getRemainingMoney()*(currentTrip.getActivity()/100)/daysRemaining;
        float objTransport = currentTrip.getRemainingMoney()*(currentTrip.getTransport()/100)/daysRemaining;

        foodObjectiveView.setText("Votre Objectif:" + formatter.format(objFood) + "$");
        lodgingObjectiveView.setText("Votre Objectif:" + formatter.format(objLodging)+ "$");
        activityObjectiveView.setText("Votre Objectif:" + formatter.format(objActivity)+ "$");
        transportObjectiveView.setText("Votre Objectif:" + formatter.format(objTransport)+ "$");

        foodExpenseView.setText(formatter.format(currentDay.getFood()));
        lodgingExpenseView.setText(formatter.format(currentDay.getLodging()));
        activityExpenseView.setText(formatter.format(currentDay.getActivity()));
        transportExpenseView.setText(formatter.format(currentDay.getTransport()));

        if(currentDay.getFood() > objFood){
            foodExpenseView.setTextColor(Color.RED);
        }else if(currentDay.getLodging() > objLodging){
            lodgingExpenseView.setTextColor(Color.RED);
        }else if(currentDay.getActivity() > objActivity){
            activityExpenseView.setTextColor(Color.RED);
        }else if(currentDay.getTransport() > objTransport){
            transportExpenseView.setTextColor(Color.RED);
        }

    }
}
