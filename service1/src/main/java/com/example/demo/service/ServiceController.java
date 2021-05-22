package com.example.demo.service;

import com.example.demo.shared.Ship;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/service1")
public class ServiceController {
  private ServiceController serviceController;

  @Autowired
  public void setService(ServiceController service) {
    this.serviceController = service;
  }

  @GetMapping("/getSchedule")
  public String schedule() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    List<Ship> ships = new SchedulerGenerator(500).getSchedule();
    String json = gson.toJson(ships);
    return json;
  }
}
