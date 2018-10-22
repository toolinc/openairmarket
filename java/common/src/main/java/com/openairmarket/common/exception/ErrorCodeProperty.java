package com.openairmarket.common.exception;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.io.Serializable;
import java.util.ResourceBundle;

/** Stores and retrieves the properties for a specific error code. */
@AutoValue
public abstract class ErrorCodeProperty implements Serializable {

  private static final String CODE = ".code";
  private static final String NAME = ".name";
  private static final String DESCRIPTION = ".description";

  abstract String property();

  public Integer getCode(ResourceBundle bundle) {
    return Integer.valueOf(getProperty(bundle, property().concat(CODE)));
  }

  public String getName(ResourceBundle bundle) {
    return getProperty(bundle, property().concat(NAME));
  }

  public String getDescription(ResourceBundle bundle) {
    return getProperty(bundle, property().concat(DESCRIPTION));
  }

  public static ErrorCodeProperty create(String property) {
    Preconditions.checkState(!Strings.isNullOrEmpty(property), "Property is null or empty.");
    return new AutoValue_ErrorCodeProperty(property);
  }

  private static String getProperty(ResourceBundle bundle, String property) {
    return bundle.getString(property);
  }
}
