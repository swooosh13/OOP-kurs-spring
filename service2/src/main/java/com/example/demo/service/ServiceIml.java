package com.example.demo.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.Result;
import core.ResultGenerator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
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

  public String getSchedule() {
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(service1_uri + "/getSchedule", String.class);

    try (FileWriter fileWriter = new FileWriter(path + "/arrivalOfShips.json")) {
      fileWriter.write(result);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }

  public String getScheduleByFilename(String filename) throws FileNotFoundException {
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

  private void responseResult(HttpServletResponse response, Result result) {
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Content-type", "application/json;charset=UTF-8");
    response.setStatus(200);
    Gson gsong = new GsonBuilder().setPrettyPrinting().create();
    try {
      response.getWriter().write(gsong.toJson(result));
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
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
    return data;
  }
}
