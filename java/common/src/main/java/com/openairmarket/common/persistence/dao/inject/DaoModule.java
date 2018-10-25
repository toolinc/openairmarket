package com.openairmarket.common.persistence.dao.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.openairmarket.common.persistence.dao.ActiveDaoFactory;
import com.openairmarket.common.persistence.dao.CatalogDao;
import com.openairmarket.common.persistence.dao.CatalogDaoFactory;
import com.openairmarket.common.persistence.dao.DaoFactory;
import com.openairmarket.common.persistence.dao.tenant.TenantDao;
import com.openairmarket.common.persistence.model.tenant.Tenant;
import javax.inject.Provider;
import javax.persistence.EntityManager;

/** Defines the data access object injection points. */
final class DaoModule extends AbstractModule {

  @Override
  protected void configure() {
    requireBinding(Key.get(new TypeLiteral<Provider<EntityManager>>() {}));
    bind(DaoFactory.class).to(DaoFactoryImpl.class);
    bind(ActiveDaoFactory.class).to(ActiveDaoFactoryImpl.class);
    bind(CatalogDaoFactory.class).to(CatalogDaoFactoryImpl.class);
  }

  @Provides
  TenantDao providesTenantDao(CatalogDaoFactory catalogDaoFactory) {
    CatalogDao<Integer, Tenant> catalogDao = catalogDaoFactory.create(Tenant.class, Integer.class);
    return new TenantDaoImpl(catalogDao);
  }
}
