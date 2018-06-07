package com.gbombardier.tripocketmanager.database;

import android.content.Context;
import android.widget.Toast;

import com.gbombardier.tripocketmanager.models.DaysInfos;
import com.gbombardier.tripocketmanager.models.Expense;
import com.gbombardier.tripocketmanager.models.Trip;
import com.gbombardier.tripocketmanager.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

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
    private int createdDays = 1;

    private DatabaseProfile(final Context context) {
        this.context = context;

        if(persEnabled == false){
            persEnabled = true;
            FirebaseDatabase.getInstance().setPersistenceEnabled(false);
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
            //
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
        currentTripRef.child("bonusTravel").setValue(trip.getBonusTravel());
        currentTripRef.child("departure").setValue(trip.getDeparture());
        currentTripRef.child("food").setValue(trip.getFood());
        currentTripRef.child("lodging").setValue(trip.getLodging());
        currentTripRef.child("activity").setValue(trip.getActivity());
        currentTripRef.child("transport").setValue(trip.getTransport());

        //Ajouter des daysinfo pour toutes les day depuis le départ
        Date date = new Date();
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(trip.getDeparture());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Modifier si le budget est créé avant le départ
        float daysToCreate =  (now.getTime()-date.getTime())/(1000*60*60*24);

        for(int i = 0; i<=daysToCreate;i++){
            if(i==0){
                writeDay(key, trip, true, trip.getDeparture());
            }else{
                String dt = trip.getDeparture();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(dt));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, i);
                dt = sdf.format(c.getTime());
                writeDay(key, trip, false, dt);
            }
            createdDays++;
        }
    }

    public String writeDay(String key, Trip trip, boolean create, String dayDate){
        //Pour les daysList
        DatabaseReference currentTripRef = usersDatabase.child(currentUserInfo.getid()).child("tripsList").child(key);
        String keyDays =  currentTripRef.child("daysList").push().getKey();

        currentTripRef.child("daysList").child(keyDays).child("food").setValue(0);
        currentTripRef.child("daysList").child(keyDays).child("lodging").setValue(0);
        currentTripRef.child("daysList").child(keyDays).child("activity").setValue(0);
        currentTripRef.child("daysList").child(keyDays).child("transport").setValue(0);
        currentTripRef.child("daysList").child(keyDays).child("id").setValue(keyDays);

        try{
            createdDays = trip.getDaysList().size()+1;
        }catch(Exception e){
            createdDays = 1;
        }

        if(create){
            currentTripRef.child("daysList").child(keyDays).child("date").setValue(trip.getDeparture());
            currentTripRef.child("daysList").child(keyDays).child("title").setValue("jour 1");
        }else{
            currentTripRef.child("daysList").child(keyDays).child("date").setValue(dayDate);
            String jour = "jour " + (createdDays);
            currentTripRef.child("daysList").child(keyDays).child("title").setValue(jour);
        }

        return keyDays;
    }

    //Pour le modele de date
    public String formatDate(Date date){
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear()+1900;

        String stringMonth, stringDay;
        month++;
        if(month<=9){
            stringMonth = "0"+month;
        }else{
            stringMonth = String.valueOf(month);
        }

        if(day<=9){
            stringDay = "0"+day;
        }else{
            stringDay = String.valueOf(day);
        }

        String dateString = year + "-" + stringMonth + "-" + stringDay;

        return dateString;
    }

    //Modifie le style d'un voyage
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

    //Ajoute une dépense
    public void writeExpense(Expense expense, Trip currentTrip, Vector<DaysInfos> daysList, String dayDate){
        String rank = "";
        String idDay = "";
        boolean found =false;
        DaysInfos day = new DaysInfos();
        DatabaseReference currentTripRef = usersDatabase.child(currentUserInfo.getid()).child("tripsList").child(currentTrip.getid());

        Date date = new Date();
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for(DaysInfos dayInfo : daysList){
            try {
                date = format.parse(dayInfo.getDate());
                now= format.parse(dayDate);

                if(now.equals(date)){
                    idDay = dayInfo.getId();
                    day = dayInfo;
                    rank = String.valueOf(day.getExpenses().size());
                    found = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(!found){
            idDay = writeDay(currentTrip.getid(), currentTrip, false, dayDate);
            rank = "1";
        }

        DatabaseReference currentDayRef = usersDatabase.child(currentUserInfo.getid()).child("tripsList").child(currentTrip.getid()).child("daysList").child(idDay);
        currentDayRef.child(expense.getCategory()).setValue(day.getCategoryValue(expense.getCategory())+expense.getValue());
        DatabaseReference currentExpenseRef = usersDatabase.child(currentUserInfo.getid()).child("tripsList").child(currentTrip.getid()).child("daysList").child(idDay).child("expenses").child(rank);
        currentExpenseRef.child("title").setValue(expense.getTitle());
        currentExpenseRef.child("category").setValue(expense.getCategory());
        currentExpenseRef.child("value").setValue(expense.getValue());

        currentTripRef.child("remainingMoney").setValue(currentTrip.getRemainingMoney()-expense.getValue());
    }
}
