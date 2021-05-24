package com.example.demo.shared;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utils {

  public static String intToDateFormat(int minutes) {
    return minutes / 1440 + ":" + minutes / 60 % 24 + ":" + minutes % 60;
  }

  public static long getTimeInMillis(LocalDateTime localDateTime) {
    ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("Europe/Moscow"));
    long millis = zdt.toInstant().toEpochMilli();
    return millis;
  }

  public static LocalDateTime getDateFromMillis(long millis) {
    LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("Europe/Moscow"));
    return localDateTime;
  }
}