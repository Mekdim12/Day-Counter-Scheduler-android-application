package com.example.daycount_downer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView mothLeftHolder,DayHolder,TimeHolder,tittleTheEvent;
    ImageView settingbutton;
    SharedPreferences information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        information =  getApplicationContext().getSharedPreferences("",MODE_PRIVATE);



        mothLeftHolder = findViewById(R.id.monthHolder);
        DayHolder = findViewById(R.id.DayTextViewHolder);
        TimeHolder = findViewById(R.id.HourHolder);
        tittleTheEvent = findViewById(R.id.tittleTheEvent);
        settingbutton = findViewById(R.id.settingbutton);

        Typeface myfont = Typeface.createFromAsset(getApplicationContext().getAssets(),"digitalfont.ttf" );

        mothLeftHolder.setTypeface(myfont);
        DayHolder.setTypeface(myfont);
        TimeHolder.setTypeface(myfont);
        settingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, settingsPage.class);
                startActivity(intent);
            }
        });


        if(information.getString("Date",null) == null ||  information.getString("Time",null) == null ||  information.getString("Tittle",null) == null  ){
            Toast.makeText(this, "at least one of meta data is getting null error", Toast.LENGTH_SHORT).show();
        }else{

                if(information.getString("Tittle",null).equals("")){
                    tittleTheEvent.setText("Untitled");
                }else{
                    tittleTheEvent.setText(information.getString("Tittle",null));
                    Thread thread = new Thread(new forHandlingbackgroundCountDowning(information.getString("Date",null) +" "+information.getString("Time",null)+":00"));
                    thread.start();
                }
        }


    }



    class forHandlingbackgroundCountDowning implements Runnable{

        String FinalDate;
        private volatile boolean running = true;

        public forHandlingbackgroundCountDowning(String FinalDate) {
            this.FinalDate = FinalDate;
        }

        @Override
        public void run() {


                while(running){
                    try {

                        Object [] fetched = usingNewLibraryForSAndroidsGreaterThanEight(FinalDate);

                        Duration duration = (Duration) fetched[0];

                        DayHolder.setText(Long.toString(duration.toDays()) + " DAYS LEFT");
                        long months = duration.toDays() / 30;
                        mothLeftHolder.setText(Long.toString(months) + " MONTHS");
                        long seconds = duration.toMinutes() * 60;
                        TimeHolder.setText(Long.toString(duration.toHours()) + ":" + Long.toString(duration.toMinutes())+ ":" +Long.toString((Long) fetched[1]));

                        if(duration.toHours() < 0 || duration.toMinutes() < 0 || (Long) fetched[1] < 0) {
                             TimeHolder.setText("0:0:0");
                                tittleTheEvent.setText("set new Schedule Right here >");
                                running = false;

                        }





                        Thread.sleep(500);


                    }catch (Exception er){}

                }


        }
    }

    public  Object [] usingNewLibraryForSAndroidsGreaterThanEight(String FinalDate){
//        String FinalDate = "2022-10-17 00:00:00";



        LocalDateTime finaldatee = LocalDateTime.parse(FinalDate,DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss") );
        long millis = finaldatee.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();



        LocalDateTime currentDay = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        long milliscurrent = currentDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long difference_bn_days = millis - milliscurrent;

        Duration dayDuration = Duration.ofMillis(difference_bn_days);
//            String formatted = String.format("%d Day %02d Hour %02d Minute %02d Second %d Millisecond (%d Nanosecond)",dayDuration.toDaysPart(), dayDuration.toHoursPart(), dayDuration.toMinutesPart(), dayDuration.toSecondsPart(),dayDuration.toMillisPart(), dayDuration.toNanosPart());


        return new Object[]{dayDuration, (difference_bn_days / 1000) % 60};



    }

}