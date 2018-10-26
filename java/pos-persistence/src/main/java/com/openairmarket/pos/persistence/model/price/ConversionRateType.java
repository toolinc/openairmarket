package com.openairmarket.pos.persistence.model.price;

/** Defines the different types of conversion rate types. */
public enum ConversionRateType {

  /** Average Rates. */
  AVERAGE,

  /** Company Rate. */
  COMPANY,

  /** Fixed Currency Rate. */
  FIXED,

  /** Manual Currency Rate. */
  MANUAL,

  /** No Conversion Rate. */
  NONE,

  /** Period Conversion Type. */
  PERIOD_END,

  /** Spot Conversation Rate Type. */
  SPOT,

  /** User Rate Type. */
  USER
}
