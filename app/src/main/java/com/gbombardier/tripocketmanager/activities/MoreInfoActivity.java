package com.gbombardier.tripocketmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.models.Trip;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MoreInfoActivity extends AppCompatActivity {
    private Trip currentTrip = new Trip();
    private TextView nourritureTotalView, nourritureJourView, nourritureRestantView, nourritureJourRestantView;
    private TextView hebTotalView, hebJourView, hebRestantView, hebJourRestantView;
    private TextView actTotalView, actJourView, actRestantView, actJourRestantView;
    private TextView transTotalView, transJourView, transRestantView, transJourRestantView;
    private TextView totalTotalView, totalJourView, totalRestantView, totalJourRestantView;
    private TextView styleView;
    private float totalBudget;
    NumberFormat formatter = NumberFormat.getNumberInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        nourritureTotalView = findViewById(R.id.nourriture_budgetTotal_view);
        nourritureJourView = findViewById(R.id.nourriture_budgetJourTotal_view);
        nourritureJourRestantView = findViewById(R.id.nourriture_budgetJourRestant_view);
        nourritureRestantView = findViewById(R.id.nourriture_budgetRestant_view);
        hebTotalView = findViewById(R.id.hebergement_budgetTotal_view);
        hebJourView = findViewById(R.id.hebergement_budgetJourTotal_view);
        hebRestantView = findViewById(R.id.hebergement_budgetRestant_view);
        hebJourRestantView = findViewById(R.id.hebergement_budgetJourRestant_view);
        actTotalView = findViewById(R.id.activites_budgetTotal_view);
        actJourView = findViewById(R.id.activites_budgetJourTotal_view);
        actJourRestantView = findViewById(R.id.activites_budgetJourRestant_view);
        actRestantView = findViewById(R.id.activites_budgetRestant_view);
        transTotalView = findViewById(R.id.transport_budgetTotal_view);
        transJourView = findViewById(R.id.transport_budgetJourTotal_view);
        transJourRestantView = findViewById(R.id.transport_budgetJourRestant_view);
        transRestantView = findViewById(R.id.transport_budgetRestant_view);
        totalTotalView = findViewById(R.id.total_budgetTotal_view);
        totalJourView = findViewById(R.id.total_budgetJourTotal_view);
        totalJourRestantView = findViewById(R.id.total_budgetJourRestant_view);
        totalRestantView = findViewById(R.id.total_budgetRestant_view);
        styleView = findViewById(R.id.style_view);

        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        if( getIntent().getExtras()!=null){
            currentTrip = (Trip)getIntent().getSerializableExtra("trip");
            totalBudget = currentTrip.getTotalBudget() - currentTrip.getMainPlaneCost();
            updateUI();
        }

    }

    @Override
    protected void onResume(){
        super.onResume();

        if( getIntent().getExtras()!=null){
            currentTrip = (Trip)getIntent().getSerializableExtra("trip");
            updateUI();
        }
    }

    public void updateUI(){
        styleView.setText(currentTrip.getTripStyle());

        Date date = new Date();
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(currentTrip.getDeparture());
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur dans le format de la date",Toast.LENGTH_LONG).show();
        }


        int daysRemaining = 0;
        if(date.getTime()> now.getTime()){
            daysRemaining = currentTrip.getTotalTripDays();
        }else{
            daysRemaining = currentTrip.getTotalTripDays() - Math.round(-(date.getTime()- now.getTime())/(1000*60*60*24));
        }

        //Les budgets totaux
        totalTotalView.setText(String.valueOf(formatter.format(totalBudget)));
        nourritureTotalView.setText(String.valueOf(formatter.format(totalBudget*(currentTrip.getFood()/100))));
        hebTotalView.setText(String.valueOf(formatter.format(totalBudget*(currentTrip.getLodging()/100))));
        actTotalView.setText(String.valueOf(formatter.format(totalBudget*(currentTrip.getActivity()/100))));
        transTotalView.setText(String.valueOf(formatter.format(totalBudget*(currentTrip.getTransport()/100))));

        //Les budgets par jour
        totalJourView.setText(String.valueOf(formatter.format(totalBudget/currentTrip.getTotalTripDays())));
        nourritureJourView.setText(String.valueOf(formatter.format(totalBudget*(currentTrip.getFood()/100)/currentTrip.getTotalTripDays())));
        hebJourView.setText(String.valueOf(formatter.format(totalBudget*(currentTrip.getLodging()/100)/currentTrip.getTotalTripDays())));
        actJourView.setText(String.valueOf(formatter.format(totalBudget*(currentTrip.getActivity()/100)/currentTrip.getTotalTripDays())));
        transJourView.setText(String.valueOf(formatter.format(totalBudget*(currentTrip.getTransport()/100)/currentTrip.getTotalTripDays())));

        //Les budgets restants
        totalRestantView.setText(String.valueOf(formatter.format(currentTrip.getRemainingMoney())));
        nourritureRestantView.setText(String.valueOf(formatter.format(currentTrip.getRemainingMoney()*(currentTrip.getFood()/100))));
        hebRestantView.setText(String.valueOf(formatter.format(currentTrip.getRemainingMoney()*(currentTrip.getLodging()/100))));
        actRestantView.setText(String.valueOf(formatter.format(currentTrip.getRemainingMoney()*(currentTrip.getActivity()/100))));
        transRestantView.setText(String.valueOf(formatter.format(currentTrip.getRemainingMoney()*(currentTrip.getTransport()/100))));

        //Les budgets par jour restants
        totalJourRestantView.setText(String.valueOf(formatter.format(currentTrip.getRemainingMoney()/currentTrip.getTotalTripDays())));
        nourritureJourRestantView.setText(String.valueOf(formatter.format(currentTrip.getRemainingMoney()*(currentTrip.getFood()/100)/daysRemaining)));
        hebJourRestantView.setText(String.valueOf(formatter.format(currentTrip.getRemainingMoney()*(currentTrip.getLodging()/100)/daysRemaining)));
        actJourRestantView.setText(String.valueOf(formatter.format(currentTrip.getRemainingMoney()*(currentTrip.getActivity()/100)/daysRemaining)));
        transJourRestantView.setText(String.valueOf(formatter.format(currentTrip.getRemainingMoney()*(currentTrip.getTransport()/100)/daysRemaining)));
    }
}
