package com.cs211d.joel.statecaptials;
/*
  Author: Joel Rainey
  Date: 4/23
  Class: CS211D Spring 2015
  Android Project: State Capitals Trivia Game
  Filename: Message.java
  Assignment Objective: Create State Capitals Trivia Game. Where the questions, user name and
  score are stored in an SQLite Database
*/
import android.content.Context;
import android.widget.Toast;



public class Message
{
    public static void message(Context context,String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
