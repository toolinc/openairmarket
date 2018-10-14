package com.openairmarket.etl.model;

import com.google.auto.value.AutoValue;
import java.util.regex.Pattern;

/** Store data and a regex. */
@AutoValue
public abstract class DataPattern {

  public abstract String data();

  public abstract Pattern regex();

  public static DataPattern create(String data, Pattern regex) {
    return new AutoValue_DataPattern(data, regex);
  }
}
