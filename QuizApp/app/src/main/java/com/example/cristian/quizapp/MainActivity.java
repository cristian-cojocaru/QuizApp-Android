package com.example.cristian.quizapp;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity", "PORNIT");




        Button startGame = (Button) findViewById(R.id.startGame);
        Button exit = (Button) findViewById(R.id.exit);
        Button credits = (Button) findViewById(R.id.credits);
        Button options = (Button) findViewById(R.id.options);
        startGame.setOnClickListener(this);
        exit.setOnClickListener(this);
        credits.setOnClickListener(this);
        options.setOnClickListener(this);


    }

    private void startGame() {
        Log.d("MainActivity", "A FOST PE AICI 1!!");
        Intent newActivity = new Intent(this, Main2Activity.class);
        startActivity(newActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startGame:
                startGame();

                break;
            case R.id.exit:
                Log.d("MainActivity", "A FOST PE AICI: exit !!!!");
                finish();
                break;
            case R.id.options:
                Log.d("MainActivity", "A FOST PE AICI: options !!!!");
                break;
            case R.id.credits:
                Log.d("MainActivity", "A FOST PE AICI: credits !!!!");
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



