package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.persistence.model.AbstractActiveModel;
import java.io.Serializable;
import javax.inject.Singleton;

/** Factory to create instances of {@link ActiveDao}. */
@Singleton
public interface ActiveDaoFactory {

  /**
   * Creates a new {@link ActiveDao} instance.
   *
   * @param entityClass specifies the {@link Class} of the entity.
   * @param idClass specifies the {@link Class} of the identifier.
   * @param <S> the {@link java.lang.reflect.Type} of the identifier.
   * @param <T> the {@link java.lang.reflect.Type} of the entity.
   * @return a new instance
   */
  <S extends Serializable, T extends AbstractActiveModel<S>> ActiveDao<S, T> create(
      Class<T> entityClass, Class<S> idClass);
}
