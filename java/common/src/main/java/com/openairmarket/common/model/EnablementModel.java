package com.openairmarket.common.domain.model;

/** Specifies the behavior of whether or not a domain object is active. */
public interface EnablementModel extends DomainModel {

  /**
   * Retrieves the enable status of an instance.
   *
   * @return the status
   */
  boolean isEnable();

  /**
   * Specifies the enable status.
   *
   * @param enable the status
   */
  void setEnable(boolean enable);
}
