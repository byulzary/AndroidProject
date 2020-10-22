package com.example.petappnewver1910;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPetsActivity extends AppCompatActivity {

    private static String TAG = MyPetsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);
        RecyclerView rv = findViewById(R.id.recyclerPets);
        rv.setLayoutManager(new GridLayoutManager(this, 1));
        ArrayList<Pet> pets = new ArrayList<>();

        DiffUtil.ItemCallback<Pet> itemCallback = new DiffUtil.ItemCallback<Pet>() {
            @Override
            public boolean areItemsTheSame(@NonNull Pet oldItem, @NonNull Pet newItem) {
                return oldItem.id.equals(newItem.id);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Pet oldItem, @NonNull Pet newItem) {
                return oldItem.id.equals(newItem.id);
            }
        };

        ListAdapter<Pet, PetAdapter.PetViewHolder> listAdapter = new ListAdapter<Pet, PetAdapter.PetViewHolder>(itemCallback) {
            @NonNull
            @Override
            public PetAdapter.PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(MyPetsActivity.this);
                View petCard = inflater.inflate(R.layout.pet_card, parent, false);
                return new PetAdapter.PetViewHolder(petCard);
            }

            @Override
            public void onBindViewHolder(@NonNull PetAdapter.PetViewHolder holder, int position) {
                holder.bind(getCurrentList().get(position));
            }
        };
        rv.setAdapter(listAdapter);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("pets");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Pet> pets = new ArrayList<>();
                for (DataSnapshot petSnapshot : dataSnapshot.getChildren()) {
                    Pet newPet = petSnapshot.getValue(Pet.class);
                    newPet.id = petSnapshot.getKey();
                    pets.add(newPet);
                }
//                rv.setAdapter(new PetAdapter(MyPetsActivity.this, pets));
                listAdapter.submitList(pets);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error loading pets", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);


    }
}
