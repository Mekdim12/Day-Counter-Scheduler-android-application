package com.example.daycount_downer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

public class settingsPage extends AppCompatActivity {

    ImageView backbutton,backButton;
    ImageButton CalanderChossing;
    String Date,time;
    EditText DateInserting,tittleofthevent;
    Switch switch1;
    boolean isChecked = true;
    SharedPreferences information;
    Button settingSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        information =  getApplicationContext().getSharedPreferences("",MODE_PRIVATE);

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

                startActivity(new Intent(settingsPage.this, MainActivity.class));

            }
        });



    }
}