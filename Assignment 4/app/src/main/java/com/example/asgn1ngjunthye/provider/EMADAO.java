package com.example.asgn1ngjunthye.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.asgn1ngjunthye.Category;
import com.example.asgn1ngjunthye.Event;

import java.util.List;

@Dao
public interface EMADAO {
    @Query("select * from categories")
    LiveData<List<Category>> getAllCategory();

    @Insert
    void addCategory(Category category);

    @Query("select * from events")
    LiveData<List<Event>> getAllEvent();

    @Insert
    void addEvent(Event event);

    @Query("delete FROM categories")
    void deleteAllCategory();

    @Query("delete FROM events")
    void deleteAllEvents();

    @Query("select * FROM categories where CategoryID=:id")
    List<Category> getCategory(String id);

    @Query("update categories set EventCount = EventCount + 1 where CategoryID=:id")
    void increaseCount (String id);
}
