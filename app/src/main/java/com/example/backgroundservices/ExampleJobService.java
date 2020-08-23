package com.example.backgroundservices;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

/**
 * @author Lokesh chennamchetty
 * @date 22/08/2020
 */
public class ExampleJobService extends JobService {

    public static final String TAG = "ExampleJobService";
    private boolean jobCalcelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: ");
        doBackgroundTask(jobParameters);
        return true;
    }

    private void doBackgroundTask(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++) {
                    Log.d(TAG, "run: "+i);
                    if (jobCalcelled) {
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
                Log.d(TAG, "job finished");
                jobFinished(jobParameters,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: job cancelled before completion");
        jobCalcelled = true;
        return true;
    }
}
