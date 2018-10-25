package com.openairmarket.common.persistence.dao.inject;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.dao.Dao;
import com.openairmarket.common.persistence.dao.DaoFactory;
import com.openairmarket.common.persistence.model.AbstractModel;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

/** Provides the implementation of the {@link DaoFactory}. */
final class DaoFactoryImpl implements DaoFactory {

  private final Provider<EntityManager> entityManagerProvider;

  @Inject
  public DaoFactoryImpl(Provider<EntityManager> entityManagerProvider) {
    this.entityManagerProvider = Preconditions.checkNotNull(entityManagerProvider);
  }

  @Override
  public <S extends Serializable, T extends AbstractModel<S>> Dao<S, T> create(
      Class<T> entityClass, Class<S> idClass) {
    return new DaoImpl<>(entityManagerProvider, entityClass, idClass);
  }
}
