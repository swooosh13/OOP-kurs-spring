package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("/service2")
public class ServiceController {
  private ServiceIml service;

  @Autowired
  public void setService(ServiceIml serviceTwo) {
    this.service = serviceTwo;
  }

  @GetMapping("/getSchedule")
  public String getSchedule() {
    return service.getSchedule();
  }

  @GetMapping("/getSchedule/{filename}")
  public String getSchedule(@PathVariable("filename") String filename) {
    try {
      String data = service.getScheduleByFilename(filename);
      if (data.equals("")) {
        return "empty file";
      }
      return data;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  // TODO не робит
  @PostMapping("/postResults")
  public String postResults(@RequestBody String str) {
    try {
      if (str.equals("\"result\"")) {
        return service.getResults();
      }
      FileWriter file = new FileWriter(service.path + "report.json");
      file.write("");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}