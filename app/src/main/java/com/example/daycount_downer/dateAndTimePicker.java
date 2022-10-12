package com.example.daycount_downer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

public class dateAndTimePicker extends AppCompatActivity {

    ImageView backButton;
    TimePicker simpleTimePicker;
    DatePicker datePicker;
    Button setDateandTime;
    String fullDate,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_and_time_picker);


        backButton = findViewById(R.id.backButton);
        simpleTimePicker = (TimePicker) findViewById(R.id.timepciker);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        setDateandTime =(Button) findViewById(R.id.setDateandTime);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dateAndTimePicker.this, settingsPage.class));
            }
        });

        simpleTimePicker.setIs24HourView(true); // used to display AM/PM mode
        // perform set on time changed listener event
        simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                time = hourOfDay+":"+minute;
            }
        });


        setDateandTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dateAndTimePicker.this, settingsPage.class);

                intent.putExtra("Date",getCurrentDate());
                if(time == null || time.equals(""))
                    time = "";
                intent.putExtra("Time",time);

                startActivity(intent);

            }
        });



    }


    public String getCurrentDate(){
        StringBuilder builder=new StringBuilder();
        builder.append(datePicker.getDayOfMonth()+"-");
        builder.append((datePicker.getMonth() + 1)+"-");//month is 0 based
        builder.append(datePicker.getYear());
        return builder.toString();
    }

}