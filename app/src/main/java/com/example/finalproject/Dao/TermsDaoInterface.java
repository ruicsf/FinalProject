package com.example.finalproject.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.finalproject.Entities.TermTable;

import java.util.List;

@Dao
public interface TermsDaoInterface {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (TermTable term);

    //TODO may have to change to replace instead of ignore
    //TODO may need to add an insert all

    @Delete
    void delete (TermTable term );

    @Update
    void update (TermTable term);

    @Query("DELETE FROM terms_table")
    void deleteAllFromTermsTable();

    @Query("SELECT * FROM terms_table ORDER BY termId ASC") // change to order by todo
    List<TermTable> getAllTermsFromTable();


}
