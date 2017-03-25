package com.example.cristian.quizapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "QuizQuestions";
    public static final String KEY_QID = "qId";
    public static final String KEY_QTEXT = "qText";
    public static final String KEY_QANS1 = "qAns1";
    public static final String KEY_QANS2 = "qAns2";
    public static final String KEY_QANS3 = "qAns3";
    public static final String KEY_QANS4 = "qAns4";
    public static final String KEY_QRA = "qRightAnswer";

    private static final String DATABASE_NAME = "questions.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE " + TABLE_NAME + " ( "
            + KEY_QID + " INTGER PRIMARY KEY, "
            + KEY_QTEXT + " TEXT, "
            + KEY_QANS1 + " TEXT, "
            + KEY_QANS2 + " TEXT, "
            + KEY_QANS3 + " TEXT, "
            + KEY_QANS4 + " TEXT, "
            + KEY_QRA + " TEXT " + " ) ;";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
