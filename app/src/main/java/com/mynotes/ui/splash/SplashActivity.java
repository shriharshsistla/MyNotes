package com.mynotes.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.mynotes.ui.main.MainActivity;
import com.mynotes.R;

public class SplashActivity extends AppCompatActivity {

    ProgressBar splashProgressBar;
    private static final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        startSplashFunctionality();
    }

    private void startSplashFunctionality() {
        splashProgressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashProgressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        }, SPLASH_TIME);
    }

    private void initViews() {
        splashProgressBar = findViewById(R.id.progressBar);
    }
}