package com.gbombardier.tripocketmanager.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.activities.TripWatcherActivity;
import com.gbombardier.tripocketmanager.adapters.DayAdapter;
import com.gbombardier.tripocketmanager.adapters.ExpenseAdapter;
import com.gbombardier.tripocketmanager.models.DaysInfos;
import com.gbombardier.tripocketmanager.models.Expense;

import java.util.ArrayList;

public class DayExpensesFragment extends DialogFragment{

    private DaysInfos currentDay;
    private String category = "";
    private TextView title;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView viewExpenses;
    private ExpenseAdapter adapterRecycler;
    private ArrayList<Expense> currentExpenses = new ArrayList<>();

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_day_expenses, container, false);

            title = rootView.findViewById(R.id.dayInfo_title);
            viewExpenses = rootView.findViewById(R.id.recycler_expenses);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                currentDay = (DaysInfos)bundle.getSerializable("day");
                category = bundle.getString("cat");
                updateUI();
            }else{
                title.setText("Erreur, veuillez recommencer");
            }


            //Pour le recyclerView
            mLayoutManager = new LinearLayoutManager(getActivity());
            viewExpenses.setLayoutManager(mLayoutManager);
            viewExpenses.setItemAnimator(new DefaultItemAnimator());
            adapterRecycler = new ExpenseAdapter(category, DayExpensesFragment.this, currentDay.getExpenses());
            viewExpenses.setAdapter(adapterRecycler);


            return rootView;
        }

        public void updateUI(){
            title.setText(category);
        }
}
