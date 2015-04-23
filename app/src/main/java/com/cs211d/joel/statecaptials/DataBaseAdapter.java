package com.cs211d.joel.statecaptials;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/*
TODO - Add Methods for States Table, pretty much the same as USerScore Table
TODO - Add CSVReader class and fill methods
 */

public class DataBaseAdapter
{

    DataBaseHelper helper;

    public DataBaseAdapter(Context context)
    {
        helper = new DataBaseHelper(context);
    }


    /**********************Methods for TABLE_UserScore**************/

    public long insertUser_USERTABLE(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DataBaseHelper.NAME,name);

        long id = db.insert(helper.TABLE_USERSCORES,null,cv);

        return id;
    }

    public long insertScore_USERTABLE(String name,int score)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(helper.SCORE, score);
        String[] whereArgs={name};
        int count = db.update(helper.TABLE_USERSCORES, contentValues, helper.NAME + "=?", whereArgs);
        return count;
    }

    public String getAllData_USERTABLE()
    {

        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {helper.UID,helper.NAME,helper.SCORE};

        StringBuffer buffer = new StringBuffer();

        Cursor cursor = db.query(helper.TABLE_USERSCORES, columns, null, null, null, null, null);

        while(cursor.moveToNext())
        {
            int cid = cursor.getInt(0);
            String name  = cursor.getString(1);
            String score = cursor.getString(2);

            buffer.append(cid+" "+name+" "+score+"\n");
        }

        return buffer.toString();
    }

    public String getScore_USERTABLE(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {helper.SCORE};

        String[] selectionArgs ={name};

        StringBuffer buffer = new StringBuffer();

        Cursor cursor = db.query(helper.TABLE_USERSCORES, columns, helper.NAME +"=?",selectionArgs, null, null, null);

        while(cursor.moveToNext())
        {
            int index2  = cursor.getColumnIndex(helper.SCORE);
            int score = cursor.getInt(index2);

            buffer.append(score);
        }
        return buffer.toString();
    }

    public int deleteRow_USERTABLE(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs ={name};
        int count = db.delete(helper.TABLE_USERSCORES, helper.NAME + "=?", whereArgs);

        return count;
    }



    /**********************Methods for TABLE_STATES**************/

    public long insertSTATEnCAP_STATESTABLE(String state,String captial)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DataBaseHelper.STATENAME,state);
        cv.put(DataBaseHelper.STATECAPITAL,captial);

        long id = db.insert(helper.TABLE_STATES, null, cv);

        return id;
    }



    public String getAllData_STATESTABLE()
    {

        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {helper.UID,helper.STATENAME,helper.STATECAPITAL};

        StringBuffer buffer = new StringBuffer();

        Cursor cursor = db.query(helper.TABLE_STATES, columns, null, null, null, null, null);

        while(cursor.moveToNext())
        {
            int cid = cursor.getInt(0);
            String states  = cursor.getString(1);
            String capitals = cursor.getString(2);

            buffer.append(cid+" "+states+" "+capitals+"\n");
        }

        return buffer.toString();
    }


    public String getState_STATESTABLE(int uid)
{
    SQLiteDatabase db = helper.getWritableDatabase();

    String[] columns = {helper.STATENAME};

    String[] selectionArgs ={""+uid};

    StringBuffer buffer = new StringBuffer();

    Cursor cursor = db.query(helper.TABLE_STATES, columns, helper.UID +"=?",selectionArgs, null, null, null,null);

    while(cursor.moveToNext())
    {
        int index1  = cursor.getColumnIndex(helper.STATENAME);
        int stateName = cursor.getInt(index1);

        buffer.append(stateName);
    }
    return buffer.toString();
}

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
        private static final String CREATE_TABLE_USERSCORES = "CREATE TABLE " + TABLE_USERSCORES + " " +
                "("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME +" VARCHAR(255), " + SCORE + " INTEGER);";

        //Drop Statement for Table_USERSCORES
        private static final String DROP_TABLE_USERSCORES = "DROP TABLE IF EXISTS "+TABLE_USERSCORES;


        //Create Statement for TABLE_STATES
        private static final String CREATE_TABLE_STATES = "CREATE TABLE " + TABLE_STATES + " " +
                "("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STATENAME +" VARCHAR(255), " + STATECAPITAL + " VARCHAR(255));";

        //Drop Statement for TABLE_STATES
        private static final String DROP_TABLE_STATES = "DROP TABLE IF EXISTS "+TABLE_STATES;


        private Context context;

        public DataBaseHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            this.context=context;
            Message.message(context,"Constructor Called");

        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL(CREATE_TABLE_USERSCORES);
                db.execSQL(CREATE_TABLE_STATES);
                Message.message(context, "onCreate Called");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            try
            {
                db.execSQL(DROP_TABLE_USERSCORES);
                db.execSQL(DROP_TABLE_STATES);
                Message.message(context, "onUpgrade Called");
                onCreate(db);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
    }

}
