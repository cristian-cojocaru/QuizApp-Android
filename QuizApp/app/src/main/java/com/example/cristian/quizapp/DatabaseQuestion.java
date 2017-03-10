package com.example.cristian.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseQuestion {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.KEY_QID,
            DatabaseHelper.KEY_QTEXT,
            DatabaseHelper.KEY_QANS1,
            DatabaseHelper.KEY_QANS2,
            DatabaseHelper.KEY_QANS3,
            DatabaseHelper.KEY_QANS4,
            DatabaseHelper.KEY_QRA,
    };

    public DatabaseQuestion(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public void addQuestion(Question q) {

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.KEY_QID, q.getid());
        values.put(DatabaseHelper.KEY_QTEXT, q.getQtext());
        values.put(DatabaseHelper.KEY_QANS1, q.getAns1());
        values.put(DatabaseHelper.KEY_QANS2, q.getAns2());
        values.put(DatabaseHelper.KEY_QANS3, q.getAns3());
        values.put(DatabaseHelper.KEY_QANS4, q.getAns4());
        values.put(DatabaseHelper.KEY_QRA, q.getra());
        // Inserting Row
        database.insert(DatabaseHelper.TABLE_NAME, null, values);
        //close(); // Closing database connection
    }

    public Question getQuestion(int id){
        String[] selectionId = new String[1];
        selectionId[0] = String.valueOf(id);
        Cursor cursor = database.query(
                true,
                DatabaseHelper.TABLE_NAME,
                allColumns,
                DatabaseHelper.KEY_QID + "=?",
                selectionId,
                null, null, null, null
        );

        cursor.moveToFirst();
        Question q = new Question(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                Integer.parseInt(cursor.getString(6))
        );

        return q;
    }

    void createQuestions(){
        Question q1 = new Question(1, "Care este capitala Romaniei?", "Budapesta", "Bucuresti", "Bagdad", "Timisoara",2);
        addQuestion(q1);
        Question q2 = new Question(2, "In ce an a fost infiintat Google?", "1995", "1999", "1998", "2000",3);
        addQuestion(q2);
        Question q3 = new Question(3, "Care este primul rege al Romaniei?", "Carol I", "Mihai I", "Ferdinand", "Carol al 2-lea",1);
        addQuestion(q3);
    }
}
