package com.openairmarket.common.persistence.dao.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.openairmarket.common.persistence.dao.ActiveDao;
import com.openairmarket.common.persistence.dao.CatalogDao;
import com.openairmarket.common.persistence.dao.Dao;
import com.openairmarket.common.persistence.dao.tenant.TenantDao;
import com.openairmarket.common.persistence.model.tenant.Tenant;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public final class TenantDaoModule extends AbstractModule {

  @Override
  protected void configure() {
    requireBinding(Key.get(new TypeLiteral<Provider<EntityManager>>() {}));
  }

  @Provides
  TenantDao providesTenantDao(Provider<EntityManager> entityManagerProvider) {
    Dao<Integer, Tenant> dao = new DaoImpl<>(entityManagerProvider, Tenant.class, Integer.class);
    ActiveDao<Integer, Tenant> activeDao =
        new ActiveDaoImpl<>(entityManagerProvider, dao, Tenant.class, Integer.class);
    CatalogDao<Integer, Tenant> catalogDao =
        new CatalogDaoImpl<>(entityManagerProvider, activeDao, Tenant.class);
    return new TenantDaoImpl(catalogDao);
  }
}
