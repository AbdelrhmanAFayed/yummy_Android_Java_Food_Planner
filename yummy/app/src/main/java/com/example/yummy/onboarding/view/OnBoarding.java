package com.example.yummy.onboarding.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yummy.R;
import com.example.yummy.onboarding.BoardingContract;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class OnBoarding extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button btn_SignIN ;
    Button btn_SignUp ;
    Button btn_guest ;
    Button btn_Google ;

    TextView textEmail ;
    TextView textPass  ;

    BoardingContract.Presenter presenter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        btn_guest = findViewById(R.id.btn_guest);
        btn_SignIN = findViewById(R.id.btn_SignIn);
        btn_SignUp = findViewById(R.id.btn_SignUp);
        btn_Google = findViewById(R.id.btn_google);

        textEmail = findViewById(R.id.editTextEmail);
        textPass = findViewById(R.id.editTextPass);

        presenter.fireBaseinit();

        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.guestListnr();

            }
        });

       btn_Google.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               presenter.googleListnr();

           }
       });

       btn_SignIN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               presenter.signInListnr();

           }
       });

       btn_SignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               presenter.signUpListnr();
           }
       });
    }

}