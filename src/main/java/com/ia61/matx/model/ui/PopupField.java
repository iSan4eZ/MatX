package com.ia61.matx.model.ui;

import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@AllArgsConstructor
public class PopupField<T> {

  private FieldType fieldType;
  private Supplier<T> valueGetter;
  private Consumer<T> valueSetter;
  private String title;

  public void setValue(Object value) {
    if (Objects.nonNull(valueSetter)) {
      valueSetter.accept((T) value);
    }
  }

  public Object getValue() {
    return Objects.isNull(valueGetter) ? null : valueGetter.get();
  }

  public FieldType getFieldType() {
    return fieldType;
  }

  public String getTitle() {
    return title;
  }
}
