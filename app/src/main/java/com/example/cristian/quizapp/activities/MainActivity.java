package com.example.cristian.quizapp.activities;
import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.example.cristian.quizapp.R;


public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity", "CREATE");

        Button startGame = (Button) findViewById(R.id.startGame);
        Button exit = (Button) findViewById(R.id.exit);
        Button ranking = (Button) findViewById(R.id.credits);
        //Button options = (Button) findViewById(R.id.options);

        startGame.setOnClickListener(this);
        exit.setOnClickListener(this);
        ranking.setOnClickListener(this);
        //options.setOnClickListener(this);


    }

    private void startGame() {
        Log.d("MainActivity", "START-GAME!!");
        Intent newActivity = new Intent(this, QuestionActivity.class);
        startActivity(newActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startGame:
                startGame();
                break;
            case R.id.exit:
                Log.d("MainActivity", "EXIT");
                finish();
                break;
//            case R.id.options:
//                Intent newActivityOptions = new Intent(this, OptionsActivity.class);
//                startActivity(newActivityOptions);
//                break;
            case R.id.credits:
                Intent newActivityRanking = new Intent(this, RankingActivity.class);
                startActivity(newActivityRanking);
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MainActivity", "START");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "RESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MainActivity", "PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity", "STOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity", "DESTROY");
    }
}



