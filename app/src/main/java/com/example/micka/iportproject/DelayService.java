package com.example.micka.iportproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by micka on 2/12/2018.
 */

public class DelayService extends Service{

    private int delay;
    private SharePrefSingleton sPref;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sPref = SharePrefSingleton.getInstance(getApplicationContext());
        delay = sPref.getDelay();


        Intent startWebViewWrapper = new Intent(getApplicationContext(),WebViewWrappingActivity.class);
        startWebViewWrapper.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),PendingIntent.FLAG_ONE_SHOT,startWebViewWrapper,0);
        final AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);


        long time = SystemClock.elapsedRealtime()+delay;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME,time,pendingIntent);

         stopSelf();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
