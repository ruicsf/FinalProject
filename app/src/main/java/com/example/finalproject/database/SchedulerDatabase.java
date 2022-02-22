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


@Database(entities = {TermTable.class, CourseTable.class, AssessmentTable.class, InstructorTable.class}, version = 7)
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
//                TermTable terDbTable3 = new TermTable(3,"Term 3", "04/01/2022", "04/28/2022");
//                termDao.insert(terDbTable3);



                CourseTable courseDbTable = new CourseTable(101, "Course 1", "02/01/2022","02/28/2022", "Complete","First programming course",1);
                courseDao.insert(courseDbTable);
                CourseTable courseDbTable2 = new CourseTable(102, "Course 2", "03/01/2022","03/30/2022", "In Progress","second programming course",1);
                courseDao.insert(courseDbTable2);
                CourseTable courseDbTable3 = new CourseTable(103, "Course 3", "04/01/2022","04/30/2022", "Dropped","third programming course",2);
                courseDao.insert(courseDbTable3);
                CourseTable courseDbTable11 = new CourseTable(104, "Course 4", "05/01/2022","05/28/2022", "Incomplete","fourth programming course",2);
                courseDao.insert(courseDbTable11);
//                CourseTable courseDbTable12 = new CourseTable(105, "Course 5", "06/01/2022","06/30/2022", "Incomplete","fifth programming course",2);
//                courseDao.insert(courseDbTable12);
//                CourseTable courseDbTable13 = new CourseTable(106, "Course 6", "07/01/2022","07/30/2022", "Incomplete","sixth programming course",2);
//                courseDao.insert(courseDbTable13);
//


                AssessmentTable assessmentTable = new AssessmentTable(201,"Assessment 1", "Objective", "12/01/2021", "12/01/2021", 101);
                assessmentDao.insert(assessmentTable);
                AssessmentTable assessmentTable2 = new AssessmentTable(202,"Assessment 2", "Performance", "01/01/2022",  "01/01/2022", 102);
                assessmentDao.insert(assessmentTable2);
                AssessmentTable assessmentTable3 = new AssessmentTable(203,"Assessment 3", "Performance", "02/01/2022",  "03/01/2022", 103);
                assessmentDao.insert(assessmentTable3);
                AssessmentTable assessmentTable4 = new AssessmentTable(204,"Assessment 4", "Objective", "03/01/2022", "03/30/2021", 104);
                assessmentDao.insert(assessmentTable4);
                AssessmentTable assessmentTable5 = new AssessmentTable(205,"Assessment 5", "Performance", "04/01/2022", "04/30/2021", 101);
                assessmentDao.insert(assessmentTable5);
                AssessmentTable assessmentTable6 = new AssessmentTable(206,"Assessment 6", "Objective", "03/01/2022", "03/30/2021", 101);
                assessmentDao.insert(assessmentTable4);
                AssessmentTable assessmentTable7 = new AssessmentTable(207,"Assessment 7", "Performance", "04/01/2022", "04/30/2021", 102);
                assessmentDao.insert(assessmentTable5);



                InstructorTable instructorTable = new InstructorTable(301, "Instructor 1", "209-333-4587", "ruifern@wgue.edu", 101);
                instructorDao.insert(instructorTable);
                InstructorTable instructorTable2 = new InstructorTable(302, "Instructor 2", "928-333-4587", "johnyb@wgu.edu", 102);
                instructorDao.insert(instructorTable2);
                InstructorTable instructorTable3 = new InstructorTable(303, "Instructor 3", "928-333-4587", "joe@wgu.edu", 103);
                instructorDao.insert(instructorTable3);
                InstructorTable instructorTable4 = new InstructorTable(304, "Instructor 4", "928-333-4587", "kim@wgu.edu", 104);
                instructorDao.insert(instructorTable4);
                InstructorTable instructorTable5 = new InstructorTable(305, "Instructor 5", "928-333-4587", "james@wgu.edu", 101);
                instructorDao.insert(instructorTable3);
                InstructorTable instructorTable6 = new InstructorTable(306, "Instructor 6", "928-333-4587", "jill@wgu.edu", 102);
                instructorDao.insert(instructorTable4);







            });
        }

    };

}
