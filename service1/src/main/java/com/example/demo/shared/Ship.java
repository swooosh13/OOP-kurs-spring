package com.example.demo.shared;

import java.time.*;
import java.util.Random;
import java.time.LocalDateTime;

public class Ship implements Comparable<Ship> {
  private String name;
  private Cargo cargo;
  private LocalDateTime arrival; // реальная дата стоянки
  private LocalDateTime actualArrival; // запланированный срок стоянки
  private int unloadTime;

  private int waitTime;
  private LocalDateTime unloadTimeStart;
  private int unloadDelay;

  public Ship() {
    this.name = "name";
    this.cargo = new Cargo();
  }

  public Ship(String name, Cargo cargo, LocalDateTime actualArrival, LocalDateTime scheduledArrival, int unloadTime, int unloadDelay) {
    this.name = name;
    this.cargo = cargo;
    this.arrival = actualArrival;
    this.actualArrival = scheduledArrival;
    this.unloadTime = unloadTime;
    this.unloadDelay = unloadDelay;
    this.unloadTimeStart = actualArrival;
    this.waitTime = 0;
  }

  public String toString() {
    return "Ship {"
      + "\n  name: '" + this.name + "\'"
      + "\n  real arrival: " + this.arrival
      + "\n  scheduled arrival: " + this.actualArrival
      + "\n  unload started ar: " + this.unloadTimeStart
      + "\n  cargo: " + this.cargo.toString()
      + "\n}";
  }

  public Cargo getCargo() {
    return this.cargo;
  }

  public typeCargo getCargoType() {
    return this.cargo.getType();
  }

  public LocalDateTime getTimeOfArrival() {
    return arrival;
  }

  public int getUnloadDelay() {
    return this.unloadDelay;
  }

  public int getWaitTime() {
    return this.waitTime;
  }

  public void setUnloadTimeStart(LocalDateTime unloadTimeStart) {
    this.unloadTimeStart = unloadTimeStart;
  }

  public void setWaitTime(int waitTime) {
    this.waitTime = waitTime;
  }

  public int getUnloadTime() {
    return this.unloadTime;
  }

  @Override
  public int compareTo(Ship o) {
    if (this.arrival.isAfter(o.getTimeOfArrival())) {
      return 1;
    } else if (this.arrival.isEqual((o.getTimeOfArrival()))) {
      return 0;
    } else {
      return -1;
    }
//    return this.arrival.compareTo(o.getTimeOfArrival());
  }

  public static void main(String[] args) {
    Random random = new Random();
    typeCargo type = typeCargo.values()[random.nextInt(typeCargo.values().length)];

  }
}
