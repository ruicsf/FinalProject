package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;

import com.example.finalproject.Entities.TermTable;
import com.example.finalproject.R;
import com.example.finalproject.database.DatabaseRepository;

import java.util.Calendar;

public class EditTerm extends AppCompatActivity {

    DatabaseRepository dbDatabaseRepository;
    int id;
    String title,start,end;

    EditText editTermTitle,editTermStart,editTermEnd;

    TermTable selectedTerm;
    int numberOfCourses;

    //todo may have imported wrong Calendar
    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener startListener;
    DatePickerDialog.OnDateSetListener endListener;

    Long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);

        id = getIntent().getIntExtra("termId", -1);
        if (id == -1){
            // todo add missing code for courses id = add
        }

        dbDatabaseRepository = new DatabaseRepository(getApplication());
        for (TermTable term: dbDatabaseRepository.getAllTermsFromRepo()){
            selectedTerm = term;
            title = term.getTermTitle();
            start = term.getStartOfTerm();
            end = term.getEndOfTerm();
        }

//        editTermTitle = findViewById(R.id.ter)



    }


}