package com.openairmarket.common.persistence.dao.inject;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.openairmarket.common.persistence.dao.DaoException;
import com.openairmarket.common.persistence.dao.security.SystemUserDao;
import com.openairmarket.common.persistence.model.security.SystemUser;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SystemUserDaoImplTest {
  @Inject PersistService persistService;
  @Inject UnitOfWork unitOfWork;
  @Inject Provider<EntityManager> entityManagerProvider;
  @Inject private SystemUserDao systemUserDao;

  @Before
  public void setUp() {
    Injector injector =
        Guice.createInjector(new JpaPersistModule("OpenAirMarket_PU"), new DaoModule());
    injector.injectMembers(this);
    persistService.start();
  }

  @After
  public void tearDown() {
    persistService.stop();
  }

  @Test
  public void shouldStoreSystemUser() throws DaoException {
    unitOfWork.begin();
    EntityManager entityManager = entityManagerProvider.get();
    entityManager.getTransaction().begin();
    SystemUser systemUser = new SystemUser();
    systemUser.setId(777L);
    systemUser.setActive(Boolean.TRUE);
    systemUser.setEmail("god@heaven.com");
    systemUserDao.persist(systemUser);
    entityManager.getTransaction().commit();
    unitOfWork.end();
  }
}
