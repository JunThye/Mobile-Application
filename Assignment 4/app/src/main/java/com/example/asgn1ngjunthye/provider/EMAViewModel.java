package com.example.asgn1ngjunthye.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.asgn1ngjunthye.Category;
import com.example.asgn1ngjunthye.Event;

import java.util.List;

public class EMAViewModel extends AndroidViewModel {
    private EMARepository repository;
    private LiveData<List<Category>> allCategoryLiveData;
    private LiveData<List<Event>> allEventLiveData;

    public EMAViewModel(@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new EMARepository(application);

        // get all items by calling method defined in repository class
        allCategoryLiveData = repository.getAllCategory();
        allEventLiveData = repository.getAllEvent();
    }

    /**
     * ViewModel method to get all category
     * @return LiveData of type List<Category>
     */
    public LiveData<List<Category>> getAllCategory() {
        return allCategoryLiveData;
    }
    /**
     * ViewModel method to get all event
     * @return LiveData of type List<Event>
     */
    public LiveData<List<Event>> getAllEvent() {
        return allEventLiveData;
    }

    /**
     * ViewModel method to insert one single category,
     * usually calling insert method defined in repository class
     * @param category object containing details of new Category to be inserted
     */
    public void insertCategory(Category category) {
        repository.insertCategory(category);
    }
    /**
     * ViewModel method to insert one single event,
     * usually calling insert method defined in repository class
     * @param event object containing details of new Event to be inserted
     */
    public void insertEvent(Event event) {
        repository.insertEvent(event);
    }
    public void deleteAllCategory(){
        repository.deleteAllCategory();
    }
    public void deleteAllEvent(){repository.deleteAllEvent();}
    public List<Category> getCategory(String id) {return repository.getCategory(id);}
    public void increaseCount(String id) {repository.increaseCount(id);}

}
