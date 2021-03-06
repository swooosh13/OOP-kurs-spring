package com.example.demo.service;

import com.example.demo.shared.Ship;
import com.example.demo.shared.Unloading;
import com.example.demo.shared.typeCargo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ServiceIml {
  private String service2_uri = "http://localhost:8082/service2";

  public List<Ship> getShips() {
    RestTemplate restTemplate = new RestTemplate();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = restTemplate.getForObject(service2_uri + "/getSchedule/shipsArrival", String.class);

    Type listShipsType = new TypeToken<List<Ship>>() {}.getType();

    List<Ship> ships = gson.fromJson(json, listShipsType);
    if (ships instanceof List) {
      return ships;
    }
    return null;
  }

  public String getResults() throws InterruptedException {
    String result = "";
    List<Ship> ships = getShips();
//    System.out.println(ships);
    Collections.sort(ships);

    List<String> results = new ArrayList<>();

    List<Unloading> unloadings = new ArrayList<>();

    int i = 0;
    for (typeCargo type: typeCargo.values()) {
      List<Ship> list = new ArrayList<>();

      for(Ship ship: ships) {
        if (ship.getCargo().getType() == type) {
          list.add(ship);
        }
      }

      Unloading unloading = new Unloading(list);
      unloadings.add(unloading);
      unloading.start();

//      unloading.start();
//      unloading.join();
//      results.add(unloading.printInfo());
      i++;
    }

    for (int j = 0; j < unloadings.size(); j++) {
      try {
        unloadings.get(j).join();
      }
      catch (InterruptedException e) {
        // Если ошибка, то вывести сообщение
        System.out.println("Error: " + e.getMessage());
      }
    }

    for (int j = 0; j < unloadings.size(); j++) {
      results.add(unloadings.get(j).printInfo());
    }

    return results.toString();
  }
}
