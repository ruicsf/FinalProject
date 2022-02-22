package com.example.finalproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Entities.InstructorTable;
import com.example.finalproject.R;
import com.example.finalproject.UI.AddEditInstructor;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {

    public List<InstructorTable> mInstructorList;
    private Context context;
    private LayoutInflater inflater;

    public InstructorAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class InstructorViewHolder extends RecyclerView.ViewHolder{
        public TextView recyclerViewItemLayout;

        public InstructorViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewItemLayout = itemView.findViewById(R.id.item_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    InstructorTable current = mInstructorList.get(position);

                    Intent intent = new Intent(context, AddEditInstructor.class );
                    intent.putExtra("InstructorId", current.getInstructorID() );
                    intent.putExtra("position", position);
                    intent.putExtra("assessCourseId", current.getInstructorCourseId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
       return new InstructorViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull InstructorViewHolder holder, int position) {
        InstructorTable currentInstructor = mInstructorList.get(position);
        holder.recyclerViewItemLayout.setText(currentInstructor.getInstructorName());
    }

    @Override
    public int getItemCount() {
        return mInstructorList.size();
    }

    public InstructorTable getInstructorAt(int position){
        return mInstructorList.get(position);
    }

    public void instructorSetter(List<InstructorTable> instructor){
        mInstructorList = instructor;
        notifyDataSetChanged();
    }

}
