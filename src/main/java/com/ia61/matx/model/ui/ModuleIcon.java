package com.ia61.matx.model.ui;

public enum UnitName {
  Unit1("U1"),
  Unit2("U2"),
  Unit3("U3");

  private String name;

  UnitName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
