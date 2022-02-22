package com.example.finalproject.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.Adapters.CourseAdapter;
import com.example.finalproject.Entities.CourseTable;
import com.example.finalproject.Entities.TermTable;
import com.example.finalproject.R;
import com.example.finalproject.database.DatabaseRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditTerm extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CourseAdapter mCourseAdapter;
    DatabaseRepository mRepository;

    EditText mEditName;
    EditText mEditStartDate;
    EditText mEndDate;

    Calendar mCalendarStart = Calendar.getInstance();
    Calendar mCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener mStartDatePicker;
    DatePickerDialog.OnDateSetListener mEndDatePicker;

    int mTermId;
    int getExtraTermID;
    String mName;
    String mStartD;
    String mEndD;
    TermTable mSelectedTerm;
    List<CourseTable> mAllCourses;
    CourseTable mCourses;

    List<CourseTable> courseInTermList;

    //------------------OnCreate--------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRepository = new DatabaseRepository(getApplication());

        getAndSetViewsById();
        setRecyclerViewAndAdapter();
        getTerm();
        getAllCourses();

        // gets and sets courses for recycleview
        mCourseAdapter.courseSetter(courseInTermList);
        //    setSwipeDelete();
        setDatePicker();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // for swiping and deleting
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mRepository.delete(mCourseAdapter.getCourseAt(viewHolder.getAdapterPosition()));
                mCourseAdapter.mCourseList.remove(viewHolder.getAdapterPosition());
                mCourseAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Toast.makeText(EditTerm.this, "Course Deleted", Toast.LENGTH_SHORT).show();


            }
        }).attachToRecyclerView(mRecyclerView);

    }

    //------------------OnCreate--------------------------//

    public void setRecyclerViewAndAdapter() {
        mRecyclerView = findViewById(R.id.edit_term_recyler);
        mLayoutManager = new LinearLayoutManager(this);
        mCourseAdapter = new CourseAdapter(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCourseAdapter);


    }

    public void getTerm() {
        mTermId = getIntent().getIntExtra("termId", -1);
        for (TermTable t : mRepository.getAllTermsFromRepo()) {
            if (t.getTermId() == mTermId) {
                mSelectedTerm = t;
                getExtraTermID = t.getTermId();
            }
        }
        if (mSelectedTerm != null) {
            mName = mSelectedTerm.getTermTitle();
            mStartD = mSelectedTerm.getStartOfTerm();
            mEndD = mSelectedTerm.getEndOfTerm();
        }
        mEditName.setText(mName);
        mEditStartDate.setText(mStartD);
        mEndDate.setText(mEndD);
    }

    public void getAllCourses() {
        mAllCourses = mRepository.getAllCoursesFromRep();
        courseInTermList = new ArrayList<>();
        for (CourseTable course : mAllCourses) {
            if (course.getCourseTermId() == getExtraTermID) {
                courseInTermList.add(course);
            }
        }
    }

    public void addCourseOnClick(View view) {
        Intent intent = new Intent(this, AddEditCourse.class);
        intent.putExtra("selectedTermId", mTermId);
        startActivity(intent);

    }

    public void saveTermOnClickEdit(View view) {
        String name = mEditName.getText().toString();
        String start = mEditStartDate.getText().toString();
        String end = mEndDate.getText().toString();
// todo may need validation for empty fields
        TermTable updatedTerm = new TermTable(mTermId, name, start, end);
        mRepository.insert(updatedTerm);

        Intent intent = new Intent(this, TermsList.class);
        startActivity(intent);

    }

    private void updateLabelStartDate() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        mEditStartDate.setText(sdf.format(mCalendarStart.getTime()));
    }

    private void updateLabelEndDate() {
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

    public void getAndSetViewsById() {
        mEditName = findViewById(R.id.edit_text_title);
        mEditStartDate = findViewById(R.id.edit_text_startDate);
        mEndDate = findViewById(R.id.edit_text_endDate);
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

                updateLabelStartDate();
            }
        };
        mEditStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditTerm.this, mStartDatePicker, mCalendarStart.get(Calendar.YEAR), mCalendarStart.get(Calendar.MONTH)
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

                updateLabelEndDate();
            }
        };
        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditTerm.this, mEndDatePicker, mCalendarEnd.get(Calendar.YEAR), mCalendarEnd.get(Calendar.MONTH)
                        , mCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

}