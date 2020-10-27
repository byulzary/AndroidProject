package com.example.petappnewver1910;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.Calendar;
import java.util.Date;

public class EditPetActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = EditPetActivity.class.getSimpleName();
    RadioGroup radioGroupSex; //;)
    RadioButton radioButtonSex, radioButtonMale, radioButtonFemale;
    EditText editPetName;
    Button addPetButton;
    ImageView addPetImage;
    TextView mDisplaydate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    DatabaseReference database;
    int numOfPets;
    Date bDay;
    String petID;
    Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);
        Intent intent=getIntent();

        petID=intent.getStringExtra("petID");
        database = FirebaseDatabase.getInstance().getReference("Users");
        radioGroupSex = findViewById(R.id.radioGroupSex);
        radioButtonFemale = findViewById(R.id.femaleRadioButton);
        radioButtonMale = findViewById(R.id.maleRadioButton);
        radioButtonMale.setChecked(true);
        radioButtonFemale.setChecked(false);
        addPetButton = findViewById(R.id.addPetButton);
        editPetName = findViewById(R.id.editPetName);
        addPetButton.setOnClickListener(this);
        addPetImage = findViewById(R.id.addPetImageButton);
        mDisplaydate = (TextView) findViewById(R.id.dateView);
        editPetName.setText(petID);
        mDisplaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditPetActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet:date " + year + "/" + month + "/" + dayOfMonth);
                String date = month + "/" + dayOfMonth + "/" + year;
                mDisplaydate.setText(date);
                bDay = new Date(year, month, dayOfMonth);
            }
        };
        showPetName(petID);
    }

    private void showPetName(String petID) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("pets");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot petSnapshot : dataSnapshot.getChildren()) {
                    if (petSnapshot.getKey().equals(petID)){
                        Pet newPet = petSnapshot.getValue(Pet.class);
                        newPet.id = petSnapshot.getKey();
                        editPetName.setText(newPet.getName());
                        if (newPet.getSex().equals("Female")){
                            radioButtonFemale.setChecked(true);
                            radioButtonMale.setChecked(false);
                        }
                        else{
                            radioButtonMale.setChecked(true);
                            radioButtonFemale.setChecked(false);
                        }
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

    @Override
    public void onClick(View v) {
        String name = editPetName.getText().toString().trim();
        int checkedRadioButtonId = radioGroupSex.getCheckedRadioButtonId();
        radioButtonSex = findViewById(checkedRadioButtonId);

        if (name.isEmpty()) {
            editPetName.setError("Name cannot be empty");
            editPetName.requestFocus();
        }
        String sex="Male";
        if (radioButtonFemale.isChecked()){
            sex="Female";
        }

        String date=mDisplaydate.getText().toString().trim();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        ValueEventListener postListener=new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User newUser=dataSnapshot.getValue(User.class);
                numOfPets=newUser.numOfPets;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        database.addValueEventListener(postListener);
        Pet pet = new Pet(name, sex, date);
        pet.setId(petID);


    }
}
