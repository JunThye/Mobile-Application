package com.example.asgn1ngjunthye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.StringTokenizer;

public class AddEventActivity extends AppCompatActivity {
    EditText eventID, categoryID_Event, eventName, tickets;
    Switch isActive;
    String eventIDStr, eventNameStr,categoryIDStr;
    Boolean isActiveBool;
    int ticketCount;
    private EventBroadCastReceiver eventBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventID = findViewById(R.id.idInput);
        categoryID_Event = findViewById(R.id.categoryIdInput);
        eventName = findViewById(R.id.eventNameInput);
        tickets = findViewById(R.id.ticketAvailableInput);
        isActive = findViewById(R.id.activeEventSwitch);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);
        eventBroadCastReceiver = new EventBroadCastReceiver();
        registerReceiver(eventBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_INPUT));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(eventBroadCastReceiver);
    }
    int counter = 10000;
    public void saveEventOnClicked (View view) {
        categoryIDStr = categoryID_Event.getText().toString();
        eventNameStr = eventName.getText().toString();
        isActiveBool = isActive.isChecked();

        eventIDStr = "EME-"+counter;
        counter ++;

        if (!categoryIDStr.isEmpty() && !eventNameStr.isEmpty()){
            if (!tickets.getText().toString().isEmpty()){
                ticketCount = Integer.parseInt(tickets.getText().toString());
            }
            else {
                ticketCount = 0;
            }
            saveEventData(eventIDStr,categoryIDStr,eventNameStr,ticketCount,isActiveBool);
            eventID.setText(eventIDStr);
            String msg = "Event saved: " + eventIDStr+" to "+categoryIDStr;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(this, "Category ID and Event Name must not be empty", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveEventData (String eventIDVal,String cateIDVal, String eventNameVal, int ticketCountVal, Boolean activeStatus){

        SharedPreferences sharedPreferences = getSharedPreferences("Event",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("EventID",eventIDVal);
        editor.putString("CategoryID", cateIDVal);
        editor.putString("EventName", eventNameVal);
        editor.putInt("Tickets", ticketCountVal);
        editor.putBoolean("isActive",activeStatus);

        editor.apply();
    }
    class EventBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String msg = intent.getStringExtra(SMSReceiver.SMS_CONTENT);
                StringTokenizer originalMsg = new StringTokenizer(msg, ":");
                String eventTxt = originalMsg.nextToken();
                if (!eventTxt.equals("event")) {
                    throw new Exception();
                }
                String remainingData = originalMsg.nextToken();
                StringTokenizer data = new StringTokenizer(remainingData,";");
                if (data.countTokens() != 4) {
                    throw new Exception();
                }
                String name = data.nextToken();
                String id = data.nextToken();
                String ticketCount = data.nextToken();
                int num = Integer.parseInt(ticketCount);
                if(num < 0) {
                    throw new Exception();
                }
                String active = data.nextToken();
                if ( !active.equals("TRUE") && !active.equals("FALSE") ) {
                    throw new Exception();
                }
                Boolean activeBool = Boolean.parseBoolean(active);
                eventName.setText(name);
                categoryID_Event.setText(id);
                tickets.setText(ticketCount);
                isActive.setChecked(activeBool);
            }
            catch (Exception e) {
                Toast.makeText(context, "Unknown or Invalid Command", Toast.LENGTH_SHORT).show();
            }
        }
    }
}