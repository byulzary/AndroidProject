package com.example.petappnewver1910;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/*import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;*/
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserSettingActivity extends AppCompatActivity {

    String fullName;
    int numOfPets, numOfFriends;
    TextView textNumOfPets, textNumOfFriends, textFullName, textEmail;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        textEmail = findViewById(R.id.textEmail);
        textFullName = findViewById(R.id.textFullName);
        textNumOfFriends = findViewById(R.id.textNumOfFriends);
        textNumOfPets = findViewById(R.id.textNumOfPets);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userId = mAuth.getCurrentUser().getUid();
        showUserInfo(userId);
    }

    private void showUserInfo(String userId) {


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User newUser = dataSnapshot.getValue(User.class);
                textEmail.setText("Email: "+newUser.getEmail());
                textFullName.setText("Name: "+newUser.getFullName());
                newUser.setNumOfPets(newUser.getNumOfPets()+1);
                textNumOfPets.setText("Pets: "+String.valueOf(newUser.getNumOfPets()));
                textNumOfFriends.setText("Friends: "+String.valueOf((newUser.getNumOfFriends()))+1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        databaseReference.addValueEventListener(postListener);

    }
}
