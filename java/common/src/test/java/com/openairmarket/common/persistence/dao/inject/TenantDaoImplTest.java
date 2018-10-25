package com.openairmarket.common.persistence.dao.inject;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.openairmarket.common.persistence.dao.DaoException;
import com.openairmarket.common.persistence.dao.security.SystemUserDao;
import com.openairmarket.common.persistence.dao.tenant.TenantDao;
import com.openairmarket.common.persistence.listener.AuditListener;
import com.openairmarket.common.persistence.listener.ThreadLocalSystemUserHolder;
import com.openairmarket.common.persistence.model.security.SystemUser;
import com.openairmarket.common.persistence.model.tenant.Tenant;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class TenantDaoImplTest {

  @Inject PersistService persistService;
  @Inject UnitOfWork unitOfWork;
  @Inject Provider<EntityManager> entityManagerProvider;
  @Inject private SystemUserDao systemUserDao;
  @Inject private TenantDao tenantDao;

  @Before
  public void setUp() throws DaoException {
    Injector injector =
        Guice.createInjector(
            new JpaPersistModule("OpenAirMarket_PU"),
            binder -> binder.requestStaticInjection(AuditListener.class),
            new DaoModule());
    injector.injectMembers(this);
    persistService.start();
    UnitOfWork unitOfWork = injector.getInstance(UnitOfWork.class);
    unitOfWork.begin();
    EntityManager entityManager = entityManagerProvider.get();
    SystemUser systemUser = new SystemUser();
    systemUser.setId(1L);
    systemUser.setEmail("root@openairmarket.com");
    systemUser.setActive(Boolean.TRUE);
    entityManager.getTransaction().begin();
    systemUserDao.persist(systemUser);
    entityManager.getTransaction().commit();
    unitOfWork.end();
    ThreadLocalSystemUserHolder.registerTenancyContext(systemUser);
    injector.injectMembers(this);
  }

  @After
  public void tearDown() {
    persistService.stop();
  }

  @Test
  public void test() throws DaoException {
    unitOfWork.begin();
    EntityManager em = entityManagerProvider.get();
    em.getTransaction().begin();
    Tenant tenant = Tenant.newBuilder().setName("k").setReferenceId("2").build();
    tenantDao.persist(tenant);
    em.getTransaction().commit();
    unitOfWork.end();
  }
}
