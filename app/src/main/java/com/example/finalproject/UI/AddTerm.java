package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.finalproject.Entities.TermTable;
import com.example.finalproject.R;
import com.example.finalproject.database.DatabaseRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTerm extends AppCompatActivity {
    List<TermTable> termTableList;
    DatabaseRepository repository;

    DatePickerDialog.OnDateSetListener mStartDatePicker;
    DatePickerDialog.OnDateSetListener mEndDatePicker;

    EditText termName;
    EditText startDate;
    EditText endDate;

    Calendar mCalendarStart = Calendar.getInstance();
    Calendar mCalendarEnd = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Not doing anything here, fields are blank on create
        termName = findViewById(R.id.edit_text_title);
        startDate = findViewById(R.id.edit_text_startDate);
        endDate = findViewById(R.id.edit_text_endDate);
        repository = new DatabaseRepository(getApplication());

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
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTerm.this, mStartDatePicker, mCalendarStart.get(Calendar.YEAR), mCalendarStart.get(Calendar.MONTH)
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
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTerm.this, mEndDatePicker, mCalendarEnd.get(Calendar.YEAR), mCalendarEnd.get(Calendar.MONTH)
                        , mCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void saveTermOnClick(View view) {
        String name = termName.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();
        TermTable term;

        if (name.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty()) {

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

        } else {

            List<TermTable> allTerms = repository.getAllTermsFromRepo();
            int termsSize = allTerms.size();
            if (!allTerms.isEmpty()) {
                int lastId = allTerms.get(termsSize - 1).getTermId();

                term = new TermTable(lastId + 1, name, start, end);

            } else {
                term = new TermTable(1, name, start, end);
            }

            repository.insert(term);

            Intent intent = new Intent(this, TermsList.class);
            startActivity(intent);

        }
    }


    private void updateLabelStart() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        startDate.setText(sdf.format(mCalendarStart.getTime()));

    }

    private void updateLabelEnd() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        endDate.setText(sdf.format(mCalendarEnd.getTime()));

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

}



/*

 */