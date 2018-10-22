package com.openairmarket.common;

import java.util.Date;

/** Provides utility methods for {@code Date}. */
public class DateUtil {

  /**
   * Create a defensive copy of {@code Date}.
   *
   * @param date the {@code Date} that will be cloned.
   * @return a new instance.
   */
  public static Date clone(Date date) {
    return new Date(date.getTime());
  }
}
