package com.example.daycount_downer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class EntrancePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrance_page);

        Thread thread = new Thread(new ThreadJustForDelayCounter());
        thread.start();

    }


    class ThreadJustForDelayCounter implements  Runnable{

        @Override
        public void run() {

            try{

                Thread.sleep(3000);

            }catch (Exception er){}

            Intent intent = new Intent(EntrancePage.this, MainActivity.class);
            startActivity(intent);

        }
    }
}