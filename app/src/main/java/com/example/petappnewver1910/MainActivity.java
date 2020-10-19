package com.example.petappnewver1910;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton addPetButton, userSettingsButton;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPetButton=findViewById(R.id.addPetButton);
        userSettingsButton=findViewById(R.id.userSettingsButton);
        logoutButton=findViewById(R.id.logoutButton);
        addPetButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addPetButton:
                addPet();
                break;
            case R.id.logoutButton:
                logout();
                break;
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void addPet() {
        startActivity(new Intent(this, AddPetActivity.class));
    }
}
