package com.example.asgn1ngjunthye;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "CategoryID")
    private String categoryID;
    @ColumnInfo(name = "CategoryName")
    private String categoryName;
    @ColumnInfo(name = "EventCount")
    private int eventCount;
    @ColumnInfo(name = "IsActive")
    private boolean isActive;
    @ColumnInfo(name = "Location")
    private String location;

    public Category(String categoryID, String categoryName, int eventCount, boolean isActive, String location) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.isActive = isActive;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getEventCount() {
        return eventCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
