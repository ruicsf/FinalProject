package com.example.finalproject.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.finalproject.Entities.AssessmentTable;

import java.util.List;

@Dao
public interface AssessmentDaoInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentTable assessment);

    @Delete
    void delete(AssessmentTable assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAllFromAssessmentTable();

    @Query("SELECT * FROM assessment_table ORDER BY assessmentId ASC")
    List<AssessmentTable> getAssessmentsFromTable();

}
