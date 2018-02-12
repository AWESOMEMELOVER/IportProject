package com.example.micka.iportproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by micka on 2/12/2018.
 */

public class BootIntentHandler extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent startServiceForDelay = new Intent(context,DelayService.class);
            context.startService(startServiceForDelay);
        }
    }
}
