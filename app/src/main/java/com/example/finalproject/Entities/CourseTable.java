package com.example.finalproject.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class CourseTable {

@PrimaryKey
    private int courseId;

    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String courseNotes;

    private int courseTermId;

    public CourseTable(int courseId, String courseName, String courseStart, String courseEnd, String courseStatus, String courseNotes, int courseTermId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseNotes = courseNotes;
        this.courseTermId = courseTermId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public int getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(int courseTermId) {
        this.courseTermId = courseTermId;
    }
}
