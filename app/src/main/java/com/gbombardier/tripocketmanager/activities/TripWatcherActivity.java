package com.gbombardier.tripocketmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.models.Trip;
import com.gbombardier.tripocketmanager.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TripWatcherActivity extends AppCompatActivity {
    private DatabaseReference usersDatabase, tripsDatabase;
    private User currentUserInfo;
    private Trip currentTrip;
    private TextView destinationTitle;
    private ImageView addButton;


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
        addButton = findViewById(R.id.spend_add_button);

        //Pour gérer ce qui se passe si on appuie sur les boutons
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
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
                        updateUI();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Pour mettre à jour les infos du voyage après l'avoir loadé (page de chargement)
    public void updateUI(){
        destinationTitle.setText(currentTrip.getDestination());
    }
}
