package com.example.asgn1ngjunthye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.asgn1ngjunthye.provider.EMAViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class NewEventCategoryActivity extends AppCompatActivity {
    private EMAViewModel emaViewModel;

    EditText categoryID, categoryName, categoryEventCount,location;
    Switch isActive;
    String categoryNameStr,categoryIDStr,locationStr;
    Boolean isActiveBool;
    int eventCountInt;
//    private CategoryBroadCastReceiver categoryBroadCastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);
        location = findViewById(R.id.locationInput);
        categoryID = findViewById(R.id.categoryInput);
        categoryName = findViewById(R.id.nameInput);
        categoryEventCount = findViewById(R.id.countInput);
        isActive = findViewById(R.id.activeSwitch);
//        ActivityCompat.requestPermissions(this, new String[]{
//                android.Manifest.permission.SEND_SMS,
//                android.Manifest.permission.RECEIVE_SMS,
//                android.Manifest.permission.READ_SMS
//        }, 0);
//        categoryBroadCastReceiver = new CategoryBroadCastReceiver();
//        registerReceiver(categoryBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_INPUT));
        emaViewModel = new ViewModelProvider(this).get(EMAViewModel.class);
    }

    public void SaveCategoryOnClicked (View view) {
        categoryNameStr = categoryName.getText().toString();
        isActiveBool = isActive.isChecked();
        locationStr = location.getText().toString();
        Random random = new Random();
        int counter = random.nextInt(9000) + 1000;
        char catname = (char) (random.nextInt(26) + 'A');
        char catname2 = (char) (random.nextInt(26) + 'A');
        categoryIDStr = "C"+catname+catname2+"-"+counter;

        if (!categoryNameStr.isEmpty()){
            if (categoryNameStr.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9 ]*$")){
                if (!categoryEventCount.getText().toString().isEmpty()) {
                    eventCountInt = Integer.parseInt(categoryEventCount.getText().toString());
                }
                else {
                    eventCountInt = 0;
                }
                if (eventCountInt < 0 ) {
                    Toast.makeText(this,"Invalid Event Count'", Toast.LENGTH_SHORT).show();
                } else {
                    saveCategoryData(categoryIDStr,categoryNameStr,eventCountInt,isActiveBool,locationStr);
                    categoryID.setText(categoryIDStr);
                    String msg = "Category saved successfully: " + categoryIDStr;
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Invalid category name", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Category Name must not be empty", Toast.LENGTH_SHORT).show();
        }
        onBackPressed();

    }

    public void saveCategoryData (String idValue, String nameValue, int countValue, Boolean activeStatus,String location) {
        Category category = new Category(idValue, nameValue, countValue, activeStatus,location);
        emaViewModel.insertCategory(category);

    }
//    class CategoryBroadCastReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//                String msg = intent.getStringExtra(SMSReceiver.SMS_CONTENT);
//                StringTokenizer originalMsg = new StringTokenizer(msg, ":");
//                String categoryTxt = originalMsg.nextToken();
//                if (!categoryTxt.equals("category")){
//                    throw new Exception();
//                }
//                String remainingData = originalMsg.nextToken();
//                StringTokenizer data = new StringTokenizer(remainingData,";");
//                if (data.countTokens() != 3) {
//                    throw new Exception();
//                }
//                String name = data.nextToken();
//                String count = data.nextToken();
//                int num = Integer.parseInt(count);
//                if(num < 0) {
//                    count = "0";
//                }
//                String active = data.nextToken();
//                if ( !active.equals("TRUE") && !active.equals("FALSE") ) {
//                    throw new Exception();
//                }
//                boolean activeBool = Boolean.parseBoolean(active);
//                categoryName.setText(name);
//                categoryEventCount.setText(count);
//                isActive.setChecked(activeBool);
//            }
//            catch (Exception e) {
//                Toast.makeText(context, "Unknown or Invalid Command", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}

