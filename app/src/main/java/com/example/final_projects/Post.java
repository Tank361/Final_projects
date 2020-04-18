package com.example.final_projects;

public class Post {
    public String day;
    public String description;
    public String high;
    public String low;
    public String precip;
    public String humidity;

    public Post(String day, String description, String high, String low, String precip, String humidity) {
        this.day = day;
        this.description = description;
        this.high = high;
        this.low = low;
        this.precip = precip;
        this.humidity = humidity;
    }
}