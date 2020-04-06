package com.ia61.matx.model.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

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
        control = new Label(getValue() != null ? getValue().toString() : "");
        break;
      case INTEGER :
        control = new TextField();
        ((TextField) control).setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), (Integer) getValue(), integerFilter ));
        break;
      case FLOAT:
        control = new TextField();
        ((TextField) control).setTextFormatter(new TextFormatter<>(new FloatStringConverter(), (Float) getValue(), floatFilter ));
        break;
      case LONG:
        control = new TextField();
        ((TextField) control).setTextFormatter(new TextFormatter<>(new LongStringConverter(), (Long) getValue(), integerFilter ));
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

  private LineChart<?,?> createLineChart() {
     final List<SortedMap<Long, Float>> source = (List<SortedMap<Long, Float>>) getValue();
     NumberAxis xAxis = new NumberAxis();
     NumberAxis yAxis = new NumberAxis();
     LineChart<?, ?> lineChart = new LineChart<>(xAxis, yAxis);
     lineChart.setMinWidth(640f);

     source.forEach(coordinatesMap -> {
         XYChart.Series series = new XYChart.Series();
         series.getData().addAll(coordinatesMap.entrySet().stream()
                 .map(entry -> new XYChart.Data(entry.getKey(), entry.getValue()))
                 .collect(Collectors.toList()));
         lineChart.getData().add(series);
     });

     return lineChart;
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

  public HBox getHBox() {
    HBox hBox;
    if (fieldType == FieldType.GRAPH) {
      hBox = new HBox(new Label(title), createLineChart());
    } else {
      // Configure label
      Label label = new Label(title);
      label.setFont(new Font("Arial", 15));
      label.setWrapText(true);
      label.setPadding(new Insets(0, 10, 0, 10));

      hBox = new HBox(label, control);
    }
    hBox.setAlignment(Pos.CENTER_LEFT);
    return hBox;
  }
}
