package com.example.cristian.quizapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;



public class Main2Activity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        Button btn4 = (Button) findViewById(R.id.button4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);


        TextView question = (TextView) findViewById(R.id.question);
        TextView ans1 = (TextView) findViewById(R.id.button1);
        TextView ans2 = (TextView) findViewById(R.id.button2);
        TextView ans3 = (TextView) findViewById(R.id.button3);
        TextView ans4 = (TextView) findViewById(R.id.button4);

        DatabaseHandler db = new DatabaseHandler(this);

        Question q = db.getQuestion(1);
        question.setText(q.getQtext());
        ans1.setText(q.getAns1());
        ans2.setText(q.getAns2());
        ans3.setText(q.getAns3());
        ans4.setText(q.getAns4());
    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Log.e("Main2Activity", "de ce plm nu merge? :");
                break;
            case R.id.button2:
                Log.e("Main2Activity", "Optiunea 2");
                break;
            case R.id.button3:
                Log.e("Main2Activity", "Optiunea 3");
                break;
            case R.id.button4:
                Log.e("Main2Activity", "Optiunea 4");
                break;

        }
    }
}

abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // this is a generic way of getting your root view element
        View rootView = (View) findViewById(android.R.id.content);
        rootView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
    }
}