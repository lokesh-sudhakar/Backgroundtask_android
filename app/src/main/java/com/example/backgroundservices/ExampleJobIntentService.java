package com.example.backgroundservices;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

/**
 * @author Lokesh chennamchetty
 * @date 22/08/2020
 */
public class ExampleJobIntentService extends JobIntentService {

    private static final String TAG = "ExampleJobIntentService";


    static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context,ExampleIntentService.class,123,intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: ");
        String input = intent.getStringExtra(MainActivity.INPUT_EXTRA);
        for (int i =0;i<10;i++) {
            Log.d(TAG, "onHandleWork");
            if (isStopped()) {
                return;
            }
            SystemClock.sleep(1000);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork");
        return super.onStopCurrentWork();
    }
}
