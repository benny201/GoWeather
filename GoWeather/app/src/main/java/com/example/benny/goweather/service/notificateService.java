package com.example.benny.goweather.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.benny.goweather.MyAppContext;
import com.example.benny.goweather.R;

import java.sql.Time;

public class notificateService extends Service {
    public notificateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Calendar calendar = Calendar.getInstance();
        //int hour = calendar.get(Calendar.HOUR_OF_DAY);

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int sec = calendar.get(java.util.Calendar.SECOND);
        int min = calendar.get(java.util.Calendar.MINUTE);
        int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);

        Log.d("BENNYTIMETEST", "onStartCommand: " + hour + min +sec);

        if (hour == 11 && min == 9 ) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("睡觉提醒")
                    .setContentText("妹子,是时候睡觉了")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_child_care_black_24dp)
                    .build();
            notificationManager.notify(1, notification);

            //轮询
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            int tenHour = 10 * 60 * 60 * 1000;
            long triggerTime = SystemClock.elapsedRealtime() + tenHour;
            Intent triggerIntent = new Intent(this, notificateService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, triggerIntent, 0);
            alarmManager.cancel(pendingIntent);
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent);
        } else {

            Toast.makeText(MyAppContext.getContext(), "t", Toast.LENGTH_SHORT).show();

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            int tenHour = 14 * 1000;
            long triggerTime = SystemClock.elapsedRealtime() + tenHour;
            Intent triggerIntent = new Intent(this, notificateService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, triggerIntent, 0);
            alarmManager.cancel(pendingIntent);
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent);
        }


        return super.onStartCommand(intent, flags, startId);
    }
}
