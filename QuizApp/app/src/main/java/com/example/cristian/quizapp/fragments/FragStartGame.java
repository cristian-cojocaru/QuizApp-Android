package com.example.cristian.quizapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cristian.quizapp.R;


public class FragStartGame extends Fragment {
    private TextView startFragText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("FragStartGame", "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("FragmentStartGame", "onCreate");
    }

    private void closeFragment() {
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_start_game, container, false);
        Log.e("FragmentStartGame","onCreateView");
        startFragText = (TextView) view.findViewById(R.id.startFragmentText);
        new CountDownTimer(5000, 100) {
            public void onTick(long millisUntilFinished) {
                startFragText.setText("Jocul începe în \n " + millisUntilFinished / 1000 + " ");
            }

            public void onFinish() {
                closeFragment();
            }
        }.start();

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("FragmentStartGame","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("FragStartGame", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("FragStartGame", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("FragStartGame", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("FragStartGame", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("FragStartGame", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("FragStartGame", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("FragStartGame", "onDetach");
    }
}
