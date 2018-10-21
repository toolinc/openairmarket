package com.openairmarket.common.domain.model;

import java.time.LocalDate;

/**
 * Specifies the behavior of the entities that need to keep the active between specific range of
 * dates.
 */
public interface EnablementModelRangeDate extends EnablementModel {

  /**
   * Retrieves the date in which an instance of an object will become active.
   *
   * @return the specified date
   */
  LocalDate getEffectiveStart();

  /**
   * Specifies the date in which an instance will be active.
   *
   * @param effectiveStart the date on which the instance will become active
   */
  void setEffectiveStart(LocalDate effectiveStart);

  /**
   * Retrieves the date in which an instance of an object will expire.
   *
   * @return the specified date
   */
  LocalDate getEffectiveEnd();

  /**
   * Specifies the date in which an instance will expire.
   *
   * @param effectiveEnd the date on which the instance will become expire
   */
  void setEffectiveEnd(LocalDate effectiveEnd);
}
