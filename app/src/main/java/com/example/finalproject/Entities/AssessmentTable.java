package com.example.finalproject.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "assessment_table")
public class AssessmentTable {

    @PrimaryKey
    private int assessmentId;
    private String assessmentTitle;
    private String assessmentType;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private int assessmentCourseId;

    public AssessmentTable(int assessmentId, String assessmentTitle, String assessmentType, String assessmentStartDate, String assessmentEndDate, int assessmentCourseId) {
        this.assessmentId = assessmentId;
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentCourseId = assessmentCourseId;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }


    public int getAssessmentCourseId() {
        return assessmentCourseId;
    }

    public void setAssessmentCourseId(int assessmentCourseId) {
        this.assessmentCourseId = assessmentCourseId;
    }
}
