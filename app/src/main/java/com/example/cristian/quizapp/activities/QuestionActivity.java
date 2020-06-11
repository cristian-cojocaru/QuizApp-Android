package com.example.cristian.quizapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.cristian.quizapp.R;
import com.example.cristian.quizapp.database.DatabaseQuestion;
import com.example.cristian.quizapp.database.Question;
import com.example.cristian.quizapp.fragments.FragStartGame;
import com.example.cristian.quizapp.fragments.FragScore;

import java.util.List;
import java.util.Vector;



public class QuestionActivity extends Activity implements View.OnClickListener {
    List<Question> questionsList;
    TextView question, answer, scoreText;
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

        //showFragStartGame();

        //get ids from buttons
        b[0] = (Button) findViewById(R.id.button1);
        b[1] = (Button) findViewById(R.id.button2);
        b[2] = (Button) findViewById(R.id.button3);
        b[3] = (Button) findViewById(R.id.button4);

        //OnClickListeners
        b[0].setOnClickListener(this);
        b[1].setOnClickListener(this);
        b[2].setOnClickListener(this);
        b[3].setOnClickListener(this);

        // "next question" button
        nextQuestionBtn = (Button) findViewById(R.id.nextQuestion);
        nextQuestionBtn.setOnClickListener(this);


        //get ids from textviews
        question = (TextView) findViewById(R.id.question);


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
        qid = vectorOfIds.get(currentIndexQuestion); //qid va fi id-ul urmatoarei intrebari qid = vector[random_index]
        nextQuestion(qid); //se afiseaza intrebarea cu id-ul din qid
        vectorOfIds.remove(currentIndexQuestion);//se sterge din vector qid
        nrOfQuestions--;//se scade numarul de intrebari ramase


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
            b[nrButton - 1].setBackgroundColor(Color.GREEN);
            for (int i = 0; i < 4; i++) {
                if (i != rightAnswer - 1) {
                    b[i].setBackgroundColor(Color.RED);
                }
            }

        } else {
            answer.setText(getResources().getString(R.string.raspunsGresit));
            b[nrButton - 1].setBackgroundColor(Color.RED);
        }

        //don't allow to go further if you have not given an answer
        for (int i = 0; i < 4; i++)
            if (i != nrButton - 1)
                b[i].setEnabled(false);
        nextQuestionBtn.setEnabled(true);
    }

    public void nextQuestion(int id) {
//        answer = (TextView) findViewById(R.id.answer);
//        new CountDownTimer(10000, 100) {
//            public void onTick(long millisUntilFinished) {
//                answer.setText("Mai ai "+ millisUntilFinished / 1000+ "."+ (millisUntilFinished / 100)%10+" ");
//            }
//            public void onFinish() {
//                answer.setText("");
//                currentIndexQuestion = randomNr(0, nrOfQuestions);//generate a random index
//                qid = vectorOfIds.get(currentIndexQuestion);
//                nextQuestion(qid);
//                vectorOfIds.remove(currentIndexQuestion);
//                nrOfQuestions--;
//            }
//        }.start();
        scoreText = (TextView) findViewById(R.id.score);
        scoreText.setText(getResources().getString(R.string.score) + " " + score);

        for (int i = 0; i < 4; i++) {
            //create an gradient drawable object and set buttons background
            GradientDrawable btncolor = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{Color.WHITE, Color.LTGRAY, Color.WHITE});
            btncolor.setCornerRadius(5);
            btncolor.setStroke(2, Color.BLACK);
            b[i].setBackground(btncolor);
            b[i].setEnabled(true);
        }
        //update the text of buttons and textview
        question.setText(counter + 1 + ". " + questionsList.get(id).getQtext());
        b[0].setText(questionsList.get(id).getAns1());
        b[1].setText(questionsList.get(id).getAns2());
        b[2].setText(questionsList.get(id).getAns3());
        b[3].setText(questionsList.get(id).getAns4());
        nextQuestionBtn.setEnabled(false);

    }

    private void showFragStartGame() {
        FragmentManager fragmentManager = getFragmentManager();
        FragStartGame fragmentObj = new FragStartGame();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.nicknameFragment, fragmentObj);
        fragmentTransaction.commit();

    }

    private void showFragScore() {
        FragmentManager fragmentManager = getFragmentManager();

        Bundle bun = new Bundle();
        bun.putInt("SCORE", score);

        FragScore fragmentObj = new FragScore();
        fragmentObj.setArguments(bun);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.scoreFragment, fragmentObj);
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
                    showFragScore();
                } else {
                    answer.setText("");
                    currentIndexQuestion = randomNr(0, nrOfQuestions);//generate a random index
                    qid = vectorOfIds.get(currentIndexQuestion);
                    nextQuestion(qid);
                    vectorOfIds.remove(currentIndexQuestion);
                    nrOfQuestions--;
                    break;
                }
        }
    }
}

