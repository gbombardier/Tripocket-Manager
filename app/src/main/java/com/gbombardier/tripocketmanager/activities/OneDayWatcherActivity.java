package com.gbombardier.tripocketmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.models.DaysInfos;
import com.gbombardier.tripocketmanager.models.Trip;

public class OneDayWatcherActivity extends AppCompatActivity {
    private TextView titleView;
    private DaysInfos currentDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day_watcher);

        titleView = findViewById(R.id.oneDay_Title);


        if( getIntent().getSerializableExtra("day")!=null){
            currentDay= (DaysInfos)getIntent().getSerializableExtra("day");
        }else{
            currentDay = new DaysInfos();
        }

        updateUI();

    }

    public void updateUI(){
        titleView.setText(currentDay.getTitle());
    }
}
