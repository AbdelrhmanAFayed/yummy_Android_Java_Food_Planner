package com.example.yummy.main.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yummy.R;
import com.example.yummy.main.view.MainActivity;
import com.example.yummy.model.db.AppDataBase;
import com.example.yummy.onboarding.view.OnBoarding;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {


    TextView username ;
    Button btn_logOut ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = getContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);



        username = view.findViewById(R.id.textacc);

        btn_logOut = view.findViewById(R.id.btn_logout);

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Leaving Already ?")
                        .setMessage("Are you sure you want to Log out ?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            FirebaseAuth.getInstance().signOut();

                            new Thread(() -> AppDataBase.getInstance(requireContext()).clearAllTables()).start();

                            Intent intent = new Intent(requireContext(), OnBoarding.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });

        username.setText("Welcome " + prefs.getString("username", "default_value"));
    }
}