package com.example.petappnewver1910;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private Context context;
    private ArrayList<Pet> pets;


    public PetAdapter(Context context, ArrayList<Pet> pets) {
        this.context = context;
        this.pets = pets;

    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View petCard = inflater.inflate(R.layout.pet_card, parent, false);
        return new PetViewHolder(petCard);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.bind(pets.get(position));
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {
        Button editPetButton = itemView.findViewById(R.id.editPetButton);
        ImageView petImageView = itemView.findViewById(R.id.petImageView);
        TextView petName = itemView.findViewById(R.id.petNameView);
        TextView petDOB = itemView.findViewById(R.id.petDateOfBirthView);
        TextView petSex = itemView.findViewById(R.id.petSexView);

        private TextView nameField;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Pet pet) {
            DateFormat df = SimpleDateFormat.getDateInstance();
            petName.setText("Name: " + pet.name);
            petSex.setText("Sex: " + pet.sex);
            petDOB.setText("Date of Birth: " + df.format(new Date(pet.getbDay())));

        }
    }
}
