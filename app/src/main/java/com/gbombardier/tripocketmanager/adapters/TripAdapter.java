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
import com.gbombardier.tripocketmanager.activities.TripWatcherActivity;
import com.gbombardier.tripocketmanager.models.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

    private List<Trip> tripList;
    public Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView destination, date;

        public MyViewHolder(View view) {
            super(view);
            destination= (TextView) view.findViewById(R.id.destination_inlist);
            date=(TextView) view.findViewById(R.id.date_inlist);
        }
    }


    public TripAdapter(List<Trip> tripList, Activity activity) {
        this.tripList = tripList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_trip, parent, false);

        return new MyViewHolder(itemView);
    }

    //Solution pour le listener trouvée ici: https://stackoverflow.com/questions/42721571/recyclerview-onitemclicklistener
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.destination.setText(trip.getDestination());
        holder.date.setText(String.valueOf(trip.getDeparture()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), TripWatcherActivity.class);
                i.putExtra("destination", holder.destination.getText());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

}
