package com.gbombardier.tripocketmanager.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public class Trip implements Serializable{
    private String id;
    private String destination;
    private float mainPlaneCost;
    private float mainPlaneDays;
    private int totalTripDays;
    private String tripStyle;
    private int remainingDays;
    private float totalBudget;
    private float remainingMoney;
    private Vector<DaysInfos> daysList = new Vector<>();
    private float bonusTravel;
    private String departure;
    private float food, lodging, activity, transport;

    public Trip() {
    }

    public Trip(String destination) {
        this.destination = destination;
    }

    public Trip(String destination, float mainPlaneCost, float mainPlaneDays, int totalTripDays, float totalBudget) {
        this.destination = destination;
        this.mainPlaneCost = mainPlaneCost;
        this.mainPlaneDays = mainPlaneDays;
        this.totalTripDays = totalTripDays;
        this.totalBudget = totalBudget;
    }

    public Trip(String destination, float mainPlaneCost, float mainPlaneDays, int totalTripDays, String tripStyle, int remainingDays, float totalBudget, float remainingMoney, Vector<DaysInfos> daysList, float bonusTravel) {
        this.destination = destination;
        this.mainPlaneCost = mainPlaneCost;
        this.mainPlaneDays = mainPlaneDays;
        this.totalTripDays = totalTripDays;
        this.tripStyle = tripStyle;
        this.remainingDays = remainingDays;
        this.totalBudget = totalBudget;
        this.remainingMoney = remainingMoney;
        this.daysList = daysList;
        this.bonusTravel = bonusTravel;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getMainPlaneCost() {
        return mainPlaneCost;
    }

    public void setMainPlaneCost(float mainPlaneCost) {
        this.mainPlaneCost = mainPlaneCost;
    }

    public float getMainPlaneDays() {
        return mainPlaneDays;
    }

    public void setMainPlaneDays(float mainPlaneDays) {
        this.mainPlaneDays = mainPlaneDays;
    }

    public int getTotalTripDays() {
        return totalTripDays;
    }

    public void setTotalTripDays(int totalTripDays) {
        this.totalTripDays = totalTripDays;
    }

    public String getTripStyle() {
        return tripStyle;
    }

    public void setTripStyle(String tripStyle) {
        this.tripStyle = tripStyle;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public float getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(float totalBudget) {
        this.totalBudget = totalBudget;
    }

    public float getRemainingMoney() {
        return remainingMoney;
    }

    public void setRemainingMoney(float remainingMoney) {
        this.remainingMoney = remainingMoney;
    }

    @Exclude
    public Vector<DaysInfos> getDaysList() {
        return daysList;
    }

    public void setDaysList(Vector<DaysInfos> daysList) {
        this.daysList = daysList;
    }

    public float getBonusTravel() {
        return bonusTravel;
    }

    public void setBonusTravel(float bonusTravel) {
        this.bonusTravel = bonusTravel;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public float getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public float getLodging() {
        return lodging;
    }

    public void setLodging(int lodging) {
        this.lodging = lodging;
    }

    public float getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public float getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    public void addDay(DaysInfos day){
        daysList.add(day);
    }

    public void eraseList(){
        daysList.clear();
    }
}
