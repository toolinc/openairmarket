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

/** Test cases for the class {@link TenantDaoImpl}. that should not persist a duplicate name. */
public final class TenantDaoPersistDuplicateNameTest {

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
            .setPoolMode(false)
            // .setServerMode(true)
            // .setDatabaseName("pos")
            .setDdlGeneration(DdlGeneration.CREATE_OR_EXTEND_TABLES)
            .setDatabaseName(TenantDaoPersistDuplicateNameTest.class.getSimpleName())
            .build(),
        binder -> binder.bind(TransactionalObject.class),
        binder -> binder.requestStaticInjection(TenantDaoPersistDuplicateNameTest.class),
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
  public void shouldNotPersistDuplicateName() {
    Tenant.Buider buider = Tenant.newBuilder().setName("tenant 3").setReferenceId("3");
    transactionalObject.get().insert(buider.build());
    DaoException daoException =
        Assertions.assertThrows(
            DaoException.class,
            () -> {
              entityManager.get().getTransaction().begin();
              tenantDao.get().persist(buider.setReferenceId("4444").build());
              entityManager.get().getTransaction().rollback();
            });
    assertThat(daoException.getErrorCode().code()).isEqualTo(160);
  }

  static class TransactionalObject {

    @Inject private Provider<EntityManager> entityManager;
    @Inject private Provider<TenantDao> tenantDao;

    @Transactional
    public void insertUser(SystemUser systemUser) {
      entityManager.get().persist(systemUser);
    }

    @Transactional
    public void insert(Tenant tenant) {
      tenantDao.get().persist(tenant);
    }
  }
}
