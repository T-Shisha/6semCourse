package com.clinic.dentist.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {
    public static Date getDateFromString(String dateInString){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // String dateInString = NextDay();
        Date date=new Date();
        try {

            date = formatter.parse(dateInString);


        } catch (ParseException e) {

        }

        return date;
    }
    public static String getDate2 (String dateInString){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // String dateInString = NextDay();
        Date date=new Date();
        try {

            date = formatter.parse(dateInString);


        } catch (ParseException e) {

        }
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");

        String dat = formatForDateNow.format(date);
        return dat;
    }
}
