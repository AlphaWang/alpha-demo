package com.alphawang.jdk.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;

public class TimeZone {

    private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
      .appendValue(ChronoField.YEAR) //年
      .appendLiteral("/")
      .appendValue(ChronoField.MONTH_OF_YEAR) //月
      .appendLiteral("/")
      .appendValue(ChronoField.DAY_OF_MONTH) //日
      .appendLiteral(" ")
      .appendValue(ChronoField.HOUR_OF_DAY) //时
      .appendLiteral(":")
      .appendValue(ChronoField.MINUTE_OF_HOUR) //分
      .appendLiteral(":")
      .appendValue(ChronoField.SECOND_OF_MINUTE) //秒
      .appendLiteral(".")
      .appendValue(ChronoField.MILLI_OF_SECOND) //毫秒
      .appendLiteral(" - ")
      .appendZoneId()
//      .appendZoneText(TextStyle.FULL)
      .toFormatter();
    
    public static void main(String[] args) {
        
        String dateString = "2020-01-02 22:00:00";

        ZoneId zoneSH = ZoneId.of("Asia/Shanghai");
        ZoneId zoneNY = ZoneId.of("America/New_York");
        ZoneId zoneJST = ZoneOffset.ofHours(9);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneSH);
        ZonedDateTime zonedDateTime2 = localDateTime.atZone(zoneNY);
        
        // 可做调整
        zonedDateTime.plusDays(1);
        
        //zone: Asia/Shanghai 
        //date: 2020-01-02 22:00:00 +0800
        printDateWithZone(zonedDateTime, zoneSH);
        //zone: America/New_York 
        //date: 2020-01-02 09:00:00 -0500
        printDateWithZone(zonedDateTime, zoneNY);
        //zone: +09:00 
        //date: 2020-01-02 23:00:00 +0900
        printDateWithZone(zonedDateTime, zoneJST);
    }
    
    private static void printDateWithZone(ZonedDateTime zonedDateTime, ZoneId zone) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        System.out.println(String.format("\nzone: %s \ndate: %s", 
                                         zone, 
                                         outputFormatter.withZone(zone).format(zonedDateTime)));
        System.out.println(dateTimeFormatter.withZone(zone).format(zonedDateTime));
    }
    

}
