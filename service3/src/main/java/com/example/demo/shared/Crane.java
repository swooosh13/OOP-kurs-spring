package com.example.demo.shared;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Crane implements Callable<Object> {

  private volatile LocalDateTime currentTime;
  private int fine = 0;
  private int size = 0;

  public int getSize() {
    return size;
  }

  private ConcurrentLinkedQueue<Ship> ships;

  public Crane(ConcurrentLinkedQueue<Ship> ships) {
    this.ships = ships;
    currentTime = ZonedDateTime.of(2021, 5, 31, 0, 0, 0, 0, ZoneId.of("Europe/Moscow")).toLocalDateTime();
    size += ships.size();
  }

  @Override
  public Object call() throws InterruptedException {
    while (!ships.isEmpty()) {

      Ship currentShip = ships.poll();

      currentTime = Utils.getDateFromMillis(Math.max(Utils.getTimeInMillis(currentShip.getTimeOfArrival()), Utils.getTimeInMillis(currentTime)));
      LocalDateTime timeToUnload = currentTime.plusMinutes(currentShip.getWaitTime());

      currentShip.setUnloadTimeStart(timeToUnload);
      currentShip.setWaitTime((int) (Utils.getTimeInMillis(currentTime) - Utils.getTimeInMillis(currentShip.getTimeOfArrival())) / 1000 / 60);

      currentTime.plusMinutes((int) currentShip.getUnloadTime());

      Ship nextShip = ships.peek();

      if (nextShip == null) {
        break;
      }

      if (nextShip.getTimeOfArrival().isAfter(currentTime)) {
        this.fine += calculateFine((int) (Utils.getTimeInMillis(nextShip.getTimeOfArrival()) - Utils.getTimeInMillis(currentTime)) / 1000 / 60);
      }

      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public int calculateFine(int minutes) {
    if (minutes % 60 == 0) {
      return 100 * (minutes / 60); // penalty per hour * minutes / minutes of wait
    }

    return 100 * (minutes / 60 + 1);
  }

  public int getFine() {
    return fine;
  }

  public void setFine(int fine) {
    this.fine = fine;
  }

  public ConcurrentLinkedQueue<Ship> getShips() {
    return ships;
  }

  public void setShips(ConcurrentLinkedQueue<Ship> ships) {
    this.ships = ships;
  }
}