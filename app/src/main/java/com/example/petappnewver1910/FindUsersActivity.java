package com.example.petappnewver1910;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindUsersActivity extends AppCompatActivity {

    private static final String TAG = FindUsersActivity.class.getSimpleName();
    EditText searchByEmailText;
    Button searchButton;
    TextView userName, userPets, userFriends;
    Button addFriend;
    ImageView userImage;
    TextView noUser;
    DatabaseReference database;
    String friendID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_users);
        userName = findViewById(R.id.userNameView);
        addFriend = findViewById(R.id.buttonAddFriend);
        addFriend.setVisibility(View.INVISIBLE);
        noUser = findViewById(R.id.noUserText);
        noUser.setVisibility(View.INVISIBLE);
        userPets = findViewById(R.id.userPetsView);
        userFriends = findViewById(R.id.userFriendsView);
        userName.setVisibility(View.INVISIBLE);
        userPets.setVisibility(View.INVISIBLE);
        userFriends.setVisibility(View.INVISIBLE);
        userImage = findViewById(R.id.imageView);
        userImage.setVisibility(View.INVISIBLE);
        searchByEmailText = findViewById(R.id.emailSearchText);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByEmail();
            }
        });

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                database = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                database.child("Friends").push().setValue(friendID)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(FindUsersActivity.this, "Friend Added Successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(FindUsersActivity.this, MainActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FindUsersActivity.this, "Failed to add friend", Toast.LENGTH_LONG).show();
                                Log.e(FindUsersActivity.class.getSimpleName(), "Something failed", e);
                            }
                        });
            }
        });
    }

    boolean foundFlag = false;

    private void searchByEmail() {

        String email = searchByEmailText.getText().toString();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User userComp = userSnapshot.getValue(User.class);
                    if (userComp.email.equals(email)) {
                        friendID=userSnapshot.getKey();
                        userComp.setNumOfPets(userComp.getNumOfPets() + 1);
                        userName.setText("Name: " + userComp.getFullName());
                        userFriends.setText("Friends: " + userComp.getNumOfFriends());
                        userPets.setText("Pets: " + userComp.getNumOfPets());
                        userName.setVisibility(View.VISIBLE);
                        userFriends.setVisibility(View.VISIBLE);
                        userPets.setVisibility(View.VISIBLE);
                        userImage.setVisibility(View.VISIBLE);
                        addFriend.setVisibility(View.VISIBLE);
                        foundFlag = true;
                        noUser.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage(), databaseError.toException());
            }
        };
        databaseReference.addListenerForSingleValueEvent(postListener);

        if (foundFlag == false) {
            noUser.setVisibility(View.VISIBLE);
        } /*else {
        }
*//*            addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    database = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                    User newFriend = new User(userName.getText().toString().trim(), email);
                    newFriend.setNumOfPets(Integer.parseInt(userPets.getText().toString()) + 1);
                    newFriend.setNumOfFriends(Integer.parseInt(userFriends.getText().toString()));
                    FirebaseDatabase.getInstance().
                }
            });*/
        }
    }

