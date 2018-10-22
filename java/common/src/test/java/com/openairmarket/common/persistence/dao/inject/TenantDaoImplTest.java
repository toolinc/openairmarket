package com.openairmarket.common.persistence.dao.inject;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.openairmarket.common.persistence.dao.DaoException;
import com.openairmarket.common.persistence.dao.tenant.TenantDao;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class TenantDaoImplTest {

  @Inject private TenantDao tenantDao;

  // @Before
  public void setUp() {
    Injector injector =
        Guice.createInjector(new JpaPersistModule("OpenAirMarket_PU"), new TenantDaoModule());
    injector.injectMembers(this);
  }

  @Test
  public void test() throws DaoException {
    EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("OpenAirMarket_PU");
    entityManagerFactory.createEntityManager();

    // Tenant tenant = Tenant.newBuilder().setName("k").setReferenceId(1).build();
    // tenantDao.persist(tenant);
  }
}
