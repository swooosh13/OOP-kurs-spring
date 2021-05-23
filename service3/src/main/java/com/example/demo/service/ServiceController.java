package com.example.demo.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service3")
public class ServiceController {
  private ServiceIml service;

  @Autowired
  public void setService(ServiceIml service) {
    this.service = service;
  }

  @GetMapping("/results")
  public String getResults() throws InterruptedException {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(service.getResults());
    System.out.println(json);
    return service.getResults();
  }
}