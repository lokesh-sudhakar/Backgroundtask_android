package com.example.backgroundservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String INPUT_EXTRA = "inputExtra";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_text_input);
    }

    public void enqueueWork(View v) {
        String input = editText.getText().toString();
        Intent jobIntentService = new Intent(this,ExampleJobIntentService.class);
        jobIntentService.putExtra(INPUT_EXTRA,input);
        ExampleJobIntentService.enqueueWork(this,jobIntentService);
    }

    public void startIntentService(View v) {
        String input = editText.getText().toString();
        Intent intentService = new Intent(this,ExampleIntentService.class);
        intentService.putExtra(INPUT_EXTRA,input);
        ContextCompat.startForegroundService(this,intentService);
    }

    public void startService(View v) {
        String input = editText.getText().toString();

        Intent serviceIntent = new Intent(this, ExampleForegroundService.class);
        serviceIntent.putExtra(INPUT_EXTRA,input);
        ContextCompat.startForegroundService(this,serviceIntent);
    }

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, ExampleForegroundService.class);
        stopService(serviceIntent);
    }



    public void scheduleJob(View v) {
        ComponentName componentName = new ComponentName(this,ExampleJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(123,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
                .setMinimumLatency(5 * 1000)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int result  = jobScheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduling success");
        } else {
            Log.d(TAG, "job scheduling failed");
        }
    }

    public void cancelJob(View v) {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(123);
        Log.d(TAG, "job cancelled");
    }


}
