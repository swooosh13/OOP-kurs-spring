package com.example.demo.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Unloading extends Thread {
  private int countCranes = 0;
  private int fine = 0;

  private static int amountOfShips = 0;
  private int sizeOfQueue = 0;

  private List<Ship> ships;
  private ConcurrentLinkedQueue<Ship> queueOfShips;
  private List<Crane> cranes;

  public Unloading(List<Ship> ships) {
    this.ships = ships;
    amountOfShips = ships.size();
  }

  @Override
  public void run() {
    while (fine >= 30000 * countCranes) { // priceOfCrane * count of Cranes

      queueOfShips = new ConcurrentLinkedQueue<>(ships);

      fine = 0;
      sizeOfQueue = 0;

      countCranes++;
      cranes = new ArrayList<>(countCranes);

      ExecutorService executor = Executors.newFixedThreadPool(countCranes);

      for (int i = 0; i < countCranes; i++) {
        Crane crane = new Crane(queueOfShips);
        cranes.add(crane);
      }

      try {
        executor.invokeAll(cranes);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      executor.shutdown();

      cranes.stream().forEach(crane -> {
        fine += crane.getFine();
      });
    }
  }

  public int getCranesQuantity() {
    return countCranes;
  }

  public int getFine() {
    return fine;
  }

  public String getShipsToString() {
    String str = "";
    for (int i =0; i < ships.size(); i++) {
      str= str+ ships.get(0) + "\n";
    }
    return str;
  }

  public String printInfo() {
//  String allShips = getShipsToString();

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    HashMap<String, Object> map = new HashMap<>();
    map.put("Cargo Type", ships.get(0).getCargoType().toString());
    map.put("Fine", fine);
    map.put("cranesCount", countCranes);
    map.put("shipsCount", amountOfShips);
    map.put("averageTimeOfWait",  Utils.intToDateFormat(ships.stream().mapToInt(a -> a.getWaitTime()).sum() / amountOfShips));
    map.put("averageTimeOfDelayOfUnload",  Utils.intToDateFormat(ships.stream().mapToInt(a -> a.getUnloadDelay()).sum() / amountOfShips));

    String result = gson.toJson(map);

    return result;
  }
}