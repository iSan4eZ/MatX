package com.ia61.matx.model.ui.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum NoiseType implements EnumWithName<NoiseType> {

  WHITE("Білий шум"),
  PINK("Рожевий шум"),
  BLUE("Синій шум"),
  GRAY("Сірий шум"),
  IMPULSE("Імпульсна завада"),
  RANDOM("Випадкова завада");

  private String name;

  NoiseType(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<NoiseType> getAllValues() {
    return Arrays.asList(values());
  }

  @Override
  public NoiseType getByName(String name) {
    return Objects.isNull(name)
        ? null
        : Arrays.stream(NoiseType.values())
            .filter(type -> name.equals(type.getName()))
            .findFirst()
            .orElse(null);
  }

  @Override
  public String toString() {
    return getName();
  }
}
