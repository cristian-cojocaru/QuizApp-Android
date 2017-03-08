package com.example.cristian.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuestionQuiz";
    private static final String TABLE_NAME = "questions";
    private static final String KEY_QID = "qid";
    private static final String KEY_QTEXT = "qtext";
    private static final String KEY_ANS1 = "ans1";
    private static final String KEY_ANS2 = "ans2";
    private static final String KEY_ANS3 = "ans3";
    private static final String KEY_ANS4 = "ans4";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTIONS_TABLE =
                "CREATE TABLE " + TABLE_NAME
                        + "(" + KEY_QID + " INTEGER PRIMARY KEY,"
                        + KEY_QTEXT + " TEXT,"
                        + KEY_ANS1 + " TEXT,"
                        + KEY_ANS2 + " TEXT,"
                        + KEY_ANS3 + " TEXT,"
                        + KEY_ANS4 + " TEXT" + ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);
        //creating questions
        Question q1 = new Question(1, "Care este capitala Romaniei?", "Budapesta", "Bucuresti", "Bagdad", "Timisoara");
        addQuestion(q1);
        Question q2 = new Question(2, "In ce an a fost infiintat Google?", "1995", "1999", "1998", "2000");
        addQuestion(q2);
        Question q3 = new Question(3, "Care este primul rege al Romaniei?", "Carol I", "Mihai I", "Ferdinand", "Carol al 2-lea");
        addQuestion(q3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //////////////////
    // CRUD METHODS (create, read, update, delete)
    public void addQuestion(Question q) {

        SQLiteDatabase dbase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QID, q.getid());
        values.put(KEY_QTEXT, q.getQtext());
        values.put(KEY_ANS1, q.getAns1());
        values.put(KEY_ANS2, q.getAns2());
        values.put(KEY_ANS3, q.getAns3());
        values.put(KEY_ANS4, q.getAns4());
        // Inserting Row
        dbase.insert(TABLE_NAME, null, values);
        dbase.close(); // Closing database connection
    }

    ///read
    Question getQuestion(int id) {

        SQLiteDatabase dbase = this.getReadableDatabase();

        Cursor cursor = dbase.query(true ,
                TABLE_NAME,
                new String[]{
                        KEY_QID,
                        KEY_QTEXT,
                        KEY_ANS1,
                        KEY_ANS2,
                        KEY_ANS3,
                        KEY_ANS4
                },
                KEY_QID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor.getCount()>1)
            cursor.moveToFirst();

        try {
            Integer.parseInt(cursor.getString(0));
        } catch (Exception e) {
            Log.e("plm :", e.getMessage());
        }

        Question q = new Question(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );

        return q;

    }

//    public List<Question> getQuestions(){
//        List<Question> qlist = new ArrayList<Question>();
//        String select = "SELECT * FROM " + TABLE_NAME;
//        SQLiteDatabase db  =  this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(select, null);
//
//        if(cursor.moveToFirst()){
//            do {
//
//            }
//        }
//    }
}