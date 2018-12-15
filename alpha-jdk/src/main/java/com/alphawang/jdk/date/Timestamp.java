package com.alphawang.jdk.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class Timestamp {

    public static void main(String[] args) throws ParseException {
        Date date = new Date();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        printTime("2018-11-23 19:00:08");
        printTime("2018-11-23 20:10:00");

        printTime("2018-11-27 09:00:00");   // 1543244400 
        printTime("2018-11-27 09:10:00");   // 1543277400
    }
    
    private static void printTime(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        df.setTimeZone(TimeZone.getTimeZone("KST"));
        df.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")));
        
        Date d1 = df.parse(date);
        
        System.out.println(d1 + " -> " + d1.getTime());
    }
}
