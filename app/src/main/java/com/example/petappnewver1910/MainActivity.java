package com.example.petappnewver1910;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton addPetButton, userSettingsButton, searchForUsers, myFriendsButton, myPetsButtonImage, myEventsImageButton;
    Button logoutButton, friendsButton, myPetsButton, findUserButton, myEventsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPetButton = findViewById(R.id.addPetButton);

        userSettingsButton = findViewById(R.id.userSettingsButton);
        logoutButton = findViewById(R.id.logoutButton);


        myPetsButtonImage=findViewById(R.id.myPetsImageButton);
        myPetsButtonImage.setOnClickListener(this);
        myEventsImageButton=findViewById(R.id.myEventsImageButton);
        myEventsImageButton.setOnClickListener(this);

        myFriendsButton=findViewById(R.id.myFriendsImageButton);
        myFriendsButton.setOnClickListener(this);
        searchForUsers=findViewById(R.id.searchUsersImageButton);
        searchForUsers.setOnClickListener(this);


        addPetButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        userSettingsButton.setOnClickListener(this);
        addPetButton.setBackgroundResource(0);
        userSettingsButton.setBackgroundResource(0);
        searchForUsers.setBackgroundResource(0);
        myPetsButtonImage.setBackgroundResource(0);
        myEventsImageButton.setBackgroundResource(0);
        myFriendsButton.setBackgroundResource(0);
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
            case R.id.searchUsersImageButton:
                findUsers();
                break;
            case R.id.myFriendsImageButton:
                MyFriends();
                break;
            case R.id.myPetsImageButton:
                myPetsButton();
                break;
            case R.id.myEventsImageButton:
                myEvents();
                break;
        }
    }

    private void myEvents() {
        startActivity(new Intent(this, MyEventsActivity.class));
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
