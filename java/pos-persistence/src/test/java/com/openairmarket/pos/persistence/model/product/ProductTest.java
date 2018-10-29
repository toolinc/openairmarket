package com.openairmarket.pos.persistence.model.product;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.persist.PersistService;
import com.openairmarket.common.persistence.dao.inject.DaoModule;
import com.openairmarket.common.persistence.inject.DdlGeneration;
import com.openairmarket.common.persistence.inject.PersistenceModule;
import com.openairmarket.pos.persistence.model.business.Organization;
import java.util.UUID;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public final class ProductTest {

  @Inject private static PersistService persistService;
  @Inject private static Provider<EntityManager> entityManagerProvider;

  @BeforeAll
  public static void setUp() {
    Guice.createInjector(
        PersistenceModule.builder()
            .setDdlGeneration(DdlGeneration.CREATE_OR_EXTEND_TABLES)
            .setDatabaseName(ProductTest.class.getSimpleName())
            .build(),
        binder -> binder.requestStaticInjection(ProductTest.class),
        new DaoModule());
    persistService.start();
  }

  @AfterAll
  public static void tearDown() {
    persistService.stop();
  }

  @Test
  public void shouldStoreSystemUser() {
    entityManagerProvider.get().getTransaction().begin();
    Organization organization = new Organization();
    organization.setReferenceId(UUID.randomUUID().toString());
    organization.setName("Testing");
    entityManagerProvider.get().persist(organization);
    entityManagerProvider.get().getTransaction().commit();
  }
}
