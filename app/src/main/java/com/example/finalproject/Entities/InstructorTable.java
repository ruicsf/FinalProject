package com.example.finalproject.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "instructor_table")
public class InstructorTable {

    @PrimaryKey
    private int instructorID;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int instructorCourseId;

    public InstructorTable(int instructorID, String instructorName, String instructorPhone, String instructorEmail, int instructorCourseId) {
        this.instructorID = instructorID;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.instructorCourseId = instructorCourseId;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public int getInstructorCourseId() {
        return instructorCourseId;
    }

    public void setInstructorCourseId(int instructorCourseId) {
        this.instructorCourseId = instructorCourseId;
    }
}
