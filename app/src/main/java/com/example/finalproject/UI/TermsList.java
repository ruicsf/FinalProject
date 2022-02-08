package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.finalproject.Entities.TermTable;
import com.example.finalproject.R;
import com.example.finalproject.TermAdapter;
import com.example.finalproject.database.DatabaseRepository;

import java.util.List;

public class TermsList extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TermAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    DatabaseRepository databaseRepository;

    List<TermTable> termTableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // may need something here todo
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getTermsList();
        buildRecyclerView();

        // gets and sets items in termTable to list

        mRecyclerView = findViewById(R.id.recyler_view_terms);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManger = new LinearLayoutManager(this);

        mAdapter = new TermAdapter(termTableList);

        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);


    }

    public void getTermsList(){
        databaseRepository = new DatabaseRepository(getApplication());
        termTableList = databaseRepository.getAllTermsFromRepo();
    }

    public void buildRecyclerView (){
        mRecyclerView = findViewById(R.id.recyler_view_terms);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManger = new LinearLayoutManager(this);

        mAdapter = new TermAdapter(termTableList);

        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void addTermOnClick(View view) {
        Intent intent = new Intent(this, AddTerm.class);
        startActivity(intent);
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