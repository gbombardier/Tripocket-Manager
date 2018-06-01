package com.gbombardier.tripocketmanager.database;

import android.content.Context;

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

/**
 * Created by gabb_ on 2018-01-08.
 * Inspired by Google Firebase examples
 */

public class DatabaseProfile {
    private static final String TAG = "DatabaseProfile";
    private static DatabaseProfile instance;
    private DatabaseReference usersDatabase;
    private Context context;
    DatabaseReference myRef;
    private boolean persEnabled = false;
    private User currentUserInfo;

    private DatabaseProfile(final Context context) {
        this.context = context;

        if(persEnabled == false){
            persEnabled = true;
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            usersDatabase = rootRef.child("users");
        }else{
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            usersDatabase = rootRef.child("users");
        }

        getUserInfo();
    }

    public static DatabaseProfile getInstance(final Context context) {
        if(instance == null) {
            //FirebaseDatabase.getInstance().setPersistenceEnabled(false);
            instance = new DatabaseProfile(context);
        }

        return instance;
    }

    //Pour écrire un usager dans la base de données
    public void writeUser(User user){
        String key =  usersDatabase.push().getKey();
        usersDatabase.child(key).child("id").setValue(key);
        usersDatabase.child(key).child("email").setValue(user.getEmail());
    }

    public void getUserInfo(){
        usersDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot contactDataSnapshot : dataSnapshot.getChildren()) {
                    User user = contactDataSnapshot.getValue(User.class);
                    if(user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        currentUserInfo = new User(user.getid(), user.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Pour écrire un voyagedans la base de données
    public void writeTrip(Trip trip){
        String key =  usersDatabase.child(currentUserInfo.getid()).child("tripsList").push().getKey();
        usersDatabase.child(currentUserInfo.getid()).child("tripsList").child(key).child("id").setValue(key);
        DatabaseReference currentTripRef = usersDatabase.child(currentUserInfo.getid()).child("tripsList").child(key);

        //Setter les trucs ici
        currentTripRef.child("destination").setValue(trip.getDestination());
        currentTripRef.child("mainPlaneCost").setValue(trip.getMainPlaneCost());
        currentTripRef.child("mainPlaneDays").setValue(trip.getMainPlaneDays());
        currentTripRef.child("totalTripDays").setValue(trip.getTotalTripDays());
        currentTripRef.child("tripStyle").setValue("default");
        currentTripRef.child("remainingDays").setValue(trip.getRemainingDays());
        currentTripRef.child("totalBudget").setValue(trip.getTotalBudget());
        currentTripRef.child("remainingMoney").setValue(trip.getTotalBudget()-trip.getMainPlaneCost());
        currentTripRef.child("daysList").setValue("none");
        currentTripRef.child("bonusTravel").setValue(trip.getBonusTravel());
        currentTripRef.child("departure").setValue(trip.getDeparture());
        currentTripRef.child("food").setValue(trip.getFood());
        currentTripRef.child("lodging").setValue(trip.getLodging());
        currentTripRef.child("activity").setValue(trip.getActivity());
        currentTripRef.child("transport").setValue(trip.getTransport());
    }

    public void writeStyle(String style, String id){
        DatabaseReference currentTripRef = usersDatabase.child(currentUserInfo.getid()).child("tripsList").child(id);

        if(style.equals("Aventurier")){
            currentTripRef.child("tripStyle").setValue("Aventurier");
            currentTripRef.child("food").setValue(20);
            currentTripRef.child("lodging").setValue(15);
            currentTripRef.child("activity").setValue(40);
            currentTripRef.child("transport").setValue(25);
        }else if(style.equals("Culinaire")){
            currentTripRef.child("tripStyle").setValue("Culinaire");
            currentTripRef.child("food").setValue(45);
            currentTripRef.child("lodging").setValue(20);
            currentTripRef.child("activity").setValue(20);
            currentTripRef.child("transport").setValue(15);
        }else if(style.equals("Comfortable")){
            currentTripRef.child("tripStyle").setValue("Comfortable");
            currentTripRef.child("food").setValue(30);
            currentTripRef.child("lodging").setValue(40);
            currentTripRef.child("activity").setValue(20);
            currentTripRef.child("transport").setValue(10);
        }else if(style.equals("Par défaut")){
            currentTripRef.child("tripStyle").setValue("default");
            currentTripRef.child("food").setValue(35);
            currentTripRef.child("lodging").setValue(30);
            currentTripRef.child("activity").setValue(20);
            currentTripRef.child("transport").setValue(15);
        }

    }
}
