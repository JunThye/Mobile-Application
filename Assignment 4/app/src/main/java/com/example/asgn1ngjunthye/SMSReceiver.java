package com.example.asgn1ngjunthye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS_INPUT = "SMS_INPUT";
    public static final String SMS_CONTENT = "SMS_CONTENT";

    @Override
    public void onReceive(Context context, Intent intent) {

        SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (int i = 0; i < smsMessages.length; i++) {
            SmsMessage currentMessage = smsMessages[i];
            String content = currentMessage.getDisplayMessageBody();

            Intent msgIntent = new Intent();
            msgIntent.setAction(SMS_INPUT);
            msgIntent.putExtra(SMS_CONTENT, content);
            context.sendBroadcast(msgIntent);
        }
    }
}