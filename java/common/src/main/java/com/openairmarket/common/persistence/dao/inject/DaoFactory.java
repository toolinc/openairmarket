package com.openairmarket.common.persistence.dao.inject;

import com.openairmarket.common.persistence.dao.ActiveDao;
import com.openairmarket.common.persistence.dao.CatalogDao;
import com.openairmarket.common.persistence.dao.Dao;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import com.openairmarket.common.persistence.model.AbstractCatalogModel;
import com.openairmarket.common.persistence.model.AbstractModel;
import java.io.Serializable;
import javax.inject.Provider;
import javax.persistence.EntityManager;

/** Helper class to create dao. */
public final class DaoFactory {

  public static final <S extends Serializable, T extends AbstractModel<S>> Dao<S, T> createDao(
      Provider<EntityManager> entityManagerProvider, Class<S> idClass, Class<T> entityClass) {
    return new DaoImpl<>(entityManagerProvider, entityClass, idClass);
  }

  public static final <S extends Serializable, T extends AbstractActiveModel<S>>
      ActiveDao<S, T> createActiveDao(
          Provider<EntityManager> entityManagerProvider,
          Dao<S, T> dao,
          Class<S> idClass,
          Class<T> entityClass) {
    return new ActiveDaoImpl<>(entityManagerProvider, dao, entityClass, idClass);
  }

  public static final <S extends Serializable, T extends AbstractCatalogModel<S>>
      CatalogDao<S, T> createCatalogDao(
          Provider<EntityManager> entityManagerProvider,
          ActiveDao<S, T> activeDao,
          Class<T> entityClass) {
    return new CatalogDaoImpl<>(entityManagerProvider, activeDao, entityClass);
  }
}
