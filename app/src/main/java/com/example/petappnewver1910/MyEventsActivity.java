package com.example.petappnewver1910;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class MyEventsActivity extends AppCompatActivity {

    private static String TAG = MyEventsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        RecyclerView rv = findViewById(R.id.recyclerEvents);

        rv.setLayoutManager(new GridLayoutManager(this, 1));

        DiffUtil.ItemCallback<Event> itemCallback = new DiffUtil.ItemCallback<Event>() {
            @Override
            public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                return oldItem.id.equals(newItem.id);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                return oldItem.id.equals(newItem.id);
            }
        };

        ListAdapter<Event, EventAdapter.EventViewHolder> listAdapter = new ListAdapter<Event, EventAdapter.EventViewHolder>(itemCallback) {

            @NonNull
            @Override
            public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(MyEventsActivity.this);
                View eventCard = inflater.inflate(R.layout.event_card, parent, false);
                return new EventAdapter.EventViewHolder(eventCard, MyEventsActivity.this);
            }

            @Override
            public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
                holder.bind(getCurrentList().get(position));
            }
        };
        rv.setAdapter(listAdapter);
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Events");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Event> events = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    Event newEvent = eventSnapshot.getValue(Event.class);
                    newEvent.id = eventSnapshot.getKey();
                    events.add(newEvent);
                }
                listAdapter.submitList(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error loading Events", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);
    }
}