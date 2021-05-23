package com.example.demo.service.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import com.example.demo.service.shared.*;

import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ServiceIml {
  private String service1_uri = "http://localhost:8081/service1";
  private String service3_uri = "http://localhost:8083/service3";
  public static String path = "./src/main/resources/results";

  public String getSchedule() throws ParseException {
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(service1_uri + "/getSchedule", String.class);

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    Type itemsType = new TypeToken<List<Ship>>() {}.getType();
    List<Ship> ships = gson.fromJson(result, itemsType);
    List<Ship> shipsFromConsole = addShipFromConsole();

    if (shipsFromConsole.size() != 0) {
      ships.addAll(shipsFromConsole);
    }

    String json = gson.toJson(ships);

    try (FileWriter fileWriter = new FileWriter(path + "/shipsArrival.json")) {
      fileWriter.write(json);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return json;
  }

  public static List<Ship> addShipFromConsole() throws ParseException, IllegalArgumentException {
    List<Ship> shipsFromConsole = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);
    System.out.println("Add ship from console? (y\\n)");
    String answer = scanner.nextLine();
    Random random = new Random();
    while(answer.toUpperCase().equals("Y"))
    {
      System.out.println("Ship name: (any)");
      String name = scanner.nextLine();
      System.out.println("Type of cargo: (Bulker, Container, Tanker):");
      typeCargo type = typeCargo.valueOf(scanner.nextLine().toUpperCase(Locale.ROOT));
      System.out.println("Type weight of the cargo: (0 - any)");
      int weight = Integer.parseInt(scanner.nextLine());
      System.out.println("Day of arrival: (0 - 30)");
      int day = Integer.parseInt(scanner.nextLine());
      System.out.println("Time of arrival: (hh:mm)" );
      String time = scanner.nextLine().trim();
      String[] dates = time.split(":");


      LocalDateTime scheduledTimeOfArrival = ZonedDateTime.of(2021, 6, day, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), 0, 0, ZoneId.of("Europe/Moscow")).toLocalDateTime();
      LocalDateTime actualTimeOfArrival = scheduledTimeOfArrival.plusDays(random.nextInt(14) - 7);

      if(scheduledTimeOfArrival.getMonth() != Month.JUNE || scheduledTimeOfArrival.getDayOfMonth() == 31){
        throw new IllegalArgumentException("Incorrect date. (client error");
      }

      if(actualTimeOfArrival.getMonth() != Month.JUNE){
        throw new IllegalArgumentException("Date of actual time of arrival is out of bounds. (server error, incorrectly generated)");
      }

      int timeOfUnload = Utils.getTimeOfUnload(weight, type.getCarryingCofficient(), random.nextInt(1440));
      int delayOfUnload = random.nextInt(1440);

      Cargo cargo = new Cargo(type, weight);
      Ship ship = new Ship(name, cargo, actualTimeOfArrival, scheduledTimeOfArrival, timeOfUnload + delayOfUnload, delayOfUnload);
      shipsFromConsole.add(ship);

      System.out.println("Ship was added successfully. (OK)");
      System.out.println("to introduce a new ship? \n(y\\n)");
      answer = scanner.nextLine();
    }

    return shipsFromConsole;
  }

  public String getScheduleByFilename(String filename) throws FileNotFoundException, ParseException {
    String filePath = path + "/" + filename + ".json";
    File file = new File(filePath);

    if (!file.exists()) {
      throw new FileNotFoundException("File not found");
    }
    try {
      return new String(Files.readAllBytes(file.toPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }


  public String getResults() {
    RestTemplate restTemplate = new RestTemplate();
    String data = restTemplate.getForObject(service3_uri + "/results", String.class);

    System.out.println(data);
    String filePath = path + "/results.json";

    try (FileWriter fileWriter = new FileWriter(filePath)) {
      fileWriter.write(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "ok";
  }
}
