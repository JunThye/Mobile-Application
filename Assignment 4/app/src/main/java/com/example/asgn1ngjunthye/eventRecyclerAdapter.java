package com.example.asgn1ngjunthye;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class eventRecyclerAdapter extends RecyclerView.Adapter<eventRecyclerAdapter.eventViewHolder>{
    private ArrayList<Event> data;

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @NonNull
    @Override
    public eventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout,parent,false);
        eventViewHolder viewHolder = new eventViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull eventViewHolder holder, int position) {
        holder.eventID.setText(data.get(position).getEventID());
        holder.eventName.setText(data.get(position).getEventName());
        holder.catID.setText(data.get(position).getCategoryID());
        holder.tickets.setText(String.valueOf(data.get(position).getTicketsAvailable()));
        if (data.get(position).isActive()) {
            holder.active.setText("Active");
        }
        else {
            holder.active.setText("Inactive");
        }
        holder.cardView.setOnClickListener(v -> {
            String name = data.get(position).getEventName();

            Context context = holder.cardView.getContext();
            Intent intent = new Intent(context,EventGoogleResult.class);
            intent.putExtra("eventName",name);
            context.startActivity(intent);
        });
    }


    public class eventViewHolder extends RecyclerView.ViewHolder {

        public TextView eventID;
        public TextView eventName;
        public TextView catID;
        public TextView tickets;
        public TextView active;
        public View cardView;

        public eventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventID = itemView.findViewById(R.id.catname);
            eventName = itemView.findViewById(R.id.catcount);
            catID = itemView.findViewById(R.id.catidtxt);
            tickets = itemView.findViewById(R.id.ticketstxt);
            active = itemView.findViewById(R.id.catactive);
            cardView = itemView;
        }
    }
}
