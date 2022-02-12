package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.finalproject.Entities.CourseTable;
import com.example.finalproject.Entities.TermTable;
import com.example.finalproject.R;
import com.example.finalproject.database.DatabaseRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddEditCourse extends AppCompatActivity {
    EditText mNameText;
    EditText mStartDate;
    EditText mEndDate;
    EditText mStatus;
    EditText mNotes;
    Calendar mCalendarStart = Calendar.getInstance();
    Calendar mCalendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener mStartDatePicker;
    DatePickerDialog.OnDateSetListener mEndDatePicker;

    DatabaseRepository mRepository;
    List<CourseTable> mDBcoursesList;
    CourseTable mSelectedCourse;
    List<TermTable> mDBtermsList;

    int mTermId;
    int mCourseId;

    //------------------OnCreate-------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRepository = new DatabaseRepository(getApplication());
        getAllCourses();
        getAndSetViewsById();

        mTermId = getIntent().getIntExtra("selectedTermId", -1);
        mCourseId = getIntent().getIntExtra("courseId", -1);

        if (mCourseId != -1){
            getSelectedCourse();
            setTitle("Edit Course");
        }

        setDatePicker();

        //------------------OnCreate-------------------------//
    }

    public void getSelectedCourse() {

            for (CourseTable course : mDBcoursesList) {
                if (mCourseId == course.getCourseId()) {
                    mSelectedCourse = course;
                }
            }

        mNameText.setText(mSelectedCourse.getCourseName());
        mStartDate.setText(mSelectedCourse.getCourseStart());
        mEndDate.setText(mSelectedCourse.getCourseEnd());
        mStatus.setText(mSelectedCourse.getCourseStatus());
        mNotes.setText(mSelectedCourse.getCourseNotes());
        mTermId = mSelectedCourse.getCourseTermId();
    }

    public void saveTermOnClick(View view) {
        String name = mNameText.getText().toString();
        String start = mStartDate.getText().toString();
        String end = mEndDate.getText().toString();
        String status = mStatus.getText().toString();
        String note = mNotes.getText().toString();
        if (note.trim().isEmpty()) {
            note = " ";
        }
        if (name.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty() || status.trim().isEmpty()) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Field(s) left blank");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }
        if (mCourseId != -1){
            CourseTable updateCourse = new CourseTable(mCourseId, name, start, end, status,note, mTermId);
            mRepository.insert(updateCourse);

        }else{

            int newId = mDBcoursesList.size();
            for (CourseTable course : mDBcoursesList) {
                if (course.getCourseId() >= newId) {
                    newId = course.getCourseId();
                }
            }
            CourseTable addCourse = new CourseTable(newId + 1, name, start, end, status, note, mTermId);
            mRepository.insert(addCourse);
        }

        Intent intent = new Intent(this, TermsList.class);
        startActivity(intent);

    }

    public void getAllCourses() {
        mDBcoursesList = mRepository.getAllCoursesFromRep();
    }

    private void updateLabelStart() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        mStartDate.setText(sdf.format(mCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        mEndDate.setText(sdf.format(mCalendarEnd.getTime()));
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

    public void setDatePicker() {
        mStartDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarStart.set(Calendar.YEAR, year);
                mCalendarStart.set(Calendar.MONTH, month);
                mCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                updateLabelStart();
            }
        };
        mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEditCourse.this, mStartDatePicker, mCalendarStart.get(Calendar.YEAR), mCalendarStart.get(Calendar.MONTH)
                        , mCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mEndDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarEnd.set(Calendar.YEAR, year);
                mCalendarEnd.set(Calendar.MONTH, month);
                mCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                updateLabelEnd();
            }
        };
        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEditCourse.this, mEndDatePicker, mCalendarEnd.get(Calendar.YEAR), mCalendarEnd.get(Calendar.MONTH)
                        , mCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void getAndSetViewsById() {
        mNameText = findViewById(R.id.add_course_name);
        mStartDate = findViewById(R.id.add_course_start_date);
        mEndDate = findViewById(R.id.add_course_end_date);
        mStatus = findViewById(R.id.add_course_status);
        mNotes = findViewById(R.id.add_course_notes);
    }

    public void addInstructorButton(View view) {

        Intent intent = new Intent(this, AddEditInstructor.class);
        startActivity(intent);

    }

    public void addAssessmentButton(View view) {

        Intent intent = new Intent(this, AddEditAssessment.class);
        startActivity(intent);
    }
}