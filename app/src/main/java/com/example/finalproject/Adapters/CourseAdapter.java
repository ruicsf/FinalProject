package com.example.finalproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Entities.CourseTable;
import com.example.finalproject.R;
import com.example.finalproject.UI.AddEditCourse;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public List<CourseTable> mCourseList;
    private Context context;
    private LayoutInflater inflater;


    public CourseAdapter(Context context){
        inflater = LayoutInflater.from(context); // IMPORTANT
        this.context = context; // IMPORTANT
    }

// CLASS
    public class CourseViewHolder extends RecyclerView.ViewHolder{
        public TextView recyclerViewItemLayout;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewItemLayout = itemView.findViewById(R.id.item_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    CourseTable currentCourse = mCourseList.get(position);

                    Intent intent = new Intent(context, AddEditCourse.class);
                    intent.putExtra("courseId", currentCourse.getCourseId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);

                }
            });
        }
    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseTable currentCourse = mCourseList.get(position);
        holder.recyclerViewItemLayout.setText(currentCourse.getCourseName());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public CourseTable getCourseAt(int position) {
        return mCourseList.get(position);
    }

    public void courseSetter(List<CourseTable> courses){
        mCourseList = courses;
        notifyDataSetChanged();
    }

}
