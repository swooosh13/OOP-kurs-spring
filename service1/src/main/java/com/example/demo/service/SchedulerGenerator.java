package com.example.demo.service;

import com.github.javafaker.Faker;
import com.example.demo.shared.Cargo;
import com.example.demo.shared.Ship;
import com.example.demo.shared.Utils;
import com.example.demo.shared.typeCargo;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class SchedulerGenerator {
  private List<Ship> schedule = new ArrayList<>();

  private int amount;

  public SchedulerGenerator(int amount) {
    Random random = new Random();
    this.amount = amount;
    for (int i = 0; i < this.amount; i++) {
      typeCargo type = typeCargo.values()[random.nextInt(typeCargo.values().length)];

      int weight = type.getRandomTotal();
      Faker faker = new Faker();
      String name = faker.name().firstName();

      LocalDateTime scheduledTimeOfArrival = ZonedDateTime.of(2021, 6, random.nextInt(29) + 1, random.nextInt(24), random.nextInt(60), 0, 0, ZoneId.of("Europe/Moscow")).toLocalDateTime();
      LocalDateTime actualTimeOfArrival = scheduledTimeOfArrival.plusDays(random.nextInt(14) - 7);

      if (actualTimeOfArrival.getMonth() != Month.JUNE) {
        continue;
      }

      int timeOfUnload = Utils.getTimeOfUnload(weight, type.getCarryingCofficient(), random.nextInt(1440));
      int delayOfUnload = random.nextInt(1440);
      Cargo cargo = new Cargo(type, weight);
      Ship ship = new Ship(name, cargo, actualTimeOfArrival, scheduledTimeOfArrival, timeOfUnload + delayOfUnload, delayOfUnload);
      schedule.add(ship);
    }
  }

  public List<Ship> getSchedule() {
    Collections.sort(schedule);
    return schedule;
  }
}
