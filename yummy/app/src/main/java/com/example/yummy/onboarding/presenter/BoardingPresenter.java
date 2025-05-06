package com.example.yummy.onboarding.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.yummy.model.meal.MealRepository;
import com.example.yummy.onboarding.BoardingContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BoardingPresenter implements BoardingContract.Presenter {


    private BoardingContract.View view ;
    private FirebaseAuth mAuth;
    private final FirebaseDatabase db;

    private MealRepository mealRepository;

    public BoardingPresenter(BoardingContract.View view, FirebaseAuth auth , MealRepository mealRepository) {
        this.view = view;
        this.mAuth = auth;
        this.db = FirebaseDatabase.getInstance();

        this.mealRepository =   mealRepository ;
    }

    @Override
    public void handleSignInButtonClick() {

        String email = view.getEmail();
        String password = view.getPassword();

        if(email.isBlank() || password.isBlank())
        {
            view.showSignInError("Please enter email and password.");
        }
        else
        {
            view.showLoadingIndicator(true);
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            view.showLoadingIndicator(false);
                            if(task.isSuccessful())
                            {
                                FirebaseUser user = mAuth.getCurrentUser();
                                getUsernameFromDatabase();
                                mealRepository.restoreMealsFromFirebase();
                                view.showSignInSuccess();
                            }
                            else
                            {
                                view.showSignInError(task.getException().getMessage());
                            }

                        }
                    });

        }

    }

    @Override
    public void handleSignUpButtonClick() {
        String email = view.getEmail();
        String password = view.getPassword();

        if(email.isBlank() || password.isBlank()) {
            view.showSignUpError("Please enter email and password.");
        } else {
            view.showLoadingIndicator(true);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this::onSignUpComplete);
        }
    }

    @Override
    public void handleGoogleSignInButtonClick() {
        view.startGoogleSignInFlow();

    }

    @Override
    public void handleGoogleSignInResult(String idToken) {
        if (idToken == null) {
            view.onGoogleSignInFailure("Failed to retrieve Google ID token.");
            return;
        }

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        view.showLoadingIndicator(true);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    view.showLoadingIndicator(false);

                    if (!task.isSuccessful()) {
                        view.onGoogleSignInFailure(task.getException().getMessage());
                        return;
                    }

                    AuthResult result = task.getResult();
                    boolean isNew = result.getAdditionalUserInfo().isNewUser();
                    Log.d("BoardingPresenter", "Google sign-in; isNewUser=" + isNew);

                    if (isNew) {
                        view.promptForUsername();
                    } else {
                        getUsernameFromDatabase();
                        mealRepository.restoreMealsFromFirebase();
                        view.onGoogleSignInSuccess();
                    }
                });


    }




    @Override
    public void handleAnonymousSignInButtonClick() {

        view.showLoadingIndicator(true);
        mAuth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        view.showLoadingIndicator(false);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            handleUsernameChosen("Anonymous");
                            //view.showAnonymousSignInSuccess();
                        } else {
                            view.showAnonymousSignInError(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void handleUsernameChosen(String username) {
        Log.d("BoardingPresenter", "Started Saving");
        String uid = mAuth.getCurrentUser().getUid();
        db.getReference("users")
                .child(uid)
                .child("username")
                .setValue(username)
                .addOnSuccessListener(aVoid -> {
                    Log.d("BoardingPresenter", "Username saved to database");
                    view.saveUsernameLocally(username);
                    view.onUsernameSaved();
                })
                .addOnFailureListener(e -> {
                    Log.e("BoardingPresenter", "Failed to save username", e);
                    view.onUsernameSaveError(e.getMessage());
                });
    }

    public void getUsernameFromDatabase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

            dbRef.child("username").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String username = task.getResult().getValue(String.class);
                    if (username != null) {

                        view.saveUsernameLocally(username);
                    } else {
                        Log.d("BoardingPresenter", "Username is not set");
                    }
                } else {
                    Log.e("BoardingPresenter", "Error fetching username", task.getException());
                }
            });
        } else {
            Log.d("BoardingPresenter", "No user is signed in");
        }
    }



    private void onSignUpComplete(@NonNull Task<AuthResult> task) {
        view.showLoadingIndicator(false);
        if (task.isSuccessful()) {
            view.showSignUpSuccess();
            view.promptForUsername();
        } else {
            view.showSignUpError(task.getException().getMessage());
        }
    }
}

