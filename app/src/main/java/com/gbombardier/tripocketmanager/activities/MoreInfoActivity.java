package com.gbombardier.tripocketmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.models.Trip;

public class MoreInfoActivity extends AppCompatActivity {
    private Trip currentTrip = new Trip();
    private TextView nourritureTotalView, nourritureJourView, nourritureRestantView, nourritureJourRestantView;
    private TextView hebTotalView, hebJourView, hebRestantView, hebJourRestantView;
    private TextView actTotalView, actJourView, actRestantView, actJourRestantView;
    private TextView transTotalView, transJourView, transRestantView, transJourRestantView;
    private TextView totalTotalView, totalJourView, totalRestantView, totalJourRestantView;
    private TextView styleView;

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
        hebRestantView = findViewById(R.id.hebergement_budgetJourRestant_view);
        hebJourRestantView = findViewById(R.id.hebergement_budgetRestant_view);
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



        if( getIntent().getExtras()!=null){
            currentTrip = (Trip)getIntent().getSerializableExtra("trip");
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
        totalTotalView.setText(String.valueOf(currentTrip.getTotalBudget()));
        nourritureTotalView.setText(String.valueOf(currentTrip.getTotalBudget()*(currentTrip.getFood()/100)));
        hebTotalView.setText(String.valueOf(currentTrip.getTotalBudget()*(currentTrip.getLodging()/100)));
        actTotalView.setText(String.valueOf(currentTrip.getTotalBudget()*(currentTrip.getActivity()/100)));
        transTotalView.setText(String.valueOf(currentTrip.getTotalBudget()*(currentTrip.getTransport()/100)));
        styleView.setText(currentTrip.getTripStyle());
    }
}
