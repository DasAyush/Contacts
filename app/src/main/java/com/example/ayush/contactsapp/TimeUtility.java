package com.example.ayush.contactsapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtility {

    public static String getCurrentDateTime()
    {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("hh:mm a");
        return formatter.format(date);
    }

}
