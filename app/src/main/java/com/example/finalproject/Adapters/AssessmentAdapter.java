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

import com.example.finalproject.Entities.AssessmentTable;
import com.example.finalproject.R;
import com.example.finalproject.UI.AddEditAssessment;

import org.w3c.dom.Text;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    public List<AssessmentTable> mAssessmentList;
    private Context context;
    private LayoutInflater inflater;

    public AssessmentAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }


    // gets data from selected card and passes it onto onCreate
    public class AssessmentViewHolder extends RecyclerView.ViewHolder{
        public TextView recyclerViewItemLayout;
        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewItemLayout = itemView.findViewById(R.id.item_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    AssessmentTable current  = mAssessmentList.get(position);

                    Intent intent = new Intent(context, AddEditAssessment.class);
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

        }
    }




    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new AssessmentViewHolder(v);
    }


    // Sets text to card on rv.
    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        AssessmentTable currentAssessment = mAssessmentList.get(position);
        holder.recyclerViewItemLayout.setText(currentAssessment.getAssessmentTitle());
    }


    @Override
    public int getItemCount() {
        return mAssessmentList.size();
    }



    public AssessmentTable getAssessmentAt(int position){
        return mAssessmentList.get(position);
    }


    public void assessmentSetter (List<AssessmentTable> assessments){
        mAssessmentList = assessments;
        notifyDataSetChanged();
    }






}
