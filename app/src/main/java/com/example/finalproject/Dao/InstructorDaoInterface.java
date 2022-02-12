package com.example.finalproject.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.finalproject.Entities.InstructorTable;

import java.util.List;

@Dao
public interface InstructorDaoInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(InstructorTable instructor);

    @Delete
    void delete(InstructorTable instructor);

    @Query("DELETE FROM instructor_table")
    void deleteAllFromInstructorTable();

    @Query("SELECT * FROM instructor_table ORDER BY instructorID ASC")
    List<InstructorTable> getInstructorsFromTable();


}
