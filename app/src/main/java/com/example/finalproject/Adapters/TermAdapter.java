package com.example.finalproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Entities.TermTable;
import com.example.finalproject.R;
import com.example.finalproject.UI.EditTerm;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    public List<TermTable> mTermsList;

    private LayoutInflater inflater;
    private Context context;

    public TermAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    // CLASS
    public class TermViewHolder extends RecyclerView.ViewHolder {
        public TextView termItemLayout;
        //todo add course here

        private TermViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemLayout = itemView.findViewById(R.id.term_item_layout); // -> TEXT ON XML CARD
            //todo add course here

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    TermTable currentTerm = mTermsList.get(position);

                    Intent intent = new Intent(context, EditTerm.class);
                    intent.putExtra("termId", currentTerm.getTermId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                    Log.d("id", String.valueOf(currentTerm.getTermId()));
            }
        });
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    TermTable currentTerm = mTermsList.get(position);
//                     Intent intent = new Intent(context, EditTerm.class);
//                     intent.putExtra("termId", currentTerm.getTermId());
//                     intent.putExtra("termTitle", currentTerm.getTermTitle());
//                     intent.putExtra("startOfTerm", currentTerm.getStartOfTerm());
//                     intent.putExtra("endOfTerm", currentTerm.getEndOfTerm());
//                     intent.putExtra("position", position);
//                     context.startActivity(intent);
//                }
//            });
    }
}


    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       //todo add if statement?
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        TermViewHolder tvh = new TermViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        TermTable currentTerm = mTermsList.get(position);
        holder.termItemLayout.setText(currentTerm.getTermTitle());

        //todo code for courses here
    }

    @Override
    public int getItemCount() {
        return mTermsList.size();
        // todo add if statement?
    }

    public TermTable getTermAt(int position) {
        return mTermsList.get(position);
    }

    public void termsSetter(List<TermTable> terms) {
        mTermsList = terms;
        notifyDataSetChanged();
    }

}

