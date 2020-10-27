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
    Button logoutButton, friendsButton, myPetsButton, findUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPetButton = findViewById(R.id.addPetButton);
        userSettingsButton = findViewById(R.id.userSettingsButton);
        logoutButton = findViewById(R.id.logoutButton);
        friendsButton = findViewById(R.id.buttonFindFriends);
        myPetsButton = findViewById(R.id.myPetsButton);
        findUserButton=findViewById(R.id.findEmailButton);
        findUserButton.setOnClickListener(this);
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
                MyFriends();
                break;
            case R.id.myPetsButton:
                myPetsButton();
                break;
            case R.id.findEmailButton:
                findUsers();
                break;
        }
    }

    private void MyFriends() {
        startActivity(new Intent(this, MyFriendsActivity.class));
    }

    private void myPetsButton() {
        startActivity(new Intent(this, MyPetsActivity.class));
    }

    private void findUsers(){
        startActivity(new Intent(this, FindUsersActivity.class));
    }

    private void findFriends() {
       // startActivity(new Intent(this, FindUsersActivity.class));
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
