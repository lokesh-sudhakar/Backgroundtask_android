package com.example.backgroundservices;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * @author Lokesh chennamchetty
 * @date 22/08/2020
 */
public class ExampleIntentService extends IntentService {
    private static final String TAG = "ExampleIntentService";
    private PowerManager.WakeLock wakeLock;

    public ExampleIntentService() {
        super("ExampleIntentService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"ExampleApp: wakeclock");
        wakeLock.acquire(600000);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            Intent intentService = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intentService,0);

            Notification notification = new NotificationCompat.Builder(this,App.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentTitle("Intent Service")
                    .setContentText("intent service running")
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1,notification);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent ");

        String input = intent.getStringExtra(MainActivity.INPUT_EXTRA);
        for (int i = 0;i<10;i++) {
            Log.d(TAG, input+" "+i);
            SystemClock.sleep(1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        wakeLock.release();
        Log.d(TAG, "wake lock release ");
    }
}
