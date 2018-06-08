package com.gbombardier.tripocketmanager.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.models.DaysInfos;

public class DayExpensesFragment extends DialogFragment{

    private DaysInfos currentDay;
    private String category = "";
    private TextView title;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_day_expenses, container, false);

            title = rootView.findViewById(R.id.dayInfo_title);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                currentDay = (DaysInfos)bundle.getSerializable("day");
                category = bundle.getString("cat");
                updateUI();
            }else{
                title.setText("Erreur, veuillez recommencer");
            }

            updateUI();
            return rootView;
        }

        public void updateUI(){
            title.setText(category);
        }
}
