package com.openairmarket.common.persistence.dao.inject;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.dao.ActiveDao;
import com.openairmarket.common.persistence.dao.ActiveDaoFactory;
import com.openairmarket.common.persistence.dao.CatalogDao;
import com.openairmarket.common.persistence.dao.CatalogDaoFactory;
import com.openairmarket.common.persistence.model.AbstractCatalogModel;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

/** Provides the implementation of the {@link CatalogDaoFactory}. */
final class CatalogDaoFactoryImpl implements CatalogDaoFactory {

  private final Provider<EntityManager> entityManagerProvider;
  private final ActiveDaoFactory activeDaoFactory;

  @Inject
  public CatalogDaoFactoryImpl(
      Provider<EntityManager> entityManagerProvider, ActiveDaoFactory activeDaoFactory) {
    this.entityManagerProvider = Preconditions.checkNotNull(entityManagerProvider);
    this.activeDaoFactory = Preconditions.checkNotNull(activeDaoFactory);
  }

  @Override
  public <S extends Serializable, T extends AbstractCatalogModel<S>> CatalogDao<S, T> create(
      Class<T> entityClass, Class<S> idClass) {
    ActiveDao<S, T> activeDao = activeDaoFactory.create(entityClass, idClass);
    return new CatalogDaoImpl<>(entityManagerProvider, activeDao, entityClass);
  }
}
