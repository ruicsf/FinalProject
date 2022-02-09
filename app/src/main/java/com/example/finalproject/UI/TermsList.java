package com.example.finalproject.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject.Entities.TermTable;
import com.example.finalproject.R;
import com.example.finalproject.Adapters.TermAdapter;
import com.example.finalproject.database.DatabaseRepository;

import java.util.List;

public class TermsList extends AppCompatActivity {



    private RecyclerView mRecyclerView;
    private TermAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    DatabaseRepository databaseRepository;

    public List<TermTable> termTableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // may need something here todo
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Deals with adapter and recyleview
        getTermsList();
        buildRecyclerView();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // todo add code for delete not allowed here with courses
                databaseRepository.delete(mAdapter.getTermAt(viewHolder.getAdapterPosition()));
                mAdapter.mTermsList.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Toast.makeText(TermsList.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

//        mAdapter.setTermOnItemClickListener(new TermAdapter.TermOnItemClickListener() {
//            @Override
//            public void termOnItemClick(TermTable term) {
//                Intent intent = new Intent(TermsList.this, EditTerm.class);
//                intent.putExtra(AddTerm.EXTRA_ID, term.getTermId());
//                intent.putExtra(AddTerm.EXTRA_TITLE, term.getTermTitle());
//                intent.putExtra(AddTerm.EXTRA_START, term.getStartOfTerm());
//                intent.putExtra(AddTerm.EXTRA_END, term.getEndOfTerm());
//
//                startActivity(intent);
//            }
//        });
    }

    public void getTermsList() {
        databaseRepository = new DatabaseRepository(getApplication());
        termTableList = databaseRepository.getAllTermsFromRepo();
        //todo add course and assessment list here
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyler_view_terms);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManger = new LinearLayoutManager(this);
        mAdapter = new TermAdapter(this);
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.termsSetter(databaseRepository.getAllTermsFromRepo());
        //todo add course version here
    }

    public void addTermOnClick(View view) {
        Intent intent = new Intent(this, AddTerm.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_terms:
                databaseRepository.deleteAllTerms();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}