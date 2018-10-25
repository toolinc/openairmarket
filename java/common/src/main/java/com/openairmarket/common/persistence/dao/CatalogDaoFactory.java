package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.persistence.model.AbstractCatalogModel;
import java.io.Serializable;

public interface CatalogDaoFactory {

  /**
   * Creates a new {@link CatalogDao} instance.
   *
   * @param entityClass specifies the {@link Class} of the entity.
   * @param idClass specifies the {@link Class} of the identifier.
   * @param <S> the {@link java.lang.reflect.Type} of the identifier.
   * @param <T> the {@link java.lang.reflect.Type} of the entity.
   * @return a new instance
   */
  <S extends Serializable, T extends AbstractCatalogModel<S>> CatalogDao<S, T> create(
      Class<T> entityClass, Class<S> idClass);
}
