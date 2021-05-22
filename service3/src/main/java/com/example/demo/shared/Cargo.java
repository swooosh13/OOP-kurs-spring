package com.example.demo.shared;

public class Cargo {
  private typeCargo type;
  private int total;

  public Cargo() {
    this.type = typeCargo.BULKER;
    this.total = 1000;
  }

  public Cargo(typeCargo type, int total) {
    this.type = type;
    this.total = total;
  }

  @Override
  public String toString() {
    return "Cargo {"
      + "\n\ttype: '" + this.type + "\'"
      + ",\n\ttotal: '" + this.total + "\'"
      + "\n  }";
  }

  public typeCargo getType() {
    return this.type;
  }

  public int getTotal() {
    return this.total;
  }
}
