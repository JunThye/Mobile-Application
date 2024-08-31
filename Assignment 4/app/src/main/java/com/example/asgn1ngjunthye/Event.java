package com.example.asgn1ngjunthye;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "EventID")
    private String eventID;
    @ColumnInfo(name = "EventName")
    private String eventName;
    @ColumnInfo(name = "CategoryID")
    private String categoryID;
    @ColumnInfo(name = "TicketsAvailable")
    private int ticketsAvailable;
    @ColumnInfo(name = "IsActive")
    private boolean isActive;

    public Event(String eventID, String eventName, String categoryID, int ticketsAvailable, boolean isActive) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.categoryID = categoryID;
        this.ticketsAvailable = ticketsAvailable;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
