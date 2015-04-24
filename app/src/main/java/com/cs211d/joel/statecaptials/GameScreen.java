package com.cs211d.joel.statecaptials;

/*
  Author: Joel Rainey
  Date: 4/23
  Class: CS211D Spring 2015
  Android Project: State Capitals Trivia Game
  Filename: GameScreen.java
  Assignment Objective: Create State Capitals Trivia Game. Where the questions, user name and
  score are stored in an SQLite Database
*/

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class GameScreen extends ActionBarActivity
{

    String currUser;
    int numRounds = 0;
    int score=0;
    InputStream is;
    CSVReader csvReader;
    Random rand = new Random();
    ArrayList<String[]> stateList;
    DataBaseAdapter dataBaseAdapter;
    TextView mtv_questionText, tv_numRounds,tv_score,tv_userName;
    EditText et_cap;
    String answer ="";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent i = getIntent();
        currUser = i.getExtras().getString("currUser");

        dataBaseAdapter = new DataBaseAdapter(this);

        mtv_questionText = (TextView)findViewById(R.id.tv_questions);
        tv_numRounds = (TextView)findViewById(R.id.numRounds);
        tv_score = (TextView)findViewById(R.id.score);
        tv_userName = (TextView)findViewById(R.id.userName);

        tv_userName.setText(currUser);

        et_cap = (EditText) findViewById(R.id.et_cap);

        is = getResources().openRawResource(R.raw.states);

        csvReader = new CSVReader(is);

        stateList = csvReader.readData();

        populateScreen(mtv_questionText);


    }


    /***********getRandomNum()*********************************/
    public int getRandomNum(int min, int max)
    {
        //Generate random int between min-max

        int randomNum = rand.nextInt(max-min)+min;

        return randomNum;
    }


    /************populateScreen()******************************/
    public void populateScreen(View view)
    {

        String[] states = new String [50];

        int count = 0;

        for(String s[]:stateList)
        {
            states[count++] = s[0];
        }

        String aState = states[getRandomNum(0,states.length-1)];

        String question = dataBaseAdapter.getState_STATETABLE(aState);

        mtv_questionText.setText("What is the Capital of " + question);

        answer = dataBaseAdapter.getCaptial_STATETABLE(question);

        Message.message(this,answer);

    }

    /************submit()*************************************/

    public void submit(View view)
    {

        String userAnswer = et_cap.getText().toString().trim();

        if(numRounds != 5)
        {
            if (userAnswer.compareToIgnoreCase(answer) == 0)
            {
                score += 25;
                numRounds++;
                tv_numRounds.setText("Round " + numRounds + " out of 5");
                tv_score.setText("Score: " + score);
                populateScreen(view);
                Message.message(this, "You got it correct");
            }
            else
            {
                numRounds++;
                tv_numRounds.setText("Round " + numRounds + " out of 5");
                populateScreen(view);
            }
        }
        else
        {
            gotoScores(view);
        }

    }

    /************gotoScores()*********************************/
    public void gotoScores(View view)
    {
        Intent i = new Intent(getApplicationContext(),ScoresScreen.class);
        i.putExtra("userScore",score);
        i.putExtra("currUser",currUser);
        startActivity(i);
    }

    /************gotoSplash()*********************************/
    public void gotoSplash(View view)
    {
        Intent i = new Intent(getApplicationContext(),SplashScreen.class);
        startActivity(i);
    }


}