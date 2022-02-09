package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.Entities.TermTable;
import com.example.finalproject.R;
import com.example.finalproject.database.DatabaseRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditTerm extends AppCompatActivity {
    DatabaseRepository mRepository;
    int termId;
    String name;
    String startD;
    String endD;
    TermTable selectedTerm;

    EditText termName;
    EditText startDate;
    EditText endDate;

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener sDate;
    DatePickerDialog.OnDateSetListener eDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Not doing anything here, fields are blank on create
        termName = findViewById(R.id.edit_text_title);
        startDate = findViewById(R.id.edit_text_startDate);
        endDate = findViewById(R.id.edit_text_endDate);
        mRepository = new DatabaseRepository(getApplication());

        termId = getIntent().getIntExtra("termId", -1);
        for (TermTable t: mRepository.getAllTermsFromRepo()){
            if (t.getTermId() == termId){
                selectedTerm = t;
            }
        }

        if (selectedTerm != null){
            name=selectedTerm.getTermTitle();
            startD = selectedTerm.getStartOfTerm();
            endD = selectedTerm.getEndOfTerm();
        }
            termName.setText(name);
            startDate.setText(startD);
            endDate.setText(endD);

        sDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabelStartDate();
            }
        };

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditTerm.this, sDate, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH)
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

                updateLabelEndDate();
            }
        };
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditTerm.this, eDate, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH)
                        , calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void saveTermOnClickEdit(View view) {
        String name = termName.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();

// todo may need validation for empty fields

        TermTable updatedTerm = new TermTable(termId, name, start, end );
        mRepository.insert(updatedTerm);

        Intent intent = new Intent(this, TermsList.class);
        startActivity(intent);
        Toast.makeText(this, "Does this work?", Toast.LENGTH_SHORT).show();

    }

    private void updateLabelStartDate() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        startDate.setText(sdf.format(calendarStart.getTime()));
    }

    private void updateLabelEndDate() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        endDate.setText(sdf.format(calendarEnd.getTime()));
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