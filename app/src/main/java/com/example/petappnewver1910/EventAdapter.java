package com.example.petappnewver1910;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private ArrayList<Event> events;


    public EventAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventCard = inflater.inflate(R.layout.event_card, parent, false);
        return new EventAdapter.EventViewHolder(eventCard, context);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        private final Context context;


        TextView eventAddress=itemView.findViewById(R.id.addressView);
        TextView eventDescription=itemView.findViewById(R.id.descriptionView);
        TextView eventDate=itemView.findViewById(R.id.dateView);

        public EventViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context=context;
        }

        public void bind(Event event) {

            eventAddress.setText(event.address);
            eventDate.setText(event.date);
            eventDescription.setText(event.description);
        }
    }
}
