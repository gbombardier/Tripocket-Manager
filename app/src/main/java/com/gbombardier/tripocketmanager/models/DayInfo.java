package com.gbombardier.tripocketmanager.models;

public class DayInfo {
    private float foodSpent, activitiesSpent, lodgingSpent, travelSpent;

    public DayInfo(float foodSpent, float activitiesSpent, float lodgingSpent, float travelSpent) {
        this.foodSpent = foodSpent;
        this.activitiesSpent = activitiesSpent;
        this.lodgingSpent = lodgingSpent;
        this.travelSpent = travelSpent;
    }

    public float getFoodSpent() {
        return foodSpent;
    }

    public void setFoodSpent(float foodSpent) {
        this.foodSpent = foodSpent;
    }

    public float getActivitiesSpent() {
        return activitiesSpent;
    }

    public void setActivitiesSpent(float activitiesSpent) {
        this.activitiesSpent = activitiesSpent;
    }

    public float getLodgingSpent() {
        return lodgingSpent;
    }

    public void setLodgingSpent(float lodgingSpent) {
        this.lodgingSpent = lodgingSpent;
    }

    public float getTravelSpent() {
        return travelSpent;
    }

    public void setTravelSpent(float travelSpent) {
        this.travelSpent = travelSpent;
    }
}
