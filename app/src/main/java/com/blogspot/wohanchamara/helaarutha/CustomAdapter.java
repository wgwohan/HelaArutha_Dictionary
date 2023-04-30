package com.blogspot.wohanchamara.helaarutha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private RecyclerViewClickInterface recyclerViewClickInterface;
    private final ArrayList id, word, mean;

    CustomAdapter(ArrayList id, ArrayList word, ArrayList mean, RecyclerViewClickInterface recyclerViewClickInterface){
        this.id = id;
        this.word = word;
        this.mean = mean;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.word_txt.setText(String.valueOf(word.get(position)));
    }

    @Override
    public int getItemCount() {
        return word.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id_txt, word_txt, mean_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            word_txt = itemView.findViewById(R.id.word_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}