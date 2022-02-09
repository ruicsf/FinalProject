package com.example.finalproject.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "terms_table")
public class TermTable {

    @PrimaryKey() //TODO may not need to autogenerate
    private int termId;

    private String termTitle;
    private String startOfTerm;
    private String endOfTerm;


    public TermTable( int termId, String termTitle, String startOfTerm, String endOfTerm) {
        this.termId = termId;
        this.termTitle = termTitle;
        this.startOfTerm = startOfTerm;
        this.endOfTerm = endOfTerm;
    }


    // Getters & Setters
    public int getTermId() {
        return termId;
    }

    public String getStartOfTerm() {
        return startOfTerm;
    }

    public void setStartOfTerm(String startOfTerm) {
        this.startOfTerm = startOfTerm;
    }

    public void setEndOfTerm(String endOfTerm) {
        this.endOfTerm = endOfTerm;
    }

    public String getEndOfTerm() {
        return endOfTerm;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }


}
