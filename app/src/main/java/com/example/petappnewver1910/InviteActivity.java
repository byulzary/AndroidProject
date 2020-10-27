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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InviteActivity extends AppCompatActivity {
    private static final String TAG = InviteActivity.class.getSimpleName();
    TextView inviteeName;
    EditText inviteDescription, inviteAddress;
    Button sendInvite;
    FirebaseAuth mAuth;



    String friendID;
    String userId;

    User friend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite2);
        Intent intent=getIntent();

        friendID=intent.getStringExtra("currentUserId");
        sendInvite=findViewById(R.id.inviteButton);
        inviteeName=findViewById(R.id.inviteeName);
        inviteDescription=findViewById(R.id.inviteDescription);
        inviteAddress=findViewById(R.id.addressText);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();


        showFriendName(friendID);
        sendInvite.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Event event =new Event("None", "None", "None");
                event.setAddress(inviteAddress.getText().toString());
                event.setDescription(inviteDescription.getText().toString());
                FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(userId)
                        .child("Events")
                        .push()
                        .setValue(event)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(InviteActivity.this, "Event Added Successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(InviteActivity.this, MainActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(InviteActivity.this, "Failed to add Event", Toast.LENGTH_LONG).show();
                                Log.e(AddPetActivity.class.getSimpleName(), "Something failed", e);
                            }
                        });
                FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(friend.id)
                        .child("Events")
                        .push()
                        .setValue(event)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(InviteActivity.this, "Event Added To Friend Successfully", Toast.LENGTH_LONG).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(InviteActivity.this, "Event Add To Friend Failed", Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });

    }

    private void showFriendName(String friendID) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Users");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    if (userSnapshot.getKey().equals(friendID)){
                        friend=userSnapshot.getValue(User.class);
                        friend.setId(userSnapshot.getKey());
                        inviteeName.setText(friend.getFullName());

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage(), databaseError.toException());
            }
        };
        databaseReference.addListenerForSingleValueEvent(postListener);
    }

}
