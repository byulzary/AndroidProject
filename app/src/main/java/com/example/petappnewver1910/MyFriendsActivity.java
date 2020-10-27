package com.example.petappnewver1910;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

        RecyclerView rv = findViewById(R.id.recyclerUsers);
        DiffUtil.ItemCallback<User> itemCallback = new DiffUtil.ItemCallback<User>() {

            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId().equals(newItem.getId());
            }
        };

        ListAdapter<User, UserViewHolder> listAdapter = new ListAdapter<User, UserViewHolder>(itemCallback) {
            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(MyFriendsActivity.this);
                View usersView = inflater.inflate(R.layout.user_card, parent, false);
                return new UserViewHolder(usersView, new UserItemClickListener() {
                    @Override
                    public void onUserInviteClick(View v, User user) {
                        Intent intent=new Intent(MyFriendsActivity.this, InviteActivity.class);
                        intent.putExtra("currentUserId", user.getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
                holder.bind(getCurrentList().get(position));
            }
        };

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("Users");

        DatabaseReference friendsReference = usersReference.child(currentUserId).child("Friends");

        ValueEventListener postListener = new ValueEventListener() {
            private final String TAG = MyFriendsActivity.class.getSimpleName();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> users = new ArrayList<>();
                ArrayList<Task<User>> getUserTasks = new ArrayList<>();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String newId = userSnapshot.getValue().toString();
                    getUserTasks.add(retrieveUserFromFirebase(usersReference, newId));
                }

                Tasks.whenAllSuccess(getUserTasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> objects) {
                        for(Object o : objects) users.add((User) o);
                        listAdapter.submitList(users);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.getLocalizedMessage(), e);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error loading users", databaseError.toException());
            }
        };
        friendsReference.addValueEventListener(postListener);
        rv.setAdapter(listAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));


    }

    private Task<User> retrieveUserFromFirebase(DatabaseReference usersReference, String newId) {
        TaskCompletionSource<User> tcs = new TaskCompletionSource<>();

        usersReference.child(newId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.setId(newId);
                tcs.setResult(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });

        return tcs.getTask();
    }


}
