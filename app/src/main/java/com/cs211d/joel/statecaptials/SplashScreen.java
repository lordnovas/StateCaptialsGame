package com.cs211d.joel.statecaptials;

/*
  Author: Joel Rainey
  Date: 4/23
  Class: CS211D Spring 2015
  Android Project: State Capitals Trivia Game
  Filename: SplashScreen.java
  Assignment Objective: Create State Capitals Trivia Game. Where the questions, user name and
  score are stored in an SQLite Database
*/

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

import java.io.InputStream;
import java.util.ArrayList;


public class SplashScreen extends ActionBarActivity
{
    EditText et_userName;
    String userName;
    DataBaseAdapter dataBaseAdapter;
    InputStream is;
    CSVReader csvReader;
    ArrayList<String[]> stateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        et_userName = (EditText) findViewById(R.id.et_userName);

        userName = et_userName.getText().toString();

        dataBaseAdapter = new DataBaseAdapter(this);

        filldb();
    }

    public void filldb()
    {
        is = getResources().openRawResource(R.raw.states);
        csvReader = new CSVReader(is);
        stateList = csvReader.readData();

        if(dataBaseAdapter.count() ==0)
        {
            //Fill States Table with States and Capitals
            for (String intoDb[] : stateList)
            {
                dataBaseAdapter.insertState_STATESTABLE(intoDb[0], intoDb[1]);
            }
        }
        Message.message(this,""+dataBaseAdapter.count());
    }



    /************gotoGame()***************************/
    public void gotoGame(View view)
    {
        Intent i = new Intent(getApplicationContext(),GameScreen.class);
        userName = et_userName.getText().toString();
        dataBaseAdapter.insertUser_USERTABLE(userName);
        i.putExtra("currUser", userName);
        startActivity(i);
    }


    /************gotoScores()***************************/
    public void gotoScores(View view)
    {
        Intent i = new Intent(getApplicationContext(),ScoresScreen.class);
        userName = et_userName.getText().toString();
        i.putExtra("userScore",0);
        i.putExtra("currUser",userName);
        startActivity(i);
    }




}
