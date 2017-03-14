package com.example.cristian.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public void close() {
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
    }


//  retrive one question
    public Question getQuestion(int id) {
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
        cursor.close();
        return q;
    }

    void createQuestions() {

        Question q1 = new Question(1, "Care este capitala României?", "Budapesta", "București", "Bagdad", "Timișoara", 2);
        addQuestion(q1);
        Question q2 = new Question(2, "În ce an a fost înființat Google?", "1995", "1999", "1998", "2000", 3);
        addQuestion(q2);
        Question q3 = new Question(3, "Care este primul rege al Romaniei?", "Carol I", "Mihai I", "Ferdinand", "Carol al 2-lea", 1);
        addQuestion(q3);
        Question q4 = new Question(4, "Cine este creatorul limbajului de programare C?", "Elon Musk", "Dennis Ritchie", "Steve Jobs", "Bill Gates", 2);
        addQuestion(q4);
        Question q5 = new Question(5, "În ce perioadă a câștigat România independența națională?", "1866-1867", "1879-1880", "1899-1900", "1877-1878", 4);
        addQuestion(q5);
        Question q6 = new Question(6, "În ce țară a avut loc prima ediție a Campionatului Mondial de Fotbal?", "Mexic", "U.S.A.", "Uruguay", "Brazilia", 3);
        addQuestion(q6);
        Question q7 = new Question(7, "În ce an s-a format formația Metallica?", "1980", "1981", "1970", "1989", 2);
        addQuestion(q7);
        Question q8 = new Question(8, "Ce tip de instrument este \"balalaika\"?", "Cu corzi", "Cu clape", "Cu burduf", "Nicio varianta", 1);
        addQuestion(q8);
        Question q9 = new Question(9, "În ce an a devenit Nicolae Ceaușescu președinte al României?", "1960", "1964", "1970", "1965", 4);
        addQuestion(q9);
        Question q10 = new Question(10, "Unde a avut loc cea mai mare bătalie de tancuri din istorie?", "Stalingrad", "Kursk", "Berlin", "Sevastopol", 2);
        addQuestion(q10);
        Question q11 = new Question(11, "Care este cel mai întins județ al României?", "Suceava", "Tulcea", "Timiș", "Arad", 3);
        addQuestion(q11);
        Question q12 = new Question(12, "Câti ani a durat războiul de 100 de ani?", "100", "116", "50", "Niciuna din variante", 2);
        addQuestion(q12);
        Question q13 = new Question(13, "Cine a fost al 2-lea om pe luna?", "Yuri Gagarin", "Neil Armstrong", "Buzz Aldrin", "Dumitru Prunariu", 3);
        addQuestion(q13);
        Question q14 = new Question(14, "Cine a arhitectul care a proiectat pentru romani podul peste Dunăre în războiul daco-roman?", "Anghel Saligny", "Apolodor din Damasc", "Santiago Calatrava", "Regele Aurelianus", 2);
        addQuestion(q14);
        Question q15 = new Question(15, "Cine a spus \"Pe aici nu se trece!\"?", "Gheorghe Avramescu", "Ecaterina Teodoroiu", "Eremia Grigorescu", "Ion Antonescu", 3);
        addQuestion(q15);

    }

    List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<Question>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question q = new Question(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    Integer.parseInt(cursor.getString(6))
            );
            questions.add(q);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return questions;
    }
}
