package com.openairmarket.common.persistence.dao.inject;

import static com.google.common.truth.Truth.assertThat;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.Transactional;
import com.openairmarket.common.persistence.dao.DaoException;
import com.openairmarket.common.persistence.dao.tenant.TenantDao;
import com.openairmarket.common.persistence.inject.DdlGeneration;
import com.openairmarket.common.persistence.inject.PersistenceModule;
import com.openairmarket.common.persistence.listener.AuditListener;
import com.openairmarket.common.persistence.listener.ThreadLocalSystemUserHolder;
import com.openairmarket.common.persistence.model.security.SystemUser;
import com.openairmarket.common.persistence.model.tenant.Tenant;
import java.util.ResourceBundle;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test cases for the class {@link TenantDaoImpl}. */
public final class TenantDaoImplTest {

  private static final String RESOURCE = "DaoResourceBundle";
  private static final SystemUser SYSTEM_USER =
      SystemUser.newBuilder().setId(1L).setEmail("root@gmail.com").build();
  @Inject private static PersistService persistService;
  @Inject private static Provider<EntityManager> entityManager;
  @Inject private static Provider<TenantDao> tenantDao;
  @Inject private static Provider<TransactionalObject> transactionalObject;

  @BeforeAll
  public static void setUpTests() {
    Guice.createInjector(
        binder -> binder.bind(ResourceBundle.class).toInstance(ResourceBundle.getBundle(RESOURCE)),
        binder -> binder.requestStaticInjection(DaoException.Builder.class),
        PersistenceModule.builder()
            .setServerMode(true)
            .setDatabaseName("pos")
            .setDdlGeneration(DdlGeneration.CREATE_OR_EXTEND_TABLES)
            //.setDatabaseName(SystemUserDaoImplTest.class.getSimpleName())
            .build(),
        binder -> binder.bind(SystemUserDaoImplTest.TransactionalObject.class),
        binder -> binder.requestStaticInjection(AuditListener.class),
        binder -> binder.requestStaticInjection(TenantDaoImplTest.class),
        new DaoModule());
    persistService.start();
    transactionalObject.get().insertUser(SYSTEM_USER);
  }

  @AfterAll
  public static void tearDownTest() {
    persistService.stop();
  }

  @BeforeEach
  public void setUp() {
    ThreadLocalSystemUserHolder.registerTenancyContext(SYSTEM_USER);
  }

  @Test
  public void shouldPersist() {
    entityManager.get().getTransaction().begin();
    Tenant tenant = Tenant.newBuilder().setName("root").setReferenceId("1").build();
    tenantDao.get().persist(tenant);
    entityManager.get().getTransaction().commit();
  }

  @Test
  public void shouldNotPersistDuplicateReferenceId() {
    Tenant.Buider buider = Tenant.newBuilder().setName("tenant 2").setReferenceId("2");
    transactionalObject.get().insert(buider.build());
    entityManager.get().getTransaction().begin();
    DaoException daoException =
        Assertions.assertThrows(
            DaoException.class,
            () -> {
              tenantDao.get().persist(buider.setName("tenant 4").build());
            });
    assertThat(daoException.getErrorCode().code()).isEqualTo(150);
    entityManager.get().getTransaction().rollback();
  }

  @Test
  public void shouldNotPersistDuplicateName() {
    Tenant.Buider buider = Tenant.newBuilder().setName("tenant 3").setReferenceId("3");
    transactionalObject.get().insert(buider.build());
    entityManager.get().getTransaction().begin();
    DaoException daoException =
        Assertions.assertThrows(
            DaoException.class,
            () -> {
              tenantDao.get().persist(buider.setReferenceId("4").build());
            });
    assertThat(daoException.getErrorCode().code()).isEqualTo(160);
    entityManager.get().getTransaction().rollback();
  }

  static class TransactionalObject {

    @Inject private EntityManager entityManager;
    @Inject private TenantDao tenantDao;

    @Transactional
    public void insertUser(SystemUser systemUser) {
      entityManager.persist(systemUser);
    }

    @Transactional
    public void insert(Tenant tenant) {
      tenantDao.persist(tenant);
    }
  }
}
