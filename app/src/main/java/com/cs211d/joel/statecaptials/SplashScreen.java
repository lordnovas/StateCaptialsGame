package com.cs211d.joel.statecaptials;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.View;



public class SplashScreen extends ActionBarActivity
{
    DataBaseAdapter dataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        dataBaseAdapter = new DataBaseAdapter(this);

    }

    public void showDBData(View view)
    {
      //  dataBaseAdapter.deleteRow("Joel");
      //  dataBaseAdapter.insertUser_USERTABLE("katie");

        dataBaseAdapter.insertSTATEnCAP_STATESTABLE("New York","Albany");
        String statesData = dataBaseAdapter.getAllData_STATESTABLE();

        Message.message(this,dataBaseAdapter.getState_STATESTABLE(0));
    }
    public void gotoGame(View view)
    {
        Intent i = new Intent(getApplicationContext(),GameScreen.class);
        startActivity(i);
    }

    public void gotoScores(View view)
    {
        Intent i = new Intent(getApplicationContext(),ScoresScreen.class);
        startActivity(i);
    }


}
