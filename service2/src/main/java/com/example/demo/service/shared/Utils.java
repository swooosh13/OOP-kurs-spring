package com.example.demo.service.shared;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class Utils {

  public static int getTimeOfUnload(int weight, double coefficient, int rndm) {
    return (int) (weight / coefficient) + rndm;
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