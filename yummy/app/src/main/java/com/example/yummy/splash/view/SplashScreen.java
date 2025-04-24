package com.example.yummy.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.yummy.R;
import com.example.yummy.onboarding.view.OnBoarding;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DURATION_MS = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView gifView = findViewById(R.id.splash_gif);
        Glide.with(this)
                .asGif()
                .load(R.drawable.splash_logo)
                .into(gifView);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, OnBoarding.class));
            finish();
        }, SPLASH_DURATION_MS);
    }
}