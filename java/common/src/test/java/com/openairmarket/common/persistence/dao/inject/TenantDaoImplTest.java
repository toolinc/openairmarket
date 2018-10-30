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
import java.util.Optional;
import java.util.ResourceBundle;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
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
            .setPoolMode(false)
            // .setServerMode(true)
            // .setDatabaseName("pos")
            .setDdlGeneration(DdlGeneration.CREATE_OR_EXTEND_TABLES)
            .setDatabaseName(TenantDaoImplTest.class.getSimpleName())
            .build(),
        binder -> binder.bind(TransactionalObject.class),
        binder -> binder.requestStaticInjection(TenantDaoImplTest.class),
        new DaoModule());
    persistService.start();
    transactionalObject.get().insertUser(SYSTEM_USER);
    persistService.stop();
  }

  @BeforeEach
  public void setUp() {
    ThreadLocalSystemUserHolder.registerTenancyContext(SYSTEM_USER);
    Guice.createInjector(
        binder -> binder.bind(ResourceBundle.class).toInstance(ResourceBundle.getBundle(RESOURCE)),
        binder -> binder.requestStaticInjection(DaoException.Builder.class),
        PersistenceModule.builder()
            .setPoolMode(false)
            // .setServerMode(true)
            // .setDatabaseName("pos")
            .setDdlGeneration(DdlGeneration.NONE)
            .setDatabaseName(TenantDaoImplTest.class.getSimpleName())
            .build(),
        binder -> binder.bind(TransactionalObject.class),
        binder -> binder.requestStaticInjection(TenantDaoImplTest.class),
        new DaoModule());
    persistService.start();
  }

  @AfterEach
  public void tearDown() {
    persistService.stop();
  }

  @Test
  public void shouldPersist() {
    entityManager.get().getTransaction().begin();
    Tenant tenant = Tenant.newBuilder().setName("root").setReferenceId("1").build();
    tenantDao.get().persist(tenant);
    entityManager.get().getTransaction().commit();
    assertTwoTenants(tenant, "1", 1);
  }

  @Test
  public void shouldNotPersistDuplicateReferenceId() {
    Tenant.Buider buider = Tenant.newBuilder().setName("tenant 2").setReferenceId("2");
    Tenant tenant = buider.build();
    transactionalObject.get().insert(tenant);
    DaoException daoException =
        Assertions.assertThrows(
            DaoException.class,
            () -> {
              entityManager.get().getTransaction().begin();
              tenantDao.get().persist(buider.setName("tenant 454").build());
              entityManager.get().getTransaction().rollback();
            });
    assertThat(daoException.getErrorCode().code()).isEqualTo(150);
    assertTwoTenants(tenant, "2", 1);
  }

  @Test
  public void shouldNotPersistDuplicateName() {
    Tenant.Buider buider = Tenant.newBuilder().setName("tenant 3").setReferenceId("3");
    Tenant tenant = buider.build();
    transactionalObject.get().insert(tenant);
    DaoException daoException =
        Assertions.assertThrows(
            DaoException.class,
            () -> {
              entityManager.get().getTransaction().begin();
              tenantDao.get().persist(buider.setReferenceId("4444").build());
              entityManager.get().getTransaction().rollback();
            });
    assertThat(daoException.getErrorCode().code()).isEqualTo(160);
    assertTwoTenants(tenant, "3", 1);
  }

  @Test
  public void shouldMerge() {
    Tenant tenantOld = Tenant.newBuilder().setName("tenant 4").setReferenceId("4").build();
    transactionalObject.get().insert(tenantOld);
    Optional<Tenant> optionalTenant = tenantDao.get().find("4");
    assertThat(optionalTenant.isPresent()).isTrue();
    tenantOld = optionalTenant.get();
    tenantOld.setName("tenant 2 changed");
    tenantOld = transactionalObject.get().merge(tenantOld);
    assertTwoTenants(tenantOld, "4", 2);
  }

  @Test
  public void shouldMergeNewInstance() {
    Tenant tenant = Tenant.newBuilder().setName("tenant 5").setReferenceId("5").build();
    entityManager.get().getTransaction().begin();
    tenant = tenantDao.get().merge(tenant);
    entityManager.get().getTransaction().commit();
    assertThat(tenant.getVersion()).isEqualTo(1);
    assertTwoTenants(tenant, "5", 1);
  }

  @Test
  public void shouldRemove() {
    Tenant tenant = Tenant.newBuilder().setName("tenant 6").setReferenceId("6").build();
    transactionalObject.get().insert(tenant);
    tenant = tenantDao.get().find("6").get();
    entityManager.get().getTransaction().begin();
    tenantDao.get().remove(tenant);
    entityManager.get().getTransaction().commit();
    assertThat(tenantDao.get().find("6").isPresent()).isFalse();
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

    @Transactional(rollbackOn = DaoException.class)
    public Tenant merge(Tenant tenant) {
      return tenantDao.get().merge(tenant);
    }
  }

  private static void assertTwoTenants(Tenant tenant, String referenceId, long version) {
    Tenant tenantStored = tenantDao.get().find(referenceId, version).get();
    assertThat(tenantStored.getId()).isNotNull();
    assertThat(tenantStored.getActive()).isTrue();
    assertThat(tenantStored.getReferenceId()).isEqualTo(tenant.getReferenceId());
    assertThat(tenantStored.getName()).ignoringCase().isEqualTo(tenant.getName());
  }
}
