package com.gbombardier.tripocketmanager.models;

import java.io.Serializable;

public class Expense implements Serializable {
    private String category, title;
    private float value;
    private String id;

    public Expense(){
        this.category = "none";
        this.title = "none";
        this.value = 0;
        this.id = "";
    }
    public Expense(String category, String title, float value, String id) {
        this.category = category;
        this.title = title;
        this.value = value;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
