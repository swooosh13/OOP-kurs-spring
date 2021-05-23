package com.example.demo.service.main;

import com.example.demo.service.main.ServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.text.ParseException;

@RestController
@RequestMapping("/service2")
public class ServiceController {
  private ServiceIml service;

  @Autowired
  public void setService(ServiceIml serviceTwo) {
    this.service = serviceTwo;
  }

  @GetMapping("/getSchedule")
  public String getSchedule() throws ParseException {
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
    } catch (FileNotFoundException | ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  @PostMapping("/postResults")
  public String postResults() {
    return service.getResults();
  }
}