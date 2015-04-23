package com.cs211d.joel.statecaptials;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by joel on 4/21/2015.
 */
public class Message
{
    public static void message(Context context,String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
