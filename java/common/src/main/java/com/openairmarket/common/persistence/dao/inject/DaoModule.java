package com.openairmarket.common.persistence.dao.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.openairmarket.common.persistence.dao.ActiveDao;
import com.openairmarket.common.persistence.dao.ActiveDaoFactory;
import com.openairmarket.common.persistence.dao.CatalogDao;
import com.openairmarket.common.persistence.dao.CatalogDaoFactory;
import com.openairmarket.common.persistence.dao.DaoFactory;
import com.openairmarket.common.persistence.dao.security.SystemUserDao;
import com.openairmarket.common.persistence.dao.tenant.TenantDao;
import com.openairmarket.common.persistence.model.security.SystemUser;
import com.openairmarket.common.persistence.model.tenant.Tenant;
import javax.inject.Provider;
import javax.persistence.EntityManager;

/** Defines the data access object injection points. */
public final class DaoModule extends AbstractModule {

  @Override
  protected void configure() {
    requireBinding(Key.get(new TypeLiteral<Provider<EntityManager>>() {}));
    bind(DaoFactory.class).to(DaoFactoryImpl.class);
    bind(ActiveDaoFactory.class).to(ActiveDaoFactoryImpl.class);
    bind(CatalogDaoFactory.class).to(CatalogDaoFactoryImpl.class);
  }

  @Provides
  SystemUserDao providesSystemUserDao(ActiveDaoFactory activeDaoFactory) {
    ActiveDao<Long, SystemUser> activeDao = activeDaoFactory.create(SystemUser.class, Long.class);
    return new SystemUserDaoImpl(activeDao);
  }

  @Provides
  TenantDao providesTenantDao(CatalogDaoFactory catalogDaoFactory) {
    CatalogDao<Integer, Tenant> catalogDao = catalogDaoFactory.create(Tenant.class, Integer.class);
    return new TenantDaoImpl(catalogDao);
  }
}
