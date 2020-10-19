package com.example.petappnewver1910;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddPetActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup radioGroupSex; //;)
    RadioButton radioButtonSex;
    EditText editPetName, editAge;
    Button addPetButton;
    ImageView addPetImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        radioGroupSex = findViewById(R.id.radioGroupSex);
        addPetButton = findViewById(R.id.addPetButton);
        editAge = findViewById(R.id.editAge);
        editPetName = findViewById(R.id.editPetName);
        addPetButton.setOnClickListener(this);
        addPetImage=findViewById(R.id.addPetImageButton);

    }

    @Override
    public void onClick(View v) {
        String name = editPetName.getText().toString().trim();
        int age = Integer.parseInt(editAge.getText().toString().trim());
        int checkedRadioButtonId=radioGroupSex.getCheckedRadioButtonId();
        radioButtonSex=findViewById(checkedRadioButtonId);

        if (name.isEmpty()){
            //todo
        }
        if (age<=0){
            //todo
        }

        Pet pet=new Pet(name, age);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(userId)
                .child("pets")
                .push()
                .setValue(pet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddPetActivity.this, "Pet Added Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddPetActivity.this, MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPetActivity.this, "Failed to add pet", Toast.LENGTH_LONG).show();
                        Log.e(AddPetActivity.class.getSimpleName(), "Something failed", e);
                    }
                });


    }
}
