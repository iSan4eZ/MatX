package com.ia61.matx.model.exception;

import com.ia61.matx.module.Module;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModuleException extends RuntimeException {

  private Module module;

  public ModuleException(String message, Module module) {
    super(message);
    this.module = module;
  }
}
