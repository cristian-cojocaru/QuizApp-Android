package com.example.cristian.quizapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.cristian.quizapp.HTTPConnection.HttpHandler;
import com.example.cristian.quizapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RankingActivity extends Activity {

    private ListView lv;
    private ProgressDialog pDialog;
    private static String url = "http://devandroid.ga/android/get_users.php";
    ArrayList<HashMap<String, String>> questionList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ranking);

        questionList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list_view);
        new getRank().execute();
    }


    private class getRank extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RankingActivity.this);
            pDialog.setMessage("Se încarcă...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler helper = new HttpHandler();

            // Making a request to url and getting response
            String jsonString = helper.makeServiceCall(url);
            Log.e("RankingActivity", "Response from url: " + jsonString);
            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);

                    // Getting JSON Array node
                    JSONArray users = jsonObj.getJSONArray("users");

                    // looping through All questions
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String score = c.getString("score");
                        String data = c.getString("data");

                        // tmp hash map for single contact
                        HashMap<String, String> user = new HashMap<>();

                        // adding each child node to HashMap key => value

                        user.put("name", "Nume: "+ name);
                        user.put("score", "Scor: " + score);
                        user.put("data", "Data: " + data);

                        // adding contact to contact list
                        questionList.add(user);
                    }
                } catch (final JSONException e) {
                    Log.e("RankingActivity", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e("RankingActivity", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server.", Toast.LENGTH_LONG).show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    RankingActivity.this, questionList,
                    R.layout.list_item, new String[]{"id", "name", "score","data"},
                    new int[]{R.id.id, R.id.name, R.id.user_score,R.id.data});

            lv.setAdapter(adapter);
        }
    }


}

