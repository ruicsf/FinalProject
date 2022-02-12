package com.example.finalproject.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.finalproject.Dao.AssessmentDaoInterface;
import com.example.finalproject.Dao.CourseDaoInterface;
import com.example.finalproject.Dao.InstructorDaoInterface;
import com.example.finalproject.Dao.TermsDaoInterface;
import com.example.finalproject.Entities.AssessmentTable;
import com.example.finalproject.Entities.CourseTable;
import com.example.finalproject.Entities.InstructorTable;
import com.example.finalproject.Entities.TermTable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {TermTable.class, CourseTable.class, AssessmentTable.class, InstructorTable.class}, version = 6)
public abstract class SchedulerDatabase extends RoomDatabase {
    public abstract TermsDaoInterface termDao();
    public abstract CourseDaoInterface courseDao();
    public abstract AssessmentDaoInterface assessmentDao();
    public abstract InstructorDaoInterface instructorDao();

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
            CourseDaoInterface courseDao = instance.courseDao();
            AssessmentDaoInterface assessmentDao = instance.assessmentDao();
            InstructorDaoInterface instructorDao = instance.instructorDao();


            dbWriteExecutor.execute(()->{

                termDao.deleteAllFromTermsTable();
                courseDao.deleteAllFromCourseTable();
                assessmentDao.deleteAllFromAssessmentTable();
                instructorDao.deleteAllFromInstructorTable();


                TermTable termDbTable = new TermTable(1,"Term 1", "02/01/2022", "02/28/2022");
                termDao.insert(termDbTable);
                TermTable terDbTable2 = new TermTable(2,"Term 2", "03/01/2022", "03/28/2022");
                termDao.insert(terDbTable2);


                CourseTable courseDbTable = new CourseTable(101, "C156", "02/01/2022","02/28/2022", "Complete","First programming course",1);
                CourseTable courseDbTable2 = new CourseTable(102, "C176", "03/01/2022","03/30/2022", "In Progress","second programming course",1);
                CourseTable courseDbTable3 = new CourseTable(103, "C196", "04/01/2022","04/30/2022", "Incomplete","third programming course",1);
                courseDao.insert(courseDbTable);
                courseDao.insert(courseDbTable2);
                courseDao.insert(courseDbTable3);
                CourseTable courseDbTable11 = new CourseTable(104, "C256", "05/01/2022","05/28/2022", "Incomplete","fourth programming course",2);
                CourseTable courseDbTable12 = new CourseTable(105, "C286", "06/01/2022","06/30/2022", "Incomplete","fifth programming course",2);
                CourseTable courseDbTable13 = new CourseTable(106, "C296", "07/01/2022","07/30/2022", "Incomplete","sixth programming course",2);
                courseDao.insert(courseDbTable11);
                courseDao.insert(courseDbTable12);
                courseDao.insert(courseDbTable13);

                AssessmentTable assessmentTable = new AssessmentTable(201,"Web Applications", "Objective", "12/01/2021", 101);
                AssessmentTable assessmentTable2 = new AssessmentTable(202,"Database Applications", "Performance", "01/01/2022",  102);
                assessmentDao.insert(assessmentTable);
                assessmentDao.insert(assessmentTable);

                InstructorTable instructorTable = new InstructorTable(301, "Rui Fernandes", "209-333-4587", "ruifern@gmail.com", 101);
                InstructorTable instructorTable2 = new InstructorTable(302, "Lindsey Yip", "928-333-4587", "johnyb@gmail.com", 102);
                instructorDao.insert(instructorTable);
                instructorDao.insert(instructorTable);


            });
        }

    };

}
