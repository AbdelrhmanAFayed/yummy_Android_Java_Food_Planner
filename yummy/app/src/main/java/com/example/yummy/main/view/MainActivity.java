package com.example.yummy.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yummy.R;
import com.example.yummy.details.view.DetailedMeal;
import com.example.yummy.onboarding.view.OnBoarding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yummy.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private boolean isGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isGuest = (FirebaseAuth.getInstance().getCurrentUser()).isAnonymous();

        com.example.yummy.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

        NavController navController = navHostFragment.getNavController();


        binding.navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (isGuest &&( (itemId == R.id.navigation_favorites) || (itemId == R.id.navigation_calendar)) ) {

                showLoginDialog();
                return false;
            }


            return NavigationUI.onNavDestinationSelected(item, navController);
        });

    }

    private void showLoginDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.boarding, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        Button loginButton = dialogView.findViewById(R.id.btn_login);
        Button signUpButton = dialogView.findViewById(R.id.btn_signup);

        loginButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, OnBoarding.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            dialog.dismiss();

        });

        signUpButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, OnBoarding.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            dialog.dismiss();

        });

        dialog.show();
    }



}