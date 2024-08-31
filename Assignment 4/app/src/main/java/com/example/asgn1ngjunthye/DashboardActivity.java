package com.example.asgn1ngjunthye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asgn1ngjunthye.provider.EMADatabase;
import com.example.asgn1ngjunthye.provider.EMAViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {
    private EMAViewModel emaViewModel;
    private GestureDetectorCompat detector;
    Toolbar toolbar;
    EditText eventID2, categoryID_Event2, eventName2, tickets2;
    TextView gestureTxt;
    Switch isActive2;
    String eventIDStr2, eventNameStr2,categoryIDStr2;
    Boolean isActiveBool2;
    int ticketCount2;
    DrawerLayout drawerlayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        eventID2 = findViewById(R.id.eventIDInput2);
        categoryID_Event2 = findViewById(R.id.categoryIDInput2);
        eventName2 = findViewById(R.id.eventNameInput2);
        tickets2 = findViewById(R.id.ticketsAvailableInput2);
        isActive2 = findViewById(R.id.isActive2);
        gestureTxt = findViewById(R.id.gestureTxt);
        // Drawer
        drawerlayout = findViewById(R.id.drawer_layout);
        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assignment 2");
        // Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        // FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventVerification(view);
            }
        });
        emaViewModel = new ViewModelProvider(this).get(EMAViewModel.class);
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        detector = new GestureDetectorCompat(this, customGestureDetector);
        detector.setOnDoubleTapListener(customGestureDetector);
        View touchpad = findViewById(R.id.touchpad);
        touchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });


    }

    // 3 DOT OPTIONS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dot_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clearEventForm) {

            eventID2.setText("");
            categoryID_Event2.setText("");
            eventName2.setText("");
            tickets2.setText("");
            isActive2.setChecked(false);

        } else if (item.getItemId() == R.id.deleteAllCategories) {
            emaViewModel.deleteAllCategory();
        } else if (item.getItemId() == R.id.deleteAllEvents) {
            emaViewModel.deleteAllEvent();
        }
        return true;
    }
    public void eventVerification (View view) {
        categoryIDStr2 = categoryID_Event2.getText().toString();
        eventNameStr2 = eventName2.getText().toString();
        isActiveBool2 = isActive2.isChecked();
        Random random = new Random();
        int counter = random.nextInt(90000) + 10000;
        char catname = (char) (random.nextInt(26) + 'A');
        char catname2 = (char) (random.nextInt(26) + 'A');
        eventIDStr2 = "E"+catname+catname2+"-"+counter;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler uiHandler=new Handler(Looper.getMainLooper());

        if (!categoryIDStr2.isEmpty() && !eventNameStr2.isEmpty() ){
            if (eventNameStr2.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9 ]*$")){
                // ticket count
                if (!tickets2.getText().toString().isEmpty()){
                    ticketCount2 = Integer.parseInt(tickets2.getText().toString());
                }
                else {
                    ticketCount2 = 0;
                }
                if (ticketCount2 < 0){
                    Toast.makeText(this, "Invalid 'Tickets available'", Toast.LENGTH_SHORT).show();
                } else {
                    executor.execute(() -> {
                        List<Category> categoryList = emaViewModel.getCategory(categoryIDStr2);
                        uiHandler.post(() -> {
                            if (categoryList == null || categoryList.isEmpty()) {
                                Toast.makeText(this, "Category does not exist", Toast.LENGTH_SHORT).show();
                            } else {
                                saveEventData2(eventIDStr2,categoryIDStr2,eventNameStr2,ticketCount2,isActiveBool2);
                                Snackbar.make(view, "Event Successfully Saved", Snackbar.LENGTH_LONG)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {}
                                        }).show();
                                eventID2.setText(eventIDStr2);
                                String msg = "Event saved: " + eventIDStr2+" to "+categoryIDStr2;
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                }
            } else {
                Toast.makeText(this, "Invalid event name", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Category ID and Event Name must not be empty", Toast.LENGTH_SHORT).show();
        }
    }
    public void saveEventData2 (String eventIDVal,String cateIDVal, String eventNameVal, int ticketCountVal, Boolean activeStatus){
        Event event = new Event(eventIDVal,eventNameVal,cateIDVal,ticketCountVal,activeStatus);
        // update count using cateIDVal
        emaViewModel.insertEvent(event);
        emaViewModel.increaseCount(cateIDVal);
    }

//    public void removeLast(){
//        Gson gson = new Gson();
//        SharedPreferences sharedPreferences = getSharedPreferences("Event", Context.MODE_PRIVATE);
//        SharedPreferences catSP = getSharedPreferences("Category",MODE_PRIVATE);
//        String arrayListStringRestored = sharedPreferences.getString("data", "[]");
//        Type type = new TypeToken<ArrayList<Event>>() {}.getType();
//        eventList = gson.fromJson(arrayListStringRestored,type);
//        Event eventToBeRemoved = eventList.get(eventList.size() - 1);
//        if (!eventList.isEmpty()) {
//            eventList.remove(eventList.size() - 1);
//        }
//        String arrayListString = gson.toJson(eventList);
//
//        String Idlist = catSP.getString("IDS" ,"[]");
//        Type strType = new TypeToken<ArrayList<String>>() {}.getType();
//        catIDList = gson.fromJson(Idlist,strType);
//        int index = catIDList.indexOf(eventToBeRemoved.getCategoryID());
//        Category category = catList.get(index);
//        category.setEventCount(category.getEventCount()-1);
//        catList.set(index,category);
//
//        String newCatList = gson.toJson(catList);
//        SharedPreferences.Editor cateditor = catSP.edit();
//        cateditor.putString("data",newCatList);
//        cateditor.apply();
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("data",arrayListString);
//        editor.apply();
//    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.viewAllCategories) {
                Intent allCategory = new Intent(DashboardActivity.this, viewAllCategory.class);
                startActivity(allCategory);
            }
            else if (id == R.id.addCategory) {
                Intent newEvent = new Intent(DashboardActivity.this, NewEventCategoryActivity.class);
                startActivity(newEvent);
            }
            else if (id == R.id.viewAllEvents) {
                Intent allEvents = new Intent(DashboardActivity.this, viewAllEvents.class);
                startActivity(allEvents);
            }
            else if (id == R.id.logout) {
                Intent main = new Intent(DashboardActivity.this, LogInActivity.class);
                startActivity(main);
            }
            drawerlayout.closeDrawers();
            return true;
        }
    }
    private class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            gestureTxt.setText("onLongPress");
            eventID2.setText("");
            categoryID_Event2.setText("");
            eventName2.setText("");
            tickets2.setText("");
            isActive2.setChecked(false);
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            gestureTxt.setText("onDoubleTap");
            eventVerification(DashboardActivity.this.getCurrentFocus());
            return true;
        }
    }
}