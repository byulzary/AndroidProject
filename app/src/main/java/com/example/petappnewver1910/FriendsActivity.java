package com.example.petappnewver1910;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FriendsActivity extends AppCompatActivity implements View.OnClickListener {

    Button searchButton;
    EditText searchByEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        searchByEmail=findViewById(R.id.searchByEmail);
        searchButton=findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addPetButton:
                searchUsers();
                break;
        }

    }

    private void searchUsers() {
        String email=searchByEmail.getText().toString().trim();

    }
}
