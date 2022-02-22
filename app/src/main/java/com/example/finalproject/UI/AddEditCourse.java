package com.example.finalproject.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.Adapters.AssessmentAdapter;
import com.example.finalproject.Adapters.InstructorAdapter;
import com.example.finalproject.Entities.AssessmentTable;
import com.example.finalproject.Entities.CourseTable;
import com.example.finalproject.Entities.InstructorTable;
import com.example.finalproject.NotificationReciever;
import com.example.finalproject.R;
import com.example.finalproject.database.DatabaseRepository;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    SimpleDateFormat dateFormatter;

    DatePickerDialog.OnDateSetListener mStartDatePicker;
    DatePickerDialog.OnDateSetListener mEndDatePicker;

    DatabaseRepository mRepository;
    List<CourseTable> mDBcoursesList;
    CourseTable mSelectedCourse;
    List<AssessmentTable> assessmentTableList;
    List<InstructorTable> instructorTableList;

    RecyclerView mRecyclerViewB;
    RecyclerView mRecyclerViewA;
    AssessmentAdapter mAssessmentAdapter;
    InstructorAdapter mInstructorAdapter;
    RecyclerView.LayoutManager mLayoutManger;
    RecyclerView.LayoutManager mLayoutMangerB;

    int mTermId;
    int mCourseId;


    //------------------OnCreate-------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String myDateFormat = "MM/dd/yyyy";
        dateFormatter = new SimpleDateFormat(myDateFormat, Locale.US);

        mTermId = getIntent().getIntExtra("selectedTermId", -1);
        mCourseId = getIntent().getIntExtra("courseId", -1);

        mRepository = new DatabaseRepository(getApplication());

        getAndSetViewsById();

        getAllCourses();

        getAssessmentsInCourse();

        getInstructorsInCourse();

        setRecyclerViews();

        deleteInstructorHelper();

        deleteAssessmentHelper();

        setDatePicker();


        //------------------OnCreate-------------------------//
    }

    // share and notify menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_course_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share_notes:
                Intent notesIntent = new Intent();
                notesIntent.setAction(Intent.ACTION_SEND);
                notesIntent.putExtra(Intent.EXTRA_TEXT, mNotes.getText().toString());
                notesIntent.putExtra(Intent.EXTRA_TITLE, "Sharing Note");
                notesIntent.setType("text/plain");
                Intent noteIntentChooser = Intent.createChooser(notesIntent, null);
                startActivity(noteIntentChooser);
                return true;

            case R.id.set_course_start_alert:
                String courseStartDate = mStartDate.getText().toString();
                Date start = null;

                try{
                    start = dateFormatter.parse(courseStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long startTrigger = start.getTime();
                Intent startIntent = new Intent(AddEditCourse.this, NotificationReciever.class);
                startIntent.putExtra("key", mSelectedCourse.getCourseName() + " course starts today!");
                Toast.makeText(AddEditCourse.this, "Start notification set", Toast.LENGTH_SHORT).show();
                PendingIntent startSend = PendingIntent.getBroadcast(AddEditCourse.this, MainActivity.alertNum++, startIntent, 0  );
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP,startTrigger,startSend);
                return true;

            case R.id.set_course_end_alert:
                String courseEndDate = mEndDate.getText().toString();
                Date end = null;

                try {
                    end = dateFormatter.parse(courseEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long endTrigger = end.getTime();
                Intent endIntent = new Intent(AddEditCourse.this, NotificationReciever.class);
                endIntent.putExtra("key", mSelectedCourse.getCourseName() + " course ends today!");
                Toast.makeText(AddEditCourse.this, "End notification set", Toast.LENGTH_SHORT);
                PendingIntent endSend = PendingIntent.getBroadcast(AddEditCourse.this, MainActivity.alertNum++, endIntent, 0);
                AlarmManager endAlarmManager = (AlarmManager)  getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP,endTrigger,endSend);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    public void addInstructorButton(View view) {

        Intent intent = new Intent(this, AddEditInstructor.class);
        intent.putExtra("courseId", mCourseId);
        startActivity(intent);

    }

    public void addAssessmentButton(View view) {

        Intent intent = new Intent(this, AddEditAssessment.class);
        intent.putExtra("courseId", mCourseId);
        startActivity(intent);
    }

    public void getAssessmentsInCourse() {
        assessmentTableList = new ArrayList<>();
                List<AssessmentTable> list = mRepository.getAllAssessmentsFromRepo();
                for (AssessmentTable l : list){
                    if (l.getAssessmentCourseId() == mCourseId){
                        assessmentTableList.add(l);
                    }
                }
//                assessmentTableList = mRepository.getAllAssessmentsFromRepo();

    }

    public void getInstructorsInCourse() {
        instructorTableList = new ArrayList<>();
        List<InstructorTable> list = mRepository.getAllInstructorsFromRepo();
        for (InstructorTable instructor : list){
            if (instructor.getInstructorCourseId() == mCourseId){
                instructorTableList.add(instructor);
            }
        }
//        instructorTableList = mRepository.getAllInstructorsFromRepo();
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
        if (mCourseId != -1) {
            CourseTable updateCourse = new CourseTable(mCourseId, name, start, end, status, note, mTermId);
            mRepository.insert(updateCourse);

        } else {

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

    public void deleteInstructorHelper(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mRepository.delete(mInstructorAdapter.getInstructorAt(viewHolder.getAdapterPosition()));
                mInstructorAdapter.mInstructorList.remove(viewHolder.getAdapterPosition());
                mInstructorAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Toast.makeText(AddEditCourse.this, "Instructor Deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(mRecyclerViewA);
    }

    public void deleteAssessmentHelper(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mRepository.delete(mAssessmentAdapter.getAssessmentAt(viewHolder.getAdapterPosition()));
                mAssessmentAdapter.mAssessmentList.remove(viewHolder.getAdapterPosition());
                mAssessmentAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Toast.makeText(AddEditCourse.this, "Assessment Deleted", Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(mRecyclerViewB);
    }

    public void setRecyclerViews(){
        if (mCourseId != -1){
            getSelectedCourse();
            setTitle("Edit Course");


            mRecyclerViewB = findViewById(R.id.assessment_recyclerView);
            mRecyclerViewA = findViewById(R.id.instructor_recyclerView);

            mLayoutManger = new LinearLayoutManager(this);
            mLayoutMangerB = new LinearLayoutManager(this);

            mAssessmentAdapter = new AssessmentAdapter(this);
            mInstructorAdapter = new InstructorAdapter(this);

            mRecyclerViewB.setLayoutManager(mLayoutManger);
            mRecyclerViewA.setLayoutManager(mLayoutMangerB);

            mRecyclerViewB.setAdapter(mAssessmentAdapter);
            mRecyclerViewA.setAdapter(mInstructorAdapter);

            mAssessmentAdapter.assessmentSetter(assessmentTableList);
            mInstructorAdapter.instructorSetter(instructorTableList);
        }else{
            findViewById(R.id.add_instructor_btn).setVisibility(View.INVISIBLE);
            findViewById(R.id.add_assessment_btn).setVisibility(View.GONE);
            findViewById(R.id.instructorText).setVisibility(View.INVISIBLE);
            findViewById(R.id.assessmentText).setVisibility(View.INVISIBLE);
        }
    }


    public void termsBtn(View view) {


    }
}