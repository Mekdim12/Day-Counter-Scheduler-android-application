package com.example.daycount_downer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class settingsPage extends AppCompatActivity {

    ImageView backbutton,backButton;
    ImageButton CalanderChossing;
    String Date,time;
    EditText DateInserting,tittleofthevent;
    Switch switch1;
    boolean isChecked = true;
    SharedPreferences information;
    Button settingSchedule;
    Calendar calendar;

    public AlarmManager alarmManager;
    public PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        information =  getApplicationContext().getSharedPreferences("",MODE_PRIVATE);
        createNotificationChanne1();
        backbutton = findViewById(R.id.backButton);
        tittleofthevent = findViewById(R.id.tittleofthevent);
        CalanderChossing = findViewById(R.id.CalanderChossing);
        DateInserting = findViewById(R.id.DateInserting);
        switch1 = (Switch) findViewById(R.id.switch1);
        settingSchedule = (Button) findViewById(R.id.settingSchedule);

        Intent intent = getIntent();

        if (intent.hasExtra("Date") &&  intent.hasExtra("Time")) {

            Date = intent.getStringExtra("Date");
            time = intent.getStringExtra("Time");
            DateInserting.setText(Date+"/"+time);
        }





        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsPage.this, MainActivity.class));
            }
        });

        CalanderChossing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsPage.this, dateAndTimePicker.class));

            }
        });


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    isChecked = true;
                } else {
                    // The toggle is disabled
                    isChecked = false;
                }
            }
        });

        settingSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                information.edit().putString("Date",Date).apply();
                information.edit().putString("Time",time).apply();
                information.edit().putString("Tittle",tittleofthevent.getText().toString().trim()).apply();
                information.edit().putBoolean("Reminder",isChecked).apply();


                if(isChecked){
                    // setReminder
                    // first check for previous schedule and cancel them
                    reminderUNscheduler();



//                    calendar = Calendar.getInstance();
//                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
//                    calendar.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[0]));
//                    calendar.set(Calendar.SECOND, 0);
//                    calendar.set(Calendar.MILLISECOND, 0);
//                    calendar.set(Calendar.YEAR, Integer.parseInt(Date.split("-")[2]));
//                    calendar.set(Calendar.MONTH, Integer.parseInt(Date.split("-")[1]));
//                    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(Date.split("-")[0]));
//                    calendar.set(Calendar.AM_PM,Calendar.AM);

                    reminderSetting();
                }



                startActivity(new Intent(settingsPage.this, MainActivity.class));

            }
        });

    }
    public void reminderUNscheduler(){
        Intent intent = new Intent(this, ReminderReciver.class);
        pendingIntent = PendingIntent.getBroadcast( this,0,intent,0 );

        if(alarmManager == null){
            alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);

    }

    public void reminderSetting(){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, ReminderReciver.class);
            pendingIntent = PendingIntent.getBroadcast( this,0,intent,0 );

            LocalDateTime finaldatee = LocalDateTime.parse(Date+" "+time+":00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss") );
            long millis = finaldatee.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();


            alarmManager.setInexactRepeating(alarmManager.RTC_WAKEUP,millis,AlarmManager.INTERVAL_HALF_HOUR,pendingIntent);

            Toast.makeText(this, "Reminder Scheduled!", Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChanne1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Schedule Reminder";
            String description = "Your Schedule Events just arrives Now! Don't Miss Out ";
            int importance  = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("countdown" ,name ,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);

        }

    }
}