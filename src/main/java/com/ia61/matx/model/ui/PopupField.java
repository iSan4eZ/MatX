package com.ia61.matx.model.ui;

import javafx.scene.control.*;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class PopupField<T> {

  private FieldType fieldType;
  private Supplier<T> valueGetter;
  private Consumer<T> valueSetter;
  private String title;
  private Control control;

  private static final UnaryOperator<TextFormatter.Change> integerFilter = c -> {
    String text = c.getControlNewText();
    if (text.matches("-?([1-9][0-9]*)?")) {
      return c;
    }
    return null;
  };

  private static final UnaryOperator<TextFormatter.Change> floatFilter = c -> {
    String text = c.getControlNewText();
    if (text.matches("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?")) {
      return c ;
    }
    return null ;
  };

  private static final UnaryOperator<TextFormatter.Change> binaryFilter = c -> {
    String text = c.getControlNewText();
    if (text.matches("^[0-1]{1,}$")) {
      return c ;
    }
    return null ;
  };


  public PopupField(FieldType fieldType, Supplier<T> valueGetter, Consumer<T> valueSetter, String title) {
    this.fieldType = fieldType;
    this.valueGetter = valueGetter;
    this.valueSetter = valueSetter;
    this.title = title;

    switch (fieldType) {
      case LABEL:
        control = new Label(getValue().toString());
        break;
      case GRAPH:
        //TODO create LineChart
        break;
      case INTEGER :
        control = new TextField();
        ((TextField) control).setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), (Integer) getValue(), integerFilter ));
        break;
      case FLOAT:
        control = new TextField();
        ((TextField) control).setTextFormatter(new TextFormatter<>(new FloatStringConverter(), (Float) getValue(), floatFilter ));
        break;
      case BINARY_STRING:
        control = new TextField(getValue().toString());
        ((TextField) control).setTextFormatter(new TextFormatter<>(binaryFilter));
        break;
      case BOOLEAN:
        control = new CheckBox();
        ((CheckBox) control).setSelected(Boolean.parseBoolean(getValue().toString()));
        break;
    }
  }

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

  public Control getControl() {
    return control;
  }

  public String getTitle() {
    return title;
  }
}
