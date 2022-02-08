package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalproject.R;
import com.example.finalproject.database.DatabaseRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseRepository databaseRepository = new DatabaseRepository(getApplication());
        databaseRepository.getAllTermsFromRepo();

//        TermTable termTable = new TermTable(1, "aloha", "1/1/22", "2/2/22");
//        repository.insert(termTable); todo not working, not sure why, don't really need it

    }


    public void homeEnterBtn(View view) {
        Intent intent = new Intent(this,TermsList.class);
        startActivity(intent);

    }
}