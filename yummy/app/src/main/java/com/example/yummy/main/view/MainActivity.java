package com.example.yummy.main.view;

import static android.content.ContentValues.TAG;

import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.credentials.CredentialOption;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;

import com.example.yummy.R;
import com.example.yummy.model.network.meal.MealNetWorkCallBack;
import com.example.yummy.model.network.meal.MealRemoteDataSource;
import com.example.yummy.model.network.meal.MealRemoteDataSourceImp;
import com.example.yummy.model.network.meal.MealResponse;
import com.example.yummy.model.network.mealshort.MealShortNetWorkCallBack;
import com.example.yummy.model.network.mealshort.MealShortRemoteDataSource;
import com.example.yummy.model.network.mealshort.MealShortRemoteDataSourceImp;
import com.example.yummy.model.network.mealshort.MealShortResponse;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity implements MealShortNetWorkCallBack, MealNetWorkCallBack {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






    }

    @Override
    public void onCatSuccessResult(MealShortResponse mealShortResponse) {


    }

    @Override
    public void onCatFailureResult(String error) {

    }

    @Override
    public void onIngSuccessResult(MealShortResponse mealShortResponse) {

    }

    @Override
    public void onIngFailureResult(String error) {

    }

    @Override
    public void onAreaSuccessResult(MealShortResponse mealShortResponse) {

    }

    @Override
    public void onAreaFailureResult(String error) {

    }

    @Override
    public void onDaySuccessResult(MealResponse mealResponse) {



    }

    @Override
    public void onDayFailureResult(String error) {

    }

    @Override
    public void onNameSuccessResult(MealResponse mealResponse) {

    }

    @Override
    public void onNameFailureResult(String error) {

    }

    @Override
    public void onIDSuccessResult(MealResponse mealResponse) {

    }

    @Override
    public void onIDFailureResult(String error) {

    }

    @Override
    public void onLetterSuccessResult(MealResponse mealResponse) {

    }

    @Override
    public void onLetterFailureResult(String error) {

    }
}