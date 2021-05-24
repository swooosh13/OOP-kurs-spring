package com.example.demo.service.shared;

import java.util.Random;

public enum typeCargo {
  TANKER,
  BULKER,
  CONTAINER;

  public double getCarryingCofficient() {
    return switch (this) {
      case TANKER -> 500;
      case BULKER -> 500;
      case CONTAINER -> 200;
    };
  }

  public int getRandomTotal() {
    Random random = new Random();
    return switch (this) {
      case TANKER -> random.nextInt(10000) + 1;
      case BULKER -> random.nextInt(10000) + 1;
      case CONTAINER -> random.nextInt(5000) + 1;
    };
  }

  @Override
  public String toString() {
    return switch (this) {
      case TANKER -> "Tanker";
      case BULKER -> "Bulker";
      case CONTAINER -> "Container";
    };
  }
}
