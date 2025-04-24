package com.example.yummy.onboarding.presenter;

import com.google.firebase.auth.FirebaseAuth;

public class BoardingPresenter {

    private FirebaseAuth mAuth;

    public void fireBaseinit()
    {
        mAuth = FirebaseAuth.getInstance();
    }



}
