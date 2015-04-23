package com.cs211d.joel.statecaptials;

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

        is = getResources().openRawResource(R.raw.states);

        csvReader = new CSVReader(is);

        stateList = csvReader.readData();

        //Fill States Table with States and Capitals
        for(String intoDb[]:stateList)
        {
            dataBaseAdapter.insertState_STATESTABLE(intoDb[0],intoDb[1]);
        }

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
