package com.cs211d.joel.statecaptials;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class GameScreen extends ActionBarActivity
{
    /**
     * Note we will need to track the current_user through the app so when the user press play
     * I will bundle up an intent with the userName that I saved before it was added to db and pass it
     * to the GameScreen
     *
     * sample code from stack
     *
     * String easyPuzzle  = "630208010200050089109060030";

     Intent i = new Intent(this, ToClass.class);
     i.putExtra("epuzzle", easyPuzzle);
     startActivity(i);

     I put this sample code into my  onCreate of the GameScreen class

     Intent intent = getIntent();
     String easyPuzzle = intent.getExtras().getString("epuzzle");
     */
    String curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }


    public void populateScreen()
    {

        //Randomly pick a state name from ArrayList with States Names

        //Search Database and get the same StateName

        //Put the State name on the Screen with "What is the Capital of " + stateName

        //Search Datatbase for the StateName's Capital and save it to a string called answer

        //Next Randomly pick 2 Capitals from the ArrayList with  the Capital names and...

        //store them in the incorrect1 and incorrect2

        //Search DataBase for incorrect1 and incorrect2, retrieve them and store them in....

        //the Strings called incorrect1 and incorrect2
        //Check num of rounds if(numRounds < 2) bt_1.setText(answer), bt_2.setText(incorrect1)
        //, bt_3(incorrect2)
        //Check num of rounds if(numRounds > 2) bt_1.setText(incorrect1), bt_2.setText(incorrect1)
        // bt_3(answer)
        //Check num of rounds else if(numRounds < 2) bt_1.setText(incorrect2), bt_2.setText(answer)
        // bt_3(incorrect1)
        //else bt_1.setText(incorrect1), bt_2.setText(incorrect1),  bt_3(answer)

        //Screen is now populated after the above lines of code

    }


    public void userPicks(View view)
    {
        //Locate the switch statement code that checked to see what button was pressed
            // I think it was in radioButton code check fruits project at any rate it will be a switch statement

        /**
         * Lets check to see what button the user pressed
         * If the button's text is equal to the answer then do the following
         *   Search the Database for the users score by using the userName -
         *                  side note how about we plop the user name on top of screen with score
         *   add x amount of points to the mUserScore
         *
         *   Check if numRounds == 5
         *        Check if mUserScore is > than userScore from the Database
         *
         *             ...and if it is greater than,  update the userScore in the dataBase
         *
         *                then gotoViewHighScores where we will show curUser Score!
         *   else
         *        repopulate screen by calling populateScreen()
         *        numRounds++
         * else if we got down to this statement then the user pressed the wrong button so we do the following
         *     Check if numRounds == 5
         *        if the numRounds are == 5 then gotoViewHighScores where we will show curUser Score
         *          else
         *         repopulate screen by calling populateScreen()
         *              numRounds++
         */


    }


}