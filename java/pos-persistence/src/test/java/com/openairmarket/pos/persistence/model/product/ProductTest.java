package com.openairmarket.pos.persistence.model.product;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.openairmarket.pos.persistence.model.business.Organization;
import java.util.UUID;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class ProductTest {

  @Inject PersistService persistService;
  @Inject UnitOfWork unitOfWork;
  @Inject Provider<EntityManager> entityManagerProvider;

  @Before
  public void setUp() {
    Injector injector = Guice.createInjector(new JpaPersistModule("OpenAirMarket_PU"));
    injector.injectMembers(this);
    persistService.start();
  }

  @After
  public void tearDown() {
    persistService.stop();
  }

  @Test
  public void shouldStoreSystemUser() {
    unitOfWork.begin();
    entityManagerProvider.get().getTransaction().begin();
    Organization organization = new Organization();
    organization.setReferenceId(UUID.randomUUID().toString());
    organization.setName("Testing");
    entityManagerProvider.get().persist(organization);
    entityManagerProvider.get().getTransaction().commit();
    unitOfWork.end();
  }
}
