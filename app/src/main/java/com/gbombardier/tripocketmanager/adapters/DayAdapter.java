package com.gbombardier.tripocketmanager.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gbombardier.tripocketmanager.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gbombardier.tripocketmanager.activities.CreateActivity;
import com.gbombardier.tripocketmanager.activities.OneDayWatcherActivity;
import com.gbombardier.tripocketmanager.activities.TripWatcherActivity;
import com.gbombardier.tripocketmanager.fragments.DayExpensesFragment;
import com.gbombardier.tripocketmanager.models.DaysInfos;
import com.gbombardier.tripocketmanager.models.Expense;
import com.gbombardier.tripocketmanager.models.Trip;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder> {

    private List<DaysInfos> daysList;
    private Trip currentTrip;
    public Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, value;

        public MyViewHolder(View view) {
            super(view);
            title= (TextView) view.findViewById(R.id.titleDay_inlist);
            value=(TextView) view.findViewById(R.id.valueDay_inlist);
        }
    }


    public DayAdapter(List<DaysInfos> daysList, Activity activity, Trip trip) {
        this.daysList = daysList;
        this.activity = activity;
        this.currentTrip = trip;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_day, parent, false);

        return new MyViewHolder(itemView);
    }

    //Solution pour le listener trouvée ici: https://stackoverflow.com/questions/42721571/recyclerview-onitemclicklistener
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DaysInfos day = daysList.get(position);
        holder.title.setText(day.getDate());

        //Pour le total de dépenses
        float total = day.getFood() + day.getLodging() + day.getTransport() + day.getActivity();
        holder.value.setText(String.valueOf(total));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), OneDayWatcherActivity.class);
                i.putExtra("day", daysList.get(position));
                i.putExtra("trip", currentTrip);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

}
