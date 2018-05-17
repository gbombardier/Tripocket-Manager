package com.gbombardier.tripocketmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.adapters.TripAdapter;
import com.gbombardier.tripocketmanager.database.DatabaseProfile;
import com.gbombardier.tripocketmanager.models.Trip;
import com.gbombardier.tripocketmanager.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView boutonParam;
    private Button buttonCreate;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseProfile myDatabase;
    private Vector<Trip> tripList = new Vector<Trip>();
    private RecyclerView viewTrips;
    private LinearLayoutManager mLayoutManager;
    private TripAdapter adapter;
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usersDatabase = rootRef.child("users");
    private User currentUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_home);

        myDatabase = DatabaseProfile.getInstance(this);

        //If no user connected
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            LoginActivity.show(this);
            finish();
        }else{
            DatabaseProfile.getInstance(this).getUserInfo();
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        boutonParam = findViewById(R.id.settings_btn);
        buttonCreate = (Button)findViewById(R.id.button_create);
        viewTrips = findViewById(R.id.recycler_voyages);

        mLayoutManager = new LinearLayoutManager(this);
        viewTrips.setLayoutManager(mLayoutManager);
        viewTrips.setItemAnimator(new DefaultItemAnimator());
        adapter = new TripAdapter(tripList, HomeActivity.this);
        viewTrips.setAdapter(adapter);

        boutonParam.setOnClickListener(this);
        buttonCreate.setOnClickListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        tripList.clear();
        adapter.notifyDataSetChanged();
        getUserInfo();
    }

    //Va chercher les infos de l'user courant pour ensuite aller chercher ses voyages
    public void getUserInfo(){
        usersDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot contactDataSnapshot : dataSnapshot.getChildren()) {
                    User user = contactDataSnapshot.getValue(User.class);
                    if(user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        currentUserInfo = new User(user.getid(), user.getEmail());
                        getTripInfos();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Va chercher les infos des voyages et met a jour la liste
    public void getTripInfos(){
        usersDatabase.child(currentUserInfo.getid()).child("tripsList").addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot tripsDataSnapshot : dataSnapshot.getChildren()) {
                    Trip trip = tripsDataSnapshot.getValue(Trip.class);
                    trip.setDeparture(new Date());
                    currentUserInfo.addTrip(trip);
                    tripList.add(trip);
                    adapter.notifyItemInserted(tripList.size()-1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if(id==R.id.settings_btn){
            Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(i);
        }

        if(id==R.id.button_create){
            Intent i = new Intent(HomeActivity.this, CreateActivity.class);
            startActivity(i);
        }
    }
}
