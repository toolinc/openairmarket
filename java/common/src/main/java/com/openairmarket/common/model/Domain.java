package com.openairmarket.common.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.io.Serializable;

/** Marker inteface that specifies that an object is a model. */
public interface Domain extends Serializable {

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null, as
   * well as is not negative.
   *
   * @param <E> a Number
   * @param value an object reference
   * @return the reference that was validated
   */
  default <E extends Number> E checkPositive(E value) {
    Preconditions.checkNotNull(value);
    Preconditions.checkState(value.doubleValue() > 0.0);
    return value;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null, as
   * well as is not empty.
   *
   * @param value an object reference
   * @return the reference that was validated
   */
  default String checkNotEmpty(String value) {
    Preconditions.checkState(!Strings.isNullOrEmpty(value));
    return value.trim().toUpperCase();
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is nillable, as
   * well as is not empty.
   *
   * @param value an object reference
   * @return the reference that was validated
   */
  default String checkNillable(String value) {
    if (!Strings.isNullOrEmpty(value)) {
      return checkNotEmpty(value);
    }
    return value;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is nillable, as
   * well as is not negative.
   *
   * @param value an object reference
   * @return the reference that was validated
   */
  default <E extends Number> E checkNillablePositive(E value) {
    if (value != null) {
      return checkPositive(value);
    }
    return value;
  }
}
