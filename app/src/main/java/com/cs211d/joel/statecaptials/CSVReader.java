package com.cs211d.joel.statecaptials;

/*
  Author: Joel Rainey
  Date: 4/23
  Class: CS211D Spring 2015
  Android Project: State Capitals Trivia Game
  Filename: CSVReader.java
  Assignment Objective: Create State Capitals Trivia Game. Where the questions, user name and
  score are stored in an SQLite Database
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class CSVReader
{

    InputStream inputStream;

    /**********Constructor******************************/
    public CSVReader(InputStream is)
    {
        this.inputStream = is;
    }


    /******************readData()***********************/
    public ArrayList<String[]> readData()
    {
        ArrayList<String[]> states = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                states.add(line.split(","));
            }
        }
        catch (IOException e)
        {
            throw  new RuntimeException("Error in reading CSV file: " + e);
        }
        finally
        {
            try
            {
                inputStream.close();

            }catch (IOException e)
            {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        return states;
    }

}
