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
import android.widget.Button;
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
    TextView mtv_questionText, tv_numRounds,tv_score,tv_userName,mtv_questionText_2;
    EditText et_cap;
    String answer ="";
    String question ="";
    Boolean isState = false;
    Button bt_yes, bt_no, bt_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent i = getIntent();
        currUser = i.getExtras().getString("currUser");

        dataBaseAdapter = new DataBaseAdapter(this);

        mtv_questionText = (TextView)findViewById(R.id.tv_question_1);
        mtv_questionText_2 = (TextView)findViewById(R.id.tv_question_2);
        tv_numRounds = (TextView)findViewById(R.id.numRounds);
        tv_score = (TextView)findViewById(R.id.score);
        tv_userName = (TextView)findViewById(R.id.userName);
        bt_no = (Button) findViewById(R.id.bt_no);
        bt_yes = (Button)findViewById(R.id.bt_yes);
        bt_submit = (Button)findViewById(R.id.bt_submit);
        et_cap = (EditText) findViewById(R.id.et_cap);

        bt_submit.setVisibility(View.INVISIBLE);
        et_cap.setVisibility(View.INVISIBLE);

        tv_userName.setText(currUser);

        //Populate Database if first app install
        is = getResources().openRawResource(R.raw.states);
        csvReader = new CSVReader(is);
        stateList = csvReader.readData();
        firstQuestion(mtv_questionText);

    }


    /***********getRandomNum()*********************************/
    public int getRandomNum(int min, int max)
    {
        //Generate random int between min-max
        int randomNum = rand.nextInt(max-min)+min;

        return randomNum;
    }


    /************firstQuestion()******************************/
    public void firstQuestion(View view)
    {
        int choice = getRandomNum(0, 7);

        if(choice >= 3)
        {
            question = dataBaseAdapter.getState();
            answer = dataBaseAdapter.getCapital(question);
            isState = true;
        }
        else
        {
            question = dataBaseAdapter.getCapital();
            answer = dataBaseAdapter.getState(question);
            isState = false;
        }
        mtv_questionText.setText("Is "+question+" a US State?");
    }

    /************secondQuestion()****************/

    public void secondQuestion(View v)
    {
        //Ask different question based on isState
        if(isState)
        {
            mtv_questionText_2.setText("What is the Capital of "+question);
        }
        else
        {
            mtv_questionText_2.setText("What State is "+question+" in?");
        }
        mtv_questionText_2.setVisibility(View.VISIBLE);
    }

    /************submit()************************/

    public void submit(View v)
    {
        String userAnswer = et_cap.getText().toString().trim();

        if(numRounds != 5)
        {
            if (userAnswer.compareToIgnoreCase(answer) == 0)
            {
                //Give points end round
                score += 10;
                tv_score.setText("Score: " + score);
                bt_submit.setVisibility(View.INVISIBLE);
                et_cap.setVisibility(View.INVISIBLE);
                mtv_questionText_2.setVisibility(View.INVISIBLE);

                //Start New Round
                numRounds++;
                tv_numRounds.setText("Round " + numRounds + " out of 5");
                firstQuestion(v);
                bt_no.setVisibility(View.VISIBLE);
                bt_yes.setVisibility(View.VISIBLE);
                Message.message(this,"You Are Correct!");
            }
            else
            {
                //End round
                Message.message(this,"You Are Incorrect!");
                bt_submit.setVisibility(View.INVISIBLE);
                et_cap.setVisibility(View.INVISIBLE);
                mtv_questionText_2.setVisibility(View.INVISIBLE);

                //Start new round
                numRounds++;
                tv_numRounds.setText("Round " + numRounds + " out of 5");
                firstQuestion(v);
                bt_no.setVisibility(View.VISIBLE);
                bt_yes.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            gotoScores(v);
        }
    }

    /************gotoScores()*******************/
    public void gotoScores(View view)
    {
        Intent i = new Intent(getApplicationContext(),ScoresScreen.class);
        i.putExtra("userScore",score);
        i.putExtra("currUser",currUser);
        startActivity(i);
    }

    /************gotoSplash()******************/
    public void gotoSplash(View view)
    {
        Intent i = new Intent(getApplicationContext(),SplashScreen.class);
        startActivity(i);
    }

    /**********yesOrNo()***********************/

    public void yesOrNo(View v)
    {
        if(numRounds != 5)
        {
            switch (v.getId())
            {
            case R.id.bt_yes:
            {
                if(isState)
                {
                    //Give points end round
                    score += 10;
                    tv_score.setText("Score: " + score);
                    bt_no.setVisibility(View.INVISIBLE);
                    bt_yes.setVisibility(View.INVISIBLE);
                    Message.message(this, "You are Correct");

                    //Start second part of round
                    bt_submit.setVisibility(View.VISIBLE);
                    et_cap.setText("");
                    et_cap.setVisibility(View.VISIBLE);
                    secondQuestion(v);
                }
                else
                {
                    //Start new round
                    numRounds++;
                    tv_numRounds.setText("Round " + numRounds + " out of 5");
                    firstQuestion(v);
                }
                break;
            }
            case R.id.bt_no:
            {
                //user is correct for question 1
                if(!isState)
                {
                    //Give points end round
                    score += 10;
                    tv_score.setText("Score: " + score);
                    bt_no.setVisibility(View.INVISIBLE);
                    bt_yes.setVisibility(View.INVISIBLE);
                    Message.message(this, "You are Correct! \n  It's a Capital!");

                    //Start round
                    bt_submit.setVisibility(View.VISIBLE);
                    et_cap.setText("");
                    et_cap.setVisibility(View.VISIBLE);
                    secondQuestion(v);
                }
                else
                {
                    //Start new round
                    numRounds++;
                    tv_numRounds.setText("Round " + numRounds + " out of 5");
                    firstQuestion(v);
                }
                break;
            }

            }
        }else
        {
            gotoScores(v);
        }
    }
}