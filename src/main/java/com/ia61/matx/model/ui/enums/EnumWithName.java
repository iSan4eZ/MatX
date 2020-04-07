package com.ia61.matx.model.ui.enums;

import java.util.List;

public interface EnumWithName<ENUM> {

  String getName();

  List<ENUM> getAllValues();

  ENUM getByName(String name);

}
