package com.richardsoares.moviedb_rick.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.widget.ProgressBar;

import com.richardsoares.moviedb_rick.R;

public class Splash extends Activity {
    // Timer da splash screen
    private static final int SPLASH_TIME_OUT = 3000;
   // private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
     //   mProgress = (ProgressBar) findViewById(R.id.progress_loader);

        /*
         * Exibindo splash com um timer.
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //doWork(); // Faz o Progressbar rodar, tem que criar um na activity
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }
/* Se quiser colocar um progress bar esse é o código para o andamento dele
    private void doWork() {
        for (int progress=0; progress<100; progress+=10) {
            try {
                Thread.sleep(1000);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
                //Timber.e(e.getMessage());
            }
        }
    }
 */
}