package com.openairmarket.common.domain.model;

import java.io.Serializable;

/**
 * Specifies the contract of the builder pattern for a domain object (entity).
 *
 * @param <T> The entity which the builder will create a new instance.
 */
public interface DomainObjectBuilder<T extends DomainObject> extends Serializable {

  /**
   * Creates a instances of a given DomainObject.
   *
   * @return a new instance.
   */
  T build();
}
