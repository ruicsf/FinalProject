package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.Entities.AssessmentTable;
import com.example.finalproject.Entities.CourseTable;
import com.example.finalproject.NotificationReciever;
import com.example.finalproject.R;
import com.example.finalproject.database.DatabaseRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditAssessment extends AppCompatActivity {


    EditText editTextName;
    EditText editTextType;
    EditText editTextStart;
    EditText editTextEnd;

    DatabaseRepository mRepository;
    List<AssessmentTable> mAssessmentList;
    List<CourseTable> mCourseList;
    CourseTable mCourse;

    AssessmentTable assessmentToEdit;

    Calendar mCalendarStart = Calendar.getInstance();
    Calendar mCalendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener mStartDatePicker;
    DatePickerDialog.OnDateSetListener mEndDatePicker;

    SimpleDateFormat dateFormatter;

    int mCourseId;
    int mAssessmentId;
    int mAssessCourseId;

    //--------------------OnCreate------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRepository = new DatabaseRepository(getApplication());

        String myDateFormat = "MM/dd/yyyy";
        dateFormatter = new SimpleDateFormat(myDateFormat, Locale.US);

        mCourseId = getIntent().getIntExtra("courseId", -1);
        mAssessmentId = getIntent().getIntExtra("assessmentId", -1);
        mAssessCourseId = getIntent().getIntExtra("assessCourseId", -1);

        mAssessmentList = mRepository.getAllAssessmentsFromRepo();
        for (AssessmentTable i : mAssessmentList) {
            if (i.getAssessmentId() == mAssessmentId) {
                assessmentToEdit = i;
            }
        }

        editTextName = findViewById(R.id.assessment_name);
        editTextType = findViewById(R.id.assessment_type);
        editTextStart = findViewById(R.id.assessment_date);
        editTextEnd = findViewById(R.id.assessment_end);

        if (mAssessmentId != -1) {
            editTextName.setText(assessmentToEdit.getAssessmentTitle());
            editTextType.setText(assessmentToEdit.getAssessmentType());
            editTextStart.setText(assessmentToEdit.getAssessmentStartDate());
            editTextEnd.setText(assessmentToEdit.getAssessmentEndDate());
        }

        mStartDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarStart.set(Calendar.YEAR, year);
                mCalendarStart.set(Calendar.MONTH, month);
                mCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                UpdateLabelStart();
            }
        };
        mEndDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarEnd.set(Calendar.YEAR, year);
                mCalendarEnd.set(Calendar.MONTH, month);
                mCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                UpdateLabelEnd();
            }
        };

        editTextStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEditAssessment.this, mStartDatePicker, mCalendarStart.get(Calendar.YEAR)
                        , mCalendarStart.get(Calendar.MONTH), mCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editTextEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEditAssessment.this, mEndDatePicker, mCalendarEnd.get(Calendar.YEAR),
                        mCalendarEnd.get(Calendar.MONTH), mCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

//--------------------OnCreate------------------------------------//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assessment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.assessment_date_notification:
                String editTextDate = editTextStart.getText().toString();
                Date startDate = null;

                try {
                    startDate = dateFormatter.parse(editTextDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long timeTrigger = startDate.getTime();
                Intent intent = new Intent(AddEditAssessment.this, NotificationReciever.class);
                intent.putExtra("key", assessmentToEdit.getAssessmentTitle() + " assessment starts today!");
                Toast.makeText(AddEditAssessment.this, "Assessment notification set", Toast.LENGTH_SHORT).show();

                PendingIntent send = PendingIntent.getBroadcast(AddEditAssessment.this, MainActivity.alertNum++,intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,timeTrigger, send);
                return true;

            case R.id.assessment_end_date_notification:
                String editTextEndDate = editTextEnd.getText().toString();
                Date endDate = null;

                try {
                    endDate = dateFormatter.parse(editTextEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long endTrigger = endDate.getTime();
                Intent endIntent = new Intent(AddEditAssessment.this, NotificationReciever.class);
                endIntent.putExtra("key", assessmentToEdit.getAssessmentTitle() + " assessment ends today!");
                Toast.makeText(AddEditAssessment.this, "Assessment end notification set", Toast.LENGTH_SHORT).show();

                PendingIntent sendEnd = PendingIntent.getBroadcast(AddEditAssessment.this, MainActivity.alertNum++, endIntent, 0);
                AlarmManager endAlarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManger.set(AlarmManager.RTC_WAKEUP,endTrigger,sendEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void SaveAssessmentBtn(View view) {

        String title, type, date, endDate;

        title = editTextName.getText().toString();
        type = editTextType.getText().toString();
        date = editTextStart.getText().toString();
        endDate = editTextEnd.getText().toString();

        mAssessmentList = mRepository.getAllAssessmentsFromRepo();
        int assessmentId = 1;
        for (AssessmentTable assessment : mAssessmentList) {
            if (assessment.getAssessmentId() >= assessmentId) {
                assessmentId = assessment.getAssessmentId();
            }
        }

        if (mAssessmentId != -1) {
            AssessmentTable updateTable = new AssessmentTable(mAssessmentId, title, type, date, endDate, mAssessCourseId);
            mRepository.insert(updateTable);
        } else {
            AssessmentTable insertTable = new AssessmentTable(++assessmentId, title, type, date, endDate,  mCourseId);
            mRepository.insert(insertTable);
        }


        Intent intent = new Intent(this, AddEditCourse.class);
        if (mCourseId != -1) {
            intent.putExtra("courseId", mCourseId);
        } else {
            intent.putExtra("courseId", mAssessCourseId);
        }

        startActivity(intent);
    }


    public void UpdateLabelStart() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        editTextStart.setText(sdf.format(mCalendarStart.getTime()));
    }

    public void UpdateLabelEnd(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.US);

        editTextEnd.setText(sdf.format(mCalendarEnd.getTime()));
    }



}