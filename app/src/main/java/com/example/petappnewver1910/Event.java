package com.example.petappnewver1910;

import java.util.Date;

public class Event {

    public String description;
    public String address;
    public String date;
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Event() {

    }
    public Event(String description, String address, String date) {
        this.description = description;
        this.date = date;
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
