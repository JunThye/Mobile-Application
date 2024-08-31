package com.example.asgn1ngjunthye;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class categoryRecyclerAdapter extends RecyclerView.Adapter<categoryRecyclerAdapter.categoryViewHolder>{
    private ArrayList<Category> data;

    private final int HEADER = 0;
    private final int CATEGORY = 1;

    public void setData(ArrayList<Category> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == CATEGORY) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false); //CardView inflated as RecyclerView list item
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_category_layout, parent, false); //CardView inflated as RecyclerView list item
        }
        categoryViewHolder viewHolder = new categoryViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
        if (position != 0) {
            holder.categoryName.setText(data.get(position -1).getCategoryName());
            holder.catID.setText(data.get(position -1).getCategoryID());
            holder.count.setText(String.valueOf(data.get(position -1).getEventCount()));
            if (data.get(position -1).isActive()) {
                holder.active.setText("Active");
            } else {
                holder.active.setText("Inactive");
            }
            holder.cardView.setOnClickListener(v -> {
                String country = data.get(position -1 ).getLocation();

                Context context = holder.cardView.getContext();
                Intent intent = new Intent(context,GoogleMapActivity.class);
                intent.putExtra("country",country);
                intent.putExtra("name",data.get(position-1).getCategoryName());
                context.startActivity(intent);
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return HEADER;
        else return CATEGORY;
    }

    public class categoryViewHolder extends RecyclerView.ViewHolder {


        public TextView categoryName;
        public TextView catID;
        public TextView count;
        public TextView active;
        public View cardView;


        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.catname);
            catID = itemView.findViewById(R.id.catid2);
            count = itemView.findViewById(R.id.catcount);
            active = itemView.findViewById(R.id.catactive);
            cardView = itemView;
        }
    }
}
