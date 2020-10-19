package com.example.petappnewver1910;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG=LoginActivity.class.getSimpleName();
    private TextView register;
    private Button loginButton;
    private EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.buttonLogin);
        emailField = findViewById(R.id.editEmail);
        passwordField = findViewById(R.id.editPassword);
        register = (TextView) findViewById(R.id.textRegister);
        register.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textRegister:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.buttonLogin:
                login();
                break;
        }
    }

    private void login() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if (email.isEmpty()) {
            //todo
        }
        if (password.isEmpty()) {
            //todo
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        onSuccessLogin(authResult.getUser());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e){
                        Log.e(TAG, "onFailure: Failed Login Attempt", e);
                        onFailureLogin();
                    }
                });
    }

    private void onFailureLogin() {
        Toast.makeText(this, "Failed Login Attempt", Toast.LENGTH_LONG).show();
    }

    private void onSuccessLogin(FirebaseUser user) {
        Toast.makeText(this, "Welcome " + user.getEmail(), Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
