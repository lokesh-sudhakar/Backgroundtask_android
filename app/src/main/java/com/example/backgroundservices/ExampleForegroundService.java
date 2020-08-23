package com.example.backgroundservices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.backgroundservices.ExampleJobService.TAG;

/**
 * @author Lokesh chennamchetty
 * @date 22/08/2020
 */
public class ExampleForegroundService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra(MainActivity.INPUT_EXTRA);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);

        if (isNotificationChannelEnabled(this,App.CHANNEL_ID)) {
            Log.d(TAG, "channel not enabled");
        } else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i=0;
                    while (i<1000) {
                        Log.d(TAG, "run : "+i);
                        i++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d(TAG, "run: end");
                    stopSelf();
                }
            }).start();
        }
        //do background task here
//        stopSelf();
        return START_NOT_STICKY;
    }

    public boolean isNotificationChannelEnabled(Context context, @Nullable String channelId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(!TextUtils.isEmpty(channelId)) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel channel = manager.getNotificationChannel(channelId);
                return channel.getImportance() != NotificationManager.IMPORTANCE_NONE;
            }
            return false;
        } else {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
