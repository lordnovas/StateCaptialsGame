package com.cs211d.joel.statecaptials;

/*
  Author: Joel Rainey
  Date: 4/23
  Class: CS211D Spring 2015
  Android Project: State Capitals Trivia Game
  Filename: ScoreScreen.java
  Assignment Objective: Create State Capitals Trivia Game. Where the questions, user name and
  score are stored in an SQLite Database
*/

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ScoresScreen extends ActionBarActivity
{
    DataBaseAdapter dataBaseAdapter;
    TextView tv_userScores;
    String userName;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores_screen);

        dataBaseAdapter = new DataBaseAdapter(this);

        Intent i = getIntent();

        userName = i.getExtras().getString("currUser");

        score = i.getExtras().getInt("userScore");

        dataBaseAdapter.insertScore_USERTABLE(userName, score);

        tv_userScores = (TextView)findViewById(R.id.scores);

        String temp = dataBaseAdapter.getAllData_USERTABLE();

        tv_userScores.setText(temp);

    }


    /***********playAgain()**********************/
    public void playAgain(View view)
    {
        Intent i = new Intent(getApplicationContext(),GameScreen.class);
        i.putExtra("userScore", 0);
        i.putExtra("currUser",userName);
        startActivity(i);
    }


    /***********startSplash()********************/
    public void startSplash(View view)
    {
        Intent i = new Intent(getApplicationContext(),SplashScreen.class);
        startActivity(i);
    }


}
