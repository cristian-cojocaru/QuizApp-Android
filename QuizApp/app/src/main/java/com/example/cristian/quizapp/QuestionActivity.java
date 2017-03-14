package com.example.cristian.quizapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Vector;


public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GREEN = -16711936;
    private static final int RED = -65536;
    private DatabaseQuestion db;
    List<Question> questionsList;
    TextView question, ans1, ans2, ans3, ans4, answer, scoreText;
    Button nextQ;
    Button[] b = new Button[4];
    int score = 0;
    int nrq = 0;
    //Vector<Integer> idq = new Vector<Integer>(30);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);

        //OnClickListener
        b[0] = (Button) findViewById(R.id.button1);
        b[1] = (Button) findViewById(R.id.button2);
        b[2] = (Button) findViewById(R.id.button3);
        b[3] = (Button) findViewById(R.id.button4);

        b[0].setOnClickListener(this);
        b[1].setOnClickListener(this);
        b[2].setOnClickListener(this);
        b[3].setOnClickListener(this);

        //butonul "next question"
        nextQ = (Button) findViewById(R.id.nextQuestion);
        nextQ.setOnClickListener(this);


        //get ids from textview and button
        question = (TextView) findViewById(R.id.question);
        ans1 = (TextView) findViewById(R.id.button1);
        ans2 = (TextView) findViewById(R.id.button2);
        ans3 = (TextView) findViewById(R.id.button3);
        ans4 = (TextView) findViewById(R.id.button4);

        //open database and retrive questions
        db = new DatabaseQuestion(this);
        db.open();
        db.createQuestions();
        questionsList = db.getAllQuestions();
        nextQuestion();
        nextQ.setEnabled(false);

    }

//    public int randomNr(int min, int max){
//        return Math.floor(Math.random()*(max-min)+min);
//    }


    public void checkAnswer(int nrButton) {
        answer = (TextView) findViewById(R.id.answer);


        //color depends on the answer

        int ra = questionsList.get(nrq).getra();
        //setting the answer
        if (nrButton == ra) {
            score++;
            answer.setText("Raspuns corect!");
            b[nrButton - 1].setBackgroundColor(GREEN);
            for (int i = 0; i < 4; i++) {
                if (i != ra - 1) {
                    b[i].setBackgroundColor(RED);
                }
            }

        } else {
            answer.setText("Raspuns gresit!");
            b[nrButton - 1].setBackgroundColor(RED);
        }

        //te lasa sa mergi mai departe doar daca ai dat un raspuns
        for (int i = 0; i < 4; i++)
            if (i != nrButton - 1)
                b[i].setEnabled(false);
        nextQ.setEnabled(true);
    }


    public void nextQuestion() {
        scoreText = (TextView) findViewById(R.id.score);
        scoreText.setText("Scor: " + score);

        for (int i = 0; i < 4; i++) {
            b[i].setBackgroundColor(getResources().getColor(R.color.holo_blue_light));
            b[i].setEnabled(true);
        }
        question.setText(nrq + 1 + ". " + questionsList.get(nrq).getQtext());
        ans1.setText(questionsList.get(nrq).getAns1());
        ans2.setText(questionsList.get(nrq).getAns2());
        ans3.setText(questionsList.get(nrq).getAns3());
        ans4.setText(questionsList.get(nrq).getAns4());
        nextQ.setEnabled(false);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Log.e("QuestionActivity", "Optiunea 1");
                checkAnswer(1);
                break;
            case R.id.button2:
                Log.e("QuestionActivity", "Optiunea 2");
                checkAnswer(2);
                break;
            case R.id.button3:
                Log.e("QuestionActivity", "Optiunea 3");
                checkAnswer(3);
                break;
            case R.id.button4:
                Log.e("QuestionActivity", "Optiunea 4");
                checkAnswer(4);
                break;
            case R.id.nextQuestion:
                Log.e("QuestionActivity", "Next Question");
                nrq++;
                if (nrq == 15) {
                    Intent finalActivity = new Intent(this, FinalActivity.class);
                    finalActivity.putExtra("SCORE", score);
                    startActivity(finalActivity);
                    finish();
                    break;
                }
                answer.setText("");
                nextQuestion();
                break;

        }
    }
}

