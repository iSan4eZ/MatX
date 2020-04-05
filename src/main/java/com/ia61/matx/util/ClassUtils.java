package com.ia61.matx.util;

public class ClassUtils {

  public static Boolean isAssignableFrom(Class classThatImplements, Class whichClassToTestWith) {
    return whichClassToTestWith.isAssignableFrom(classThatImplements);
  }

}
