package com.example.cristian.quizapp;


import android.content.Context;

public class Question {
    private int qid, ra;
    private String qtext, ans1, ans2, ans3, ans4;


    public Question() {
    }

    public Question(int qid, String qtext, String ans1, String ans2, String ans3, String ans4, int ra) {
        this.qid = qid;
        this.qtext = qtext;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.ra = ra;
    }

    public int getid() {
        return this.qid;
    }

    public String getQtext() {
        return this.qtext;
    }

    public String getAns1() {
        return this.ans1;
    }

    public String getAns2() {
        return this.ans2;
    }

    public String getAns3() {
        return this.ans3;
    }

    public String getAns4() {
        return this.ans4;
    }
    public int getra(){
        return this.ra;
    }

}
