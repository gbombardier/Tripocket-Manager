package com.gbombardier.tripocketmanager.models;

/**
 * Created by gabb_ on 2018-01-08.
 */

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gabb on 2017-10-01.
 */
public class User {
    private String id;
    private String email;
    private List<Trip> tripsList;

    public User() {

    }

    public User(String email){
        this.email = email;
    }

    public User(String id, String email){
        this.id = id;
        this.email = email;
        this.tripsList = new ArrayList<Trip>();
    }

    public User(String id, String email, List<Trip> tripList){
        this.id = id;
        this.email = email;
        this.tripsList = tripList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getid() {
        return id;
    }

    public void setid(String id){
        this.id = id;
    }

    @Exclude
    public List<Trip> getTripsList() {
        return tripsList;
    }

    public void setTripsList(List<Trip> tripList) {
        this.tripsList = tripList;
    }

    public void addTrip(Trip trip){
        this.tripsList.add(trip);
    }
}

