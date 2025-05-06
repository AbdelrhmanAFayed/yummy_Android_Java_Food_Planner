package com.example.yummy.onboarding.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yummy.R;
import com.example.yummy.main.view.MainActivity;
import com.example.yummy.model.meal.MealRepositoryImp;
import com.example.yummy.onboarding.BoardingContract;
import com.example.yummy.onboarding.presenter.BoardingPresenter;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;


public class OnBoarding extends AppCompatActivity implements BoardingContract.View  {

    private EditText editTextEmail;
    private EditText editTextPass;
    private Button btnSignUp;
    private Button btnSignIn;
    private Button btnGoogle;
    private Button btnGuest;
    private ProgressBar loadingIndicator;

    private BoardingContract.Presenter presenter;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    private ActivityResultLauncher<IntentSenderRequest> googleSignInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        editTextEmail      = findViewById(R.id.editTextEmail);
        editTextPass       = findViewById(R.id.editTextPass);
        btnSignIn          = findViewById(R.id.btn_SignIn);
        btnSignUp          = findViewById(R.id.btn_SignUp);
        btnGoogle          = findViewById(R.id.btn_google);
        btnGuest           = findViewById(R.id.btn_guest);
        loadingIndicator   = findViewById(R.id.loadingIndicator);

        presenter = new BoardingPresenter(this, FirebaseAuth.getInstance(), MealRepositoryImp.getInstance(this));

        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(getString(R.string.default_web_client_id))
                                .setFilterByAuthorizedAccounts(false)
                                .build())
                .build();

        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            try {
                                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                                String idToken = credential.getGoogleIdToken();
                                if (idToken != null) {
                                    handleGoogleSignInSuccess(idToken);
                                    return;
                                }
                            } catch (Exception e) {
                                handleGoogleSignInFailure(e.getMessage());
                                return;
                            }
                        }
                        handleGoogleSignInFailure("Google sign-in canceled");
                    }
                }
        );

        btnSignIn.setOnClickListener(v -> presenter.handleSignInButtonClick());
        btnSignUp.setOnClickListener(v -> presenter.handleSignUpButtonClick());
        btnGoogle.setOnClickListener(v -> presenter.handleGoogleSignInButtonClick());
        btnGuest.setOnClickListener(v -> presenter.handleAnonymousSignInButtonClick());
    }


    @Override
    public String getEmail() {
        return editTextEmail.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return editTextPass.getText().toString().trim();
    }

    @Override
    public void showSignInSuccess() {
        Toast.makeText(this, "Sign In Successful", Toast.LENGTH_SHORT).show();
        navigateToHomeScreen();
    }

    @Override
    public void showSignUpSuccess() {
        Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showSignInError(String errorMessage) {
        Toast.makeText(this, "Sign In Failed: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSignUpError(String errorMessage) {
        Toast.makeText(this, "Sign Up Failed: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startGoogleSignInFlow() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(result -> {
                    IntentSenderRequest req = new IntentSenderRequest.Builder(result.getPendingIntent()).build();
                    googleSignInLauncher.launch(req);
                })
                .addOnFailureListener(e -> showGoogleSignInError(e.getMessage()));
    }

    @Override
    public void showGoogleSignInError(String errorMessage) {
        Toast.makeText(this, "Google Sign-In Failed: " + errorMessage, Toast.LENGTH_LONG).show();
        Log.i("Boarding",errorMessage);
    }

    @Override
    public void onGoogleSignInSuccess() {
        Toast.makeText(this, "Google Sign-In Successful", Toast.LENGTH_SHORT).show();
        navigateToHomeScreen();
    }

    @Override
    public void onGoogleSignInFailure(String errorMessage) {
        Toast.makeText(this, "Firebase Auth Failed: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAnonymousSignInSuccess() {
        Toast.makeText(this, "Guest Sign-In Successful", Toast.LENGTH_SHORT).show();
        navigateToHomeScreen();
    }

    @Override
    public void showAnonymousSignInError(String errorMessage) {
        Toast.makeText(this, "Guest Sign-In Failed: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToHomeScreen() {
        Log.d("OnBoarding", "navigateToHomeScreen()");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showLoadingIndicator(boolean show) {
        loadingIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void promptForUsername() {
        final EditText input = new EditText(this);
        input.setHint("Pick a username");


        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_username, null);
        EditText etUsername = dialogView.findViewById(R.id.etUsername);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Welcome!")
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    String username = etUsername.getText().toString().trim();
                    if (username.isEmpty()) {
                        Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                        promptForUsername();
                    } else {
                        showLoadingIndicator(true);
                        presenter.handleUsernameChosen(username);
                    }
                })
                .show();

    }

    @Override
    public void saveUsernameLocally(String username) {
        getSharedPreferences("app_prefs", MODE_PRIVATE)
                .edit()
                .putString("username", username)
                .apply();
    }

    @Override
    public void onUsernameSaved() {
        Log.d("OnBoarding", "onUsernameSaved() called");
        showLoadingIndicator(false);
        navigateToHomeScreen();
    }



    @Override
    public void onUsernameSaveError(String err) {
        showLoadingIndicator(false);
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Could not save username:\n" + err)
                .setPositiveButton("Retry", (d,w) -> promptForUsername())
                .setCancelable(false)
                .show();
    }




    private void handleGoogleSignInSuccess(String idToken) {
        presenter.handleGoogleSignInResult(idToken);
    }

    private void handleGoogleSignInFailure(String error) {
        presenter.handleGoogleSignInResult(null);
    }
}