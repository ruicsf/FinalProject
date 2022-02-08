package com.example.finalproject.database;

import android.app.Application;

import com.example.finalproject.Dao.TermsDaoInterface;
import com.example.finalproject.Entities.TermTable;

import java.util.List;

public class DatabaseRepository {

    private TermsDaoInterface termDao;

    private List<TermTable> allTerms;
    private int TermId;

    // Todo update constructor here
    public DatabaseRepository(Application application){
        SchedulerDatabase database = SchedulerDatabase.getDatabaseInstance(application);
        termDao = database.termDao();

        //todo add coursedao and assessmentdao

        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    // Terms getter
    public List<TermTable> getAllTermsFromRepo() {
        SchedulerDatabase.dbWriteExecutor.execute(()->{
            allTerms = termDao.getAllTermsFromTable();
        });
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return allTerms;
    }

    // Terms insert
    public void insert(TermTable termTable){
        SchedulerDatabase.dbWriteExecutor.execute(()->{
            termDao.insert(termTable);
        });
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete (TermTable termTable){
        SchedulerDatabase.dbWriteExecutor.execute(()->{
            termDao.delete(termTable);
        });
    }

}
