package com.example.poorva.messorganizer2;

import java.text.DateFormat;
import java.util.Calendar;

public class TodaysDate {

    public String getTodatsDate()
    {
        Calendar calendar=Calendar.getInstance();
        return DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
    }

}
