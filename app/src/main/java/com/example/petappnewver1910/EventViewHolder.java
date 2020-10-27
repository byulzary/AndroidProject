package com.example.petappnewver1910;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

    public class EventViewHolder extends RecyclerView.ViewHolder {

        private final EventsItemClickListener eventItemClickListener;
        TextView address = itemView.findViewById(R.id.addressView);
        TextView description = itemView.findViewById(R.id.descriptionView);
        TextView date = itemView.findViewById(R.id.dateView);


        public EventViewHolder(@NonNull View itemView, EventsItemClickListener clickListener) {
            super(itemView);
            this.eventItemClickListener = clickListener;
        }

        public void bind(Event event) {
            address.setText(event.getAddress());
            description.setText(event.getDescription());
            date.setText(event.getDate());
        }
    }


