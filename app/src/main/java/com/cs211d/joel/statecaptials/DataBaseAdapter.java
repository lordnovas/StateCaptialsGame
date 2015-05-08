package com.cs211d.joel.statecaptials;

/*
  Author: Joel Rainey
  Date: 4/23
  Class: CS211D Spring 2015
  Android Project: State Capitals Trivia Game
  Filename: DataBaseAdapter.java
  Assignment Objective: Create State Capitals Trivia Game. Where the questions, user name and
  score are stored in an SQLite Database
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLOutput;


public class DataBaseAdapter
{

    DataBaseHelper helper;

    /************Constructor***************************/
    public DataBaseAdapter(Context context)
    {
        helper = new DataBaseHelper(context);
    }

    /***************Methods for TABLE_UserScore********/

    /***********insertUser*****************************/
    public long insertUser_USERTABLE(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.NAME,name);
        cv.put(DataBaseHelper.SCORE,0);
        return db.insert(DataBaseHelper.TABLE_USERSCORES, null, cv);
    }

    /************insertScore***************************/

    public long insertScore_USERTABLE(String name,int score)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.SCORE, score);
        String[] whereArgs={name};
        return db.update(DataBaseHelper.TABLE_USERSCORES,
                contentValues, DataBaseHelper.NAME + "=?", whereArgs);
    }


    /*************getAllData()*************************/
    public String getAllData_USERTABLE()
    {
        //Get all the users sorted by scores in desc order

        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {DataBaseHelper.UID,
                DataBaseHelper.NAME,
                DataBaseHelper.SCORE};

        StringBuffer buffer = new StringBuffer();

        Cursor cursor = db.query(DataBaseHelper.TABLE_USERSCORES,
                columns, null, null, null, null,
                DataBaseHelper.SCORE + " DESC ", "10");

        int rank = 0;

        while(cursor.moveToNext())
        {
            rank++;
            String name  = cursor.getString(1);
            String score = cursor.getString(2);

            buffer.append(rank+" "+name+" "+score+"\n");
        }

        /**Added Close Method **/
        helper.close();
        return buffer.toString();
    }

    /*************getScore()***************************/

    public String getScore_USERTABLE(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {DataBaseHelper.SCORE};
        String[] selectionArgs ={name};
        StringBuffer buffer = new StringBuffer();
        Cursor cursor = db.query(DataBaseHelper.TABLE_USERSCORES,
                columns, DataBaseHelper.NAME + "=?", selectionArgs,
                null, null, null);

        while(cursor.moveToNext())
        {
            int index2  = cursor.getColumnIndex(DataBaseHelper.SCORE);
            int score = cursor.getInt(index2);

            buffer.append(score);
        }
        return buffer.toString();
    }

    /*************deleteRow()*********************/
    public int deleteRow_USERTABLE(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {name};
        return db.delete(DataBaseHelper.TABLE_USERSCORES, DataBaseHelper.NAME + "=?", whereArgs);

    }

    /*************STATE TABLE Methods*************/

    /*************count()*************************/
    public int count()
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_STATES;

        Cursor cursor = db.rawQuery(countQuery, null);

        int i = cursor.getCount();

        /**Added Close Method **/
        helper.close();

        return i;

    }

    /*************insertState()*******************/
    public long insertState_STATESTABLE(String state,
                                        String capital)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.STATENAME,state);
        cv.put(DataBaseHelper.STATECAPITAL, capital);

        return db.insert(DataBaseHelper.TABLE_STATES, null, cv);
    }

    /*************getCapital(state Name)**********/

    public String getCapital(String stateName)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {DataBaseHelper.STATECAPITAL};
        String[] selectionArgs ={stateName};
        StringBuffer buffer = new StringBuffer();
        Cursor cursor = db.query(DataBaseHelper.TABLE_STATES, columns,
                DataBaseHelper.STATENAME + "=?", selectionArgs, null, null, null);

        while(cursor.moveToNext())
        {
            int index2  = cursor.getColumnIndex(DataBaseHelper.STATECAPITAL);
            String cap = cursor.getString(index2);
            buffer.append(cap);
        }
        helper.close();
        return buffer.toString();
    }

    /*************getState(Capital Name)***********/
    public String getState(String capitalName)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {DataBaseHelper.STATENAME};

        String[] selectionArgs ={capitalName};

        StringBuffer buffer = new StringBuffer();

        Cursor cursor = db.query(DataBaseHelper.TABLE_STATES, columns,
                DataBaseHelper.STATECAPITAL + "=?", selectionArgs, null, null, null);

        while(cursor.moveToNext())
        {
            int index1  = cursor.getColumnIndex(DataBaseHelper.STATENAME);
            String state = cursor.getString(index1);

            buffer.append(state);
        }

        helper.close();
        return buffer.toString();
    }


    /*************getState()***********************/
    public String getState()
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        String state = "default";

        String myQuery = "SELECT * FROM "+ DataBaseHelper.TABLE_STATES + " ORDER BY RANDOM() LIMIT 1 ";

        Cursor cursor = db.rawQuery(myQuery, null);

        if(cursor.moveToFirst())
        {
            state = cursor.getString(1);
        }

        helper.close();
        return state;
    }

    /*************getCapital()*********************/
    public String getCapital()
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        String state = "default";

        String myQuery = "SELECT * FROM "+ DataBaseHelper.TABLE_STATES
                + " ORDER BY RANDOM() LIMIT 1 ";

        Cursor cursor = db.rawQuery(myQuery, null);

        if(cursor.moveToFirst())
        {
            state = cursor.getString(2);
        }

        helper.close();
        return state;
    }




    /*************deleteAllRows()*****************/

    public void deleteAllRows()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete(DataBaseHelper.TABLE_USERSCORES,
                null, null);
    }

    /*************DataBaseHelper Class************/

    static class DataBaseHelper extends SQLiteOpenHelper
    {
        //Name of the Database
        private static final String DATABASE_NAME = "NameThatCapital.db";

        //Database Version Number
        private static final  int DATABASE_VERSION=1;

        //Table Names
        private static final String TABLE_STATES ="States";
        private static final String TABLE_USERSCORES ="UserScores";

        //Common column names
        private static final String UID = "_id";

        //Table_UserScores columns
        private static final String NAME = "Name";
        private static final String SCORE = "Score";

        //Table_States columns
        private static final String STATENAME = "stateName";
        private static final String STATECAPITAL = "stateCapital";


        //Create Statement for TABLE_USERSCORES
        private static final String CREATE_TABLE_USERSCORES = "CREATE TABLE "
                + TABLE_USERSCORES + " " +
                "("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME +" VARCHAR(255), " + SCORE + " INTEGER);";

        //Drop Statement for Table_USERSCORES
        private static final String DROP_TABLE_USERSCORES
                = "DROP TABLE IF EXISTS "+TABLE_USERSCORES;


        //Create Statement for TABLE_STATES
        private static final String CREATE_TABLE_STATES
                = "CREATE TABLE "
                + TABLE_STATES + " "
                + "("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STATENAME +" VARCHAR(255), "
                + STATECAPITAL + " VARCHAR(255));";

        //Drop Statement for TABLE_STATES
        private static final String DROP_TABLE_STATES
                = "DROP TABLE IF EXISTS "
                +TABLE_STATES;


        private Context context;

        public DataBaseHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL(CREATE_TABLE_USERSCORES);
                db.execSQL(CREATE_TABLE_STATES);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion)
        {
            try
            {
                db.execSQL(DROP_TABLE_USERSCORES);
                db.execSQL(DROP_TABLE_STATES);
                onCreate(db);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
    }

}
