package com.example.tarik.triggerwordsv1;

import android.*;
import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Calendar;

/**
 * Created by huanghe on 4/04/2017.
 */

public class Notification extends AppCompatActivity {

    private Button button3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         ContextCompat.checkSelfPermission(this,
                Manifest.permission.SET_ALARM);
        setContentView(R.layout.notification_activity);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,10);
                calendar.set(Calendar.MINUTE,56);
                calendar.set(Calendar.SECOND,12);
                Intent intent = new Intent(getApplicationContext(), Notification_receiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


            }
        });


    }
}