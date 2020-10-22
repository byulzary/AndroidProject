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
    Button logoutButton, friendsButton, myPetsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPetButton = findViewById(R.id.addPetButton);
        userSettingsButton = findViewById(R.id.userSettingsButton);
        logoutButton = findViewById(R.id.logoutButton);
        friendsButton = findViewById(R.id.buttonFindFriends);
        myPetsButton=findViewById(R.id.myPetsButton);
        myPetsButton.setOnClickListener(this);
        friendsButton.setOnClickListener(this);
        addPetButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        userSettingsButton.setOnClickListener(this);
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
            case R.id.userSettingsButton:
                userSetting();
                break;
            case R.id.buttonFindFriends:
                findFriends();
                break;
            case R.id.myPetsButton:
                myPetsButton();
                break;
        }
    }

    private void myPetsButton() {
        //startActivity(new Intent(this, MyPetsActivity.class));
    }

    private void findFriends() {
        startActivity(new Intent(this, FriendsActivity.class));
    }

    private void userSetting() {
        startActivity(new Intent(this, UserSettingActivity.class));
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void addPet() {

        startActivity(new Intent(this, AddPetActivity.class));
    }
}
