package com.example.finalproject.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.finalproject.Dao.TermsDaoInterface;
import com.example.finalproject.Entities.TermTable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {TermTable.class}, version = 3)
public abstract class SchedulerDatabase extends RoomDatabase {
    public abstract TermsDaoInterface termDao();

    private static int NUMBER_OF_THREADS = 4;
    static ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile SchedulerDatabase instance;


    // todo Look at adams if this doesn't work
    public static SchedulerDatabase getDatabaseInstance(Context context) {
        if (instance == null) {
            synchronized (SchedulerDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        SchedulerDatabase.class, "database_scheduler")
                        .fallbackToDestructiveMigration()
                        .addCallback(dbCallback) // used for adding data to database
                        .build();
            }

        }
        return instance;
    }


    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            TermsDaoInterface termDao = instance.termDao();
            //coursedao
            //assessmentdao

            dbWriteExecutor.execute(()->{
                termDao.deleteAllFromTermsTable();
                // todo other table here

                TermTable termDbTable = new TermTable(1,"Term 1", "02/01/2022", "02/28/2022");
                termDao.insert(termDbTable);
                TermTable terDbTable2 = new TermTable(2,"Term 2", "03/01/2022", "03/28/2022");
                termDao.insert(terDbTable2);
//                TermTable terDbTable3 = new TermTable(3, "Term 3", "04/01/2022", "04/28/2022");
//                termDao.insert(terDbTable3);

            });
        }

    };
//    private static Callback roomCallback = new Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//
//        }
//
//    };
//    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
//        private TermsDaoInterface termDao;
//
//        private PopulateDbAsyncTask (TermsRoomDatabase db){
//            termDao = db.termDao();
//        }
//        @RequiresApi(api = Build.VERSION_CODES.O)
//        @Override
//        protected Void doInBackground (Void...voids){
//            termDao.insert(new TermTable("Term1"));
//            termDao.insert(new TermTable("Term2"));
//            termDao.insert(new TermTable("Term3"));
//            return null;
//        }
//    }
}
