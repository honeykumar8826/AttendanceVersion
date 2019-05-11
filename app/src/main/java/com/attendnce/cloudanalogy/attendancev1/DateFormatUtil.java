package com.attendnce.cloudanalogy.attendancev1;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatUtil {
    //salesforce to other date format
    public String DateChangeFormat1(String createdDate) throws ParseException {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sfdcDateTimeParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


                sfdcDateTimeParser.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        Date date = sfdcDateTimeParser.parse(createdDate);


        @SuppressLint("SimpleDateFormat") String formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(date);


        return formatter;


    }
    //another to desired format
    public String DateChangeFormat2(String createdDate) throws ParseException {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sfdcDateTimeParser = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date date = sfdcDateTimeParser.parse(createdDate);

        SimpleDateFormat formatter = new SimpleDateFormat("E-dd'th' MMM-hh:mm a");

        String strDate = formatter.format(date);
        return strDate;


    }
}
