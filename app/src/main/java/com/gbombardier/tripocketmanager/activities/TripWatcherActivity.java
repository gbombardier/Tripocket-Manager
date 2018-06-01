package com.gbombardier.tripocketmanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.database.DatabaseProfile;
import com.gbombardier.tripocketmanager.models.Trip;
import com.gbombardier.tripocketmanager.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TripWatcherActivity extends AppCompatActivity {
    private DatabaseReference usersDatabase, tripsDatabase;
    private User currentUserInfo;
    private Trip currentTrip;
    private TextView destinationTitle, dateDepartureView, remainingDaysView, budgetView;
    private Spinner styleView;
    private ImageView addButton;
    private Button moreInfoButton;
    private float totalBudget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_watcher);

        usersDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        currentTrip = new Trip();
        if( getIntent().getStringExtra("destination")!=null){
            currentTrip.setDestination( getIntent().getStringExtra("destination"));
        }

        destinationTitle = findViewById(R.id.destination_title_watcher);
        dateDepartureView = findViewById(R.id.trip_depart_view);
        remainingDaysView = findViewById(R.id.trip_days_view);
        budgetView = findViewById(R.id.trip_budget_view);
        styleView = findViewById(R.id.trip_style_view);
        moreInfoButton = findViewById(R.id.trip_moreinfo_button);

        addButton = findViewById(R.id.spend_add_button);

        //Pour gérer ce qui se passe si on appuie sur les boutons
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TripWatcherActivity.this, ExpenseActivity.class);
                i.putExtra("trip", currentTrip);
                startActivity(i);
            }
        });

        moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TripWatcherActivity.this, MoreInfoActivity.class);
                i.putExtra("trip", currentTrip);
                startActivity(i);
            }
        });

        moreInfoButton.setVisibility(View.INVISIBLE);

        //Pour le spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.style_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleView.setAdapter(adapter);
        styleView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(currentTrip.getid()==null){

                }else{
                    DatabaseProfile.getInstance(getApplicationContext()).writeStyle(styleView.getItemAtPosition(position).toString(), currentTrip.getid());

                    String style = styleView.getItemAtPosition(position).toString();
                    if(style.equals("Aventurier")){
                        currentTrip.setTripStyle("Aventurier");
                        currentTrip.setFood(20);
                        currentTrip.setLodging(15);
                        currentTrip.setActivity(40);
                        currentTrip.setTransport(25);
                    }else if(style.equals("Culinaire")){
                        currentTrip.setTripStyle("Culinaire");
                        currentTrip.setFood(45);
                        currentTrip.setLodging(20);
                        currentTrip.setActivity(20);
                        currentTrip.setTransport(15);
                    }else if(style.equals("Comfortable")){
                        currentTrip.setTripStyle("Comfortable");
                        currentTrip.setFood(30);
                        currentTrip.setLodging(40);
                        currentTrip.setActivity(20);
                        currentTrip.setTransport(10);
                    }else if(style.equals("Par défaut")){
                        currentTrip.setTripStyle("default");
                        currentTrip.setFood(35);
                        currentTrip.setLodging(30);
                        currentTrip.setActivity(20);
                        currentTrip.setTransport(15);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        getUserTripInfo();
    }

    //Va chercher les infos du user pour ensuite aller chercher le voyage
    public void getUserTripInfo(){
        usersDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot contactDataSnapshot : dataSnapshot.getChildren()) {
                    User user = contactDataSnapshot.getValue(User.class);
                    if(user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        currentUserInfo = new User(user.getid(), user.getEmail());
                        getTripInfo(currentUserInfo);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Va chercher les infos du voyage sélectionné
    public void getTripInfo(User user){
        tripsDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getid()).child("tripsList");
        tripsDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot tripsDataSnapshot : dataSnapshot.getChildren()) {
                    Trip trip = tripsDataSnapshot.getValue(Trip.class);
                    if(trip.getDestination().equals(currentTrip.getDestination())){
                        currentTrip = trip;
                        totalBudget = currentTrip.getTotalBudget()-currentTrip.getMainPlaneCost();
                        updateUI();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Pour trouver l'index d'un string du spinner style
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    //Pour mettre à jour les infos du voyage après l'avoir loadé (page de chargement)
    public void updateUI(){
        destinationTitle.setText(currentTrip.getDestination());
        dateDepartureView.setText(currentTrip.getDeparture());
        budgetView.setText(String.valueOf(totalBudget));

        if(currentTrip.getTripStyle().equals("default")){
            styleView.setSelection(getIndex(styleView, "Par défaut"));
        }else{
            styleView.setSelection(getIndex(styleView, currentTrip.getTripStyle()));
        }


        Date date = new Date();
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(currentTrip.getDeparture());
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur dans le format de la date",Toast.LENGTH_LONG).show();
        }

        if(date.getTime()> now.getTime()){
            float daysRemaining = (date.getTime()- now.getTime())/(1000*60*60*24);
            remainingDaysView.setText(String.valueOf(daysRemaining));
        }else{
            if(-(date.getTime()- now.getTime())/(1000*60*60*24) >= currentTrip.getTotalTripDays()){
                remainingDaysView.setText("Voyage Terminé");
            }else{
                remainingDaysView.setText(String.valueOf(currentTrip.getTotalTripDays() + (date.getTime()- now.getTime())/(1000*60*60*24)));
            }
        }

        moreInfoButton.setVisibility(View.VISIBLE);
    }
}
