package com.example.cristian.quizapp.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.cristian.quizapp.R;
import com.example.cristian.quizapp.database.DatabaseQuestion;
import com.example.cristian.quizapp.database.Question;
import com.example.cristian.quizapp.fragments.scoreFragment;

import java.util.List;
import java.util.Vector;


public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GREEN = -16711936;
    private static final int RED = -65536;

    List<Question> questionsList;
    TextView question, ans1, ans2, ans3, ans4, answer, scoreText;
    Button nextQuestionBtn;
    Button[] b = new Button[4];
    int currentIndexQuestion, nrOfQuestions, counter, qid, score = 0;
    Vector<Integer> vectorOfIds = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_question);

        //OnClickListeners
        b[0] = (Button) findViewById(R.id.button1);
        b[1] = (Button) findViewById(R.id.button2);
        b[2] = (Button) findViewById(R.id.button3);
        b[3] = (Button) findViewById(R.id.button4);

        b[0].setOnClickListener(this);
        b[1].setOnClickListener(this);
        b[2].setOnClickListener(this);
        b[3].setOnClickListener(this);

        // "next question" button
        nextQuestionBtn = (Button) findViewById(R.id.nextQuestion);
        nextQuestionBtn.setOnClickListener(this);


        //get ids from textview and buttons
        question = (TextView) findViewById(R.id.question);
        ans1 = (TextView) findViewById(R.id.button1);
        ans2 = (TextView) findViewById(R.id.button2);
        ans3 = (TextView) findViewById(R.id.button3);
        ans4 = (TextView) findViewById(R.id.button4);

        //open database and retrieve questions
        DatabaseQuestion db;
        db = new DatabaseQuestion(this);
        db.open();
        db.createQuestions();
        questionsList = db.getAllQuestions();
        nextQuestionBtn.setEnabled(false);

        //particular case: display first question
        //other questions will be displayed by nextQuestion function
        nrOfQuestions = questionsList.size();
        vectorOfIds = new Vector<>();

        for (int i = 0; i < nrOfQuestions; i++)
            vectorOfIds.add(i, i);

        currentIndexQuestion = randomNr(0, nrOfQuestions);//generate a random index
        qid = vectorOfIds.get(currentIndexQuestion);
        nextQuestion(vectorOfIds.get(currentIndexQuestion));
        vectorOfIds.remove(currentIndexQuestion);
        nrOfQuestions--;
    }

    //function returns a random value
    public int randomNr(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min) + min);
    }

    //check if answer is right or wrong
    public void checkAnswer(int nrButton) {
        answer = (TextView) findViewById(R.id.answer);

        //color depends on the answer
        int rightAnswer = questionsList.get(qid).getra();
        //setting the answer
        if (nrButton == rightAnswer) {
            score++;
            answer.setText(getResources().getString(R.string.raspunsCorect));
            b[nrButton - 1].setBackgroundColor(GREEN);
            for (int i = 0; i < 4; i++) {
                if (i != rightAnswer - 1) {
                    b[i].setBackgroundColor(RED);
                }
            }

        } else {
            answer.setText(getResources().getString(R.string.raspunsGresit));
            b[nrButton - 1].setBackgroundColor(RED);
        }

        //don't allow to go farther if you have not given an answer
        for (int i = 0; i < 4; i++)
            if (i != nrButton - 1)
                b[i].setEnabled(false);
        nextQuestionBtn.setEnabled(true);
    }

    public void nextQuestion(int id) {
        scoreText = (TextView) findViewById(R.id.score);
        scoreText.setText(getResources().getString(R.string.score) + " " + score);

        for (int i = 0; i < 4; i++) {
            //create an gradient drawable object and set buttons background
            GradientDrawable btncolor = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0xffffffff, 0xffcccccc, 0xffffffff});
            btncolor.setCornerRadius(5);
            btncolor.setStroke(2, Color.BLACK);
            b[i].setBackground(btncolor);
            b[i].setEnabled(true);
        }
        //update the text of buttons and textview
        question.setText(counter + 1 + ". " + questionsList.get(id).getQtext());
        ans1.setText(questionsList.get(id).getAns1());
        ans2.setText(questionsList.get(id).getAns2());
        ans3.setText(questionsList.get(id).getAns3());
        ans4.setText(questionsList.get(id).getAns4());
        nextQuestionBtn.setEnabled(false);
    }

    public void showScore() {
        FragmentManager fragmentManager = getFragmentManager();

        Bundle bun = new Bundle();
        bun.putInt("SCORE", score);

        scoreFragment fragmentObj = new scoreFragment();
        fragmentObj.setArguments(bun);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragmentObj);
        fragmentTransaction.commit();
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
                counter++;
                if (counter == 15) {
                    showScore();
                } else {
                    answer.setText("");
                    currentIndexQuestion = randomNr(0, nrOfQuestions);//generate a random index
                    qid = vectorOfIds.get(currentIndexQuestion);
                    nextQuestion(vectorOfIds.get(currentIndexQuestion));
                    vectorOfIds.remove(currentIndexQuestion);
                    nrOfQuestions--;
                    break;
                }
        }
    }
}

