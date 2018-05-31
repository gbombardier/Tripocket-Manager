package com.gbombardier.tripocketmanager.models;

public class Expense {
    private String category, title;
    private float value;

    public Expense(String category, String title, float value) {
        this.category = category;
        this.title = title;
        this.value = value;
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
}
