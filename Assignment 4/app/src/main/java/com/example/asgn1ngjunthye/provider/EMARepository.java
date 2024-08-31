package com.example.asgn1ngjunthye.provider;


import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.asgn1ngjunthye.Category;
import com.example.asgn1ngjunthye.Event;

import java.util.List;

public class EMARepository {

    private EMADAO emaDAO;
    private LiveData<List<Category>> allCategoryLiveData;
    private LiveData<List<Event>> allEventLiveData;

    EMARepository(Application application) {
        EMADatabase db = EMADatabase.getDatabase(application);

        emaDAO = db.emaDAO();

        allCategoryLiveData = emaDAO.getAllCategory();
        allEventLiveData = emaDAO.getAllEvent();
    }

    /**
     * Repository method to get all category
     * @return LiveData of type List<Category>
     */
    LiveData<List<Category>> getAllCategory() {
        return allCategoryLiveData;
    }
    /**
     * Repository method to get all event
     * @return LiveData of type List<Event>
     */
    LiveData<List<Event>> getAllEvent() {
        return allEventLiveData;
    }

    /**
     * Repository method to insert one single category
     * @param category object containing details of new Category to be inserted
     */
    void insertCategory(Category category) {
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.addCategory(category));
    }
    /**
     * Repository method to insert one single event
     * @param event object containing details of new Event to be inserted
     */
    void insertEvent(Event event) {
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.addEvent(event));
    }

    void deleteAllCategory(){
        EMADatabase.databaseWriteExecutor.execute(()->{
            emaDAO.deleteAllCategory();
        });
    }

    void deleteAllEvent(){
        EMADatabase.databaseWriteExecutor.execute(()->{
            emaDAO.deleteAllEvents();
        });
    }
    List<Category> getCategory (String id){
        return emaDAO.getCategory(id);
    }
    void increaseCount(String id){
        EMADatabase.databaseWriteExecutor.execute(()->{
            emaDAO.increaseCount(id);
        });
    }
}

