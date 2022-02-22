package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.finalproject.Entities.AssessmentTable;
import com.example.finalproject.Entities.CourseTable;
import com.example.finalproject.Entities.InstructorTable;
import com.example.finalproject.R;
import com.example.finalproject.database.DatabaseRepository;

import java.util.List;

public class AddEditInstructor extends AppCompatActivity {


    EditText editTextName;
    EditText editTextPhone;
    EditText editTextEmail;
    List<CourseTable>mCourseList;
    CourseTable mCourse;
    InstructorTable instructorToEdit;

    int mCourseId;
    int mInstructorId;
    int mAssessCourseId;

    DatabaseRepository mRepository;
    List<InstructorTable> mInstructorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_instructor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRepository = new DatabaseRepository(getApplication());

        mCourseId = getIntent().getIntExtra("courseId", -1);
        mInstructorId = getIntent().getIntExtra("InstructorId", -1);
        mAssessCourseId = getIntent().getIntExtra("assessCourseId", -1);


        mInstructorList = mRepository.getAllInstructorsFromRepo();
        for (InstructorTable instructor: mInstructorList){
            if (instructor.getInstructorID() == mInstructorId){
                instructorToEdit = instructor;
            }
        }

        editTextName = findViewById(R.id.instructor_name);
        editTextPhone = findViewById(R.id.instructor_phone);
        editTextEmail = findViewById(R.id.instructor_email);

        if (mInstructorId != -1 ){
            editTextName.setText(instructorToEdit.getInstructorName());
            editTextPhone.setText(instructorToEdit.getInstructorPhone());
            editTextEmail.setText(instructorToEdit.getInstructorEmail());
        }

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SaveInstructorButton(View view) {

        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();

        mInstructorList = mRepository.getAllInstructorsFromRepo();
        int instId = 1;
        for (InstructorTable i : mInstructorList ){
            if (i.getInstructorID() >= instId){
                instId = i.getInstructorID();
            }
        }

        if (mInstructorId != -1){
            InstructorTable updateTable = new InstructorTable(mInstructorId, name, phone, email, mAssessCourseId);
            mRepository.insert(updateTable);
        }else{
            InstructorTable insertInstructor = new InstructorTable(++instId, name, phone, email, mCourseId);
            mRepository.insert(insertInstructor);
        }


        Intent intent = new Intent(this, AddEditCourse.class);
        if (mCourseId != -1){
            intent.putExtra("courseId", mCourseId);
        }
        else {
            intent.putExtra("courseId", mAssessCourseId);
        }
        startActivity(intent);
    }
}