package com.example.todoapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shreyansh on 20/5/17.
 */

public class Task implements Parcelable {
    private String Title,Description,Date;
    boolean completed;
    int id;

    Task(){
        Title = "";
        Description = "";
        Date = "";
        completed = false;
        id=0;
    }

    Task(String t,String d,String da){
        Title = t;
        Description = d;
        Date = da;
        completed = false;
        id=0;
    }
    String getTitle(){
        return Title;
    }
    String getDescription(){
        return Description;
    }
    String getDate(){
        return Date;
    }
    int getId(){
        return id;
    }
    void toggleCompleted(){
        completed = !completed;
    }
    int isCompleted(){
        return (completed?1:0);
    }
    void setTitle(String Title){
        this.Title = Title;
    }
    void setDescription(String Description){
        this.Description = Description;
    }
    void setDate(String Date){
        this.Date = Date;
    }
    void setCompleted(boolean completed){
        this.completed = completed;
    }
    void setId(int id){
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
