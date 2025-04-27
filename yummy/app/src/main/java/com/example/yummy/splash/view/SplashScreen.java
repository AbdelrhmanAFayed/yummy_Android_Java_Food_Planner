package com.example.yummy.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.yummy.R;
import com.example.yummy.main.view.MainActivity;
import com.example.yummy.onboarding.view.OnBoarding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DURATION_MS = 3000;
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
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Log.d("SplashScreen", "Firebase currentUser = " + (user != null ? user.getUid() : "null"));
            if (user != null) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, OnBoarding.class));
            }
            finish();
        }, SPLASH_DURATION_MS);
    }
}