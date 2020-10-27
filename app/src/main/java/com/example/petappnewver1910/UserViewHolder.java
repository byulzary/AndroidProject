package com.example.petappnewver1910;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private final UserItemClickListener userItemClickListener;
    Button sendInvite = itemView.findViewById(R.id.inviteToEvent);
    ImageView userImage = itemView.findViewById(R.id.imageView);
    TextView userName = itemView.findViewById(R.id.userNameView);
    TextView userNOP = itemView.findViewById(R.id.userPetsView);
    TextView userNOF = itemView.findViewById(R.id.userFriendsView);

    public UserViewHolder(@NonNull View itemView, UserItemClickListener clickListener) {
        super(itemView);
        this.userItemClickListener = clickListener;
    }

    public void bind(User user) {
        userName.setText("Name: "+user.getFullName());
        userNOF.setText("Friends: "+(user.getNumOfFriends()+1));
        userNOP.setText("Pets: "+(user.getNumOfPets() + 1));
        sendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userItemClickListener.onUserInviteClick(v, user);
            }
        });
    }
}
