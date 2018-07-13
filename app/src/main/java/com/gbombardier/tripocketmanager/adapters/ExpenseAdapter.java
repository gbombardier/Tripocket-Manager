package com.gbombardier.tripocketmanager.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.activities.ExpenseActivity;
import com.gbombardier.tripocketmanager.activities.OneDayWatcherActivity;
import com.gbombardier.tripocketmanager.activities.TripWatcherActivity;
import com.gbombardier.tripocketmanager.fragments.DayExpensesFragment;
import com.gbombardier.tripocketmanager.models.DaysInfos;
import com.gbombardier.tripocketmanager.models.Expense;
import com.gbombardier.tripocketmanager.models.Trip;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private ArrayList<Expense> expenses;
    private String category;
    public Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, value;

        public MyViewHolder(View view) {
            super(view);
            title= view.findViewById(R.id.titleExpense_inlist);
            value= view.findViewById(R.id.valueExpense_inlist);
        }
    }


    public ExpenseAdapter(String category, DayExpensesFragment dayExpensesFragment, ArrayList<Expense> expenses) {
        if(category.equals("Nourriture")){
            this.category = "food";
        }else if(category.equals("Activités")){
            this.category = "activity";
        }else if(category.equals("Hébergement")){
            this.category = "lodging";
        }else if(category.equals("Transport")){
            this.category = "transport";
        }

        this.expenses = expenses;

        ArrayList<Expense> tempExpenses = new ArrayList<>();
        for(Expense expense : expenses){
            if(expense != null){
                if(expense.getCategory().equals(this.category)){
                    tempExpenses.add(expense);
                }
            }
        }

        this.expenses = tempExpenses;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_expense, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(this.expenses.size()==0){
            holder.title.setText(this.category + " - Aucune Dépense");
        }

        if(expenses.get(position)!=null){
            final String name = expenses.get(position).getTitle();
            holder.title.setText(name);

            final float value = expenses.get(position).getValue();
            holder.value.setText(String.valueOf(value));

            /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), ExpenseActivity.class);
                    i.putExtra("title", expenses.get(position).getTitle());
                    i.putExtra("value", String.valueOf(expenses.get(position).getValue()));
                    i.putExtra("cat", expenses.get(position).getCategory());
                    i.putExtra("id", expenses.get(position).getId());
                    v.getContext().startActivity(i);
                }
            });*/
        }



    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

}
