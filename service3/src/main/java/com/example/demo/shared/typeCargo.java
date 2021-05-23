package com.example.demo.shared;

import java.util.Random;

public enum typeCargo {
  TANKER,
  BULKER,
  CONTAINER;

  public double getCarryingCofficient() {
    return switch (this) {
      case TANKER -> 5;
      case BULKER -> 5;
      case CONTAINER -> 2;
    };
  }

  public int getRandomTotal() {
    Random random = new Random();
    return switch (this) {
      case TANKER -> random.nextInt(100) + 1;
      case BULKER -> random.nextInt(100) + 1;
      case CONTAINER -> random.nextInt(20) + 1;
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
