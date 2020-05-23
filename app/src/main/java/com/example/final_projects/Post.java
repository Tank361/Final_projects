package com.example.final_projects;

import android.media.Image;


public class Post {
    public String time;
    public String temp;
    public String description;
    public String humidity;

    //對各元件指派資料型態
    public Post(String time, String temp , String description, String humidity) {
        this.time = time;
        this.temp = temp;
        this.description = description;
        this.humidity = humidity;
    }
}