package com.openairmarket.common.persistence.dao.inject;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.dao.ActiveDao;
import com.openairmarket.common.persistence.dao.ActiveDaoFactory;
import com.openairmarket.common.persistence.dao.Dao;
import com.openairmarket.common.persistence.dao.DaoFactory;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

/** Provides the implementation of the {@link ActiveDaoFactory}. */
final class ActiveDaoFactoryImpl implements ActiveDaoFactory {

  private final Provider<EntityManager> entityManagerProvider;
  private final DaoFactory daoFactory;

  @Inject
  public ActiveDaoFactoryImpl(
      Provider<EntityManager> entityManagerProvider, DaoFactory daoFactory) {
    this.entityManagerProvider = Preconditions.checkNotNull(entityManagerProvider);
    this.daoFactory = Preconditions.checkNotNull(daoFactory);
  }

  @Override
  public <S extends Serializable, T extends AbstractActiveModel<S>> ActiveDao<S, T> create(
      Class<T> entityClass, Class<S> idClass) {
    Dao<S, T> dao = daoFactory.create(entityClass, idClass);
    return new ActiveDaoImpl<>(entityManagerProvider, dao, entityClass, idClass);
  }
}
