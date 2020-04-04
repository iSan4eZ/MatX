package com.ia61.matx.module;

import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.output.Output;
import com.ia61.matx.model.ui.PopupField;
import javafx.scene.control.Control;

import java.util.List;

public interface Module extends Input, Output {

  String getModuleName();

  List<PopupField<?>> getPopupFields();

//  List<Control> getControls();

}
