package com.example.cristian.quizapp.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.quizapp.R;
import com.example.cristian.quizapp.activities.RankingActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class FragScore extends Fragment {
    private static final String TAG = "FRAGMENT_SCORE";

    private TextView scoreTextFrag;
    private String name;
    private int score;
    private String link = "http://devandroid.ga/android/insert_user.php";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Fragment", "CREATE");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_score_fragment, container, false);

        scoreTextFrag = (TextView) view.findViewById(R.id.scoreTextFrag);
        /////
        Button btnback = (Button) view.findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        Button savebtn = (Button) view.findViewById(R.id.save);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nicknameEditText = (EditText) view.findViewById(R.id.NicknameEditText);
                name = nicknameEditText.getText().toString();
                try {
                    insertNameinDB();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        //the underlying activity is not clickable
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return view;
    }

    private void insertNameinDB() throws UnsupportedEncodingException {
        if (name.equals("")) {
            Context context = getActivity().getApplicationContext();
            CharSequence text = "Hei, nu ai niciun nume!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {

            //inserare in baza de data a numelui si scorului
            Log.e("INSERT:", name + " " + score);
            new UserInsert().execute();
            Intent newActivityRanking = new Intent(getActivity(), RankingActivity.class);
            startActivity(newActivityRanking);
            getActivity().finish();

        }
    }

    private class UserInsert extends AsyncTask<Void, Void, String> {
        private static final String link = "http://devandroid.ga/android/insert_user.php";
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Se încarcă...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... p) {
            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("name", name);
                conn.setRequestProperty("score", Integer.toString(score));


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                StringBuilder sb = new StringBuilder();
                sb.append(URLEncoder.encode("name", "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(name, "UTF-8"));
                sb.append("&");
                sb.append(URLEncoder.encode("score", "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(Integer.toString(score), "UTF-8"));

                Log.e("DO0", sb.toString());

                writer.write(sb.toString());
                writer.flush();
                writer.close();
                os.close();
                /////////
                BufferedReader buffer = new BufferedReader(new InputStreamReader(new BufferedInputStream(conn.getInputStream())));
                String line;
                StringBuilder sb1 = new StringBuilder();
                while ((line = buffer.readLine()) != null)
                    sb1.append(line + "\n");

                Log.e("DO1", sb1.toString());
                buffer.close();

                conn.connect();
                return "Text sent: " + name + " " + Integer.toString(score);
            } catch (IOException e) {
                e.printStackTrace();
                return "HTTP POST Request failed!";
            }
        }

        @Override
        protected void onProgressUpdate(Void... k) {
            super.onProgressUpdate();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("DO2", s);
        }
    }

    //////////////////////
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated");
        Bundle arguments = getArguments();

        if (arguments != null) {
            score = arguments.getInt("SCORE");
            String text = getResources().getString(R.string.yourScoreIs) + " " + Integer.toString(score);
            scoreTextFrag.setText(text);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach");
    }
}
