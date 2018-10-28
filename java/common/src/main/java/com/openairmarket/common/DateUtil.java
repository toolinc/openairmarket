package com.openairmarket.common;

import java.time.LocalDateTime;
import java.time.ZoneId;

/** Provides utility methods for date and time. */
public class DateUtil {

  private static final ZoneId ZONE_ID = ZoneId.of("PST8PDT");

  public static LocalDateTime nowLocalDateTime() {
    return LocalDateTime.now(ZONE_ID);
  }
}
