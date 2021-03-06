package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.exception.ErrorCodeProperty;
import javax.inject.Provider;

/** Defines the error codes for the {@code Dao}. */
public enum DaoErrorCode implements Provider<ErrorCodeProperty> {

  /**
   * Property that specifies the error message in case the entity has been modified recently by
   * another transaction.
   */
  OPRIMISTIC_LOCKING("dao.optimisticLocking"),
  /**
   * Property that specifies the error in case the unique has been violated for the reference id
   * field for an entity that is a catalog.
   */
  CATALOG_REFERENCE_ID_UK("dao.catalog.referenceId.UK"),
  /**
   * Property that specifies the error in case the unique has been violated for the name field for
   * an entity that is a catalog.
   */
  CATALOG_NAME_UK("dao.catalog.name.UK");

  private final ErrorCodeProperty errorCode;

  private DaoErrorCode(String property) {
    this.errorCode = ErrorCodeProperty.create(property);
  }

  @Override
  public ErrorCodeProperty get() {
    return errorCode;
  }
}
