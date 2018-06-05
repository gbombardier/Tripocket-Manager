package com.gbombardier.tripocketmanager.models;

import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class DaysInfos implements Serializable{
    private float food, activity, lodging, transport;
    private String id;
    private String date = "";
    private ArrayList<Expense> expenses;
    private String title = "";

    public DaysInfos(){
        this.food = 0;
        this.activity = 0;
        this.lodging = 0;
        this.transport = 0;

        Date dateReal = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateReal = format.parse(dateReal.toString());
            date = dateReal.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        expenses = new ArrayList<Expense>();
    }

    public DaysInfos(float foodSpent, float activitiesSpent, float lodgingSpent, float travelSpent) {
        this.food = foodSpent;
        this.activity = activitiesSpent;
        this.lodging = lodgingSpent;
        this.transport = travelSpent;
    }

    public DaysInfos(float foodSpent, float activitiesSpent, float lodgingSpent, float travelSpent, String id, String date, ArrayList<Expense> expenses) {
        this.food = foodSpent;
        this.activity = activitiesSpent;
        this.lodging = lodgingSpent;
        this.transport = travelSpent;
        this.id = id;
        this.date = date;
        this.expenses = expenses;
    }

    public float getFood() {
        return food;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public float getActivity() {
        return activity;
    }

    public void setActivity(float activity) {
        this.activity = activity;
    }

    public float getLodging() {
        return lodging;
    }

    public void setLodging(float lodging) {
        this.lodging = lodging;
    }

    public float getTransport() {
        return transport;
    }

    public void setTransport(float transport) {
        this.transport = transport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getCategoryValue(String category){
        if(category.equals("food")){
            return this.food;
        }else if(category.equals("activity")){
            return this.activity;
        }else if(category.equals("transport")){
            return this.transport;
        }else{
            return this.lodging;
        }
    }
}
