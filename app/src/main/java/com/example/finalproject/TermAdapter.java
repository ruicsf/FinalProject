package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Entities.TermTable;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    List<TermTable> mTermsList;


    // CLASS
    public static class TermViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.term_item_layout); // -> TEXT ON XML CARD

        }
    }


    public TermAdapter(List<TermTable> termList) {
        mTermsList = termList;
    }


    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        TermViewHolder tvh = new TermViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        TermTable currentTerm = mTermsList.get(position);
        holder.mTextView.setText(currentTerm.getTermTitle());
    }

    @Override
    public int getItemCount() {
       return mTermsList.size();
    }

}

