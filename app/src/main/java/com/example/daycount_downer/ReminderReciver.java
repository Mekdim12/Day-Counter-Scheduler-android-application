package com.example.daycount_downer;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Intent i = new Intent(context,MainActivity.class);

        intent. setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,  0,i, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,  "countdown")
                .setSmallIcon(R.drawable.calander)
                .setContentTitle("Schedule Reminder")
                .setContentText("Your Schedule Events just arrives Now! Don't Miss Out")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat. PRIORITY_HIGH)
                .setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(  124, builder.build());



    }
}
