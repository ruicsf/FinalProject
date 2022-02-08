package com.example.finalproject.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    EditText termName;
    EditText startDate;
    EditText endDate;

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener sDate;
    DatePickerDialog.OnDateSetListener eDate;

    Button saveBtn;

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

        sDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabelStart();
            }
        };

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTerm.this, sDate, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH)
                        , calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        eDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, month);
                calendarEnd.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabelEnd();
            }
        };

       endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTerm.this, eDate, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH)
                        , calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabelStart() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        startDate.setText(sdf.format(calendarStart.getTime()));

    }

    private void updateLabelEnd() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        endDate.setText(sdf.format(calendarEnd.getTime()));

    }

    public void saveTermOnClick(View view) {
        String name = termName.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();

        if (name.isEmpty() || start.isEmpty() || end.isEmpty()) {
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
            int lastId = allTerms.get(allTerms.size() - 1).getTermId();

            TermTable term = new TermTable(lastId + 1, name, start, end);
            repository.insert(term);

            Intent intent = new Intent(this, TermsList.class);
            startActivity(intent);
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

}