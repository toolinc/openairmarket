package com.openairmarket.common.persistence.dao.inject;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.Transactional;
import com.openairmarket.common.persistence.dao.DaoException;
import com.openairmarket.common.persistence.dao.security.SystemUserDao;
import com.openairmarket.common.persistence.inject.DdlGeneration;
import com.openairmarket.common.persistence.inject.PersistenceModule;
import com.openairmarket.common.persistence.model.security.SystemUser;
import java.util.List;
import java.util.Optional;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Test cases for the class {@link SystemUserDaoImpl}. */
public final class SystemUserDaoImplTest {

  @Inject private static PersistService persistService;
  @Inject private static Provider<EntityManager> entityManager;
  @Inject private static Provider<SystemUserDao> systemUserDao;
  @Inject private static Provider<TransactionalObject> transactionalObject;

  @BeforeAll
  public static void setUpTests() {
    Guice.createInjector(
        PersistenceModule.builder()
            .setDdlGeneration(DdlGeneration.CREATE_OR_EXTEND_TABLES)
            .setDatabaseName(SystemUserDaoImplTest.class.getSimpleName())
            .build(),
        binder -> binder.bind(TransactionalObject.class),
        binder -> binder.requestStaticInjection(SystemUserDaoImplTest.class),
        new DaoModule());
    persistService.start();
  }

  @AfterAll
  public static void tearDownTest() {
    persistService.stop();
  }

  @Test
  public void shouldPersist() throws DaoException {
    entityManager.get().getTransaction().begin();
    SystemUser systemUser = new SystemUser();
    systemUser.setId(777L);
    systemUser.setActive(Boolean.TRUE);
    systemUser.setEmail("god@heaven.com");
    systemUserDao.get().persist(systemUser);
    entityManager.get().getTransaction().commit();
    // Asserting the system user
    SystemUser user = entityManager.get().find(SystemUser.class, 777L);
    assertThat(user).isEqualTo(systemUser);
    assertThat(user.getEmail()).isEqualTo(systemUser.getEmail());
    assertThat(user.getActive()).isEqualTo(systemUser.getActive());
    assertThat(user.getVersion()).isEqualTo(1);
  }

  @Test
  public void shouldMerge() throws DaoException {
    String email = "merger@gmail.com".toUpperCase();
    SystemUser systemUser = transactionalObject.get().insert();
    systemUser.setEmail(email);
    entityManager.get().getTransaction().begin();
    SystemUser userUpdated = systemUserDao.get().merge(systemUser);
    systemUserDao.get().flush();
    entityManager.get().getTransaction().commit();
    assertThat(userUpdated.getEmail()).isEqualTo(email);
  }

  @Test
  public void shouldRefresh() {
    SystemUser systemUser = transactionalObject.get().refresh();
    entityManager.get().getTransaction().begin();
    systemUserDao.get().refresh(systemUser);
    entityManager.get().getTransaction().commit();
    assertThat(systemUser.getId()).isEqualTo(444L);
    assertThat(systemUser.getEmail()).isEqualTo("user-444@gmail.com".toUpperCase());
    assertThat(systemUser.getActive()).isTrue();
    assertThat(systemUser.getVersion()).isEqualTo(1L);
  }

  @Test
  public void shouldRefreshWithLock() {
    SystemUser systemUser = transactionalObject.get().refreshWithLock();
    entityManager.get().getTransaction().begin();
    systemUserDao.get().refresh(systemUser, LockModeType.OPTIMISTIC);
    entityManager.get().getTransaction().commit();
    assertThat(systemUser.getId()).isEqualTo(888L);
    assertThat(systemUser.getEmail()).isEqualTo("user-888@gmail.com".toUpperCase());
    assertThat(systemUser.getActive()).isTrue();
    assertThat(systemUser.getVersion()).isEqualTo(1L);
  }

  @Test
  public void shouldRemove() throws DaoException {
    SystemUser systemUser = transactionalObject.get().remove();
    entityManager.get().getTransaction().begin();
    systemUserDao.get().remove(systemUser);
    entityManager.get().getTransaction().commit();
    assertThat(entityManager.get().find(SystemUser.class, systemUser.getId()).getActive())
        .isFalse();
  }

  @Test
  public void shouldFind() {
    SystemUser systemUser = transactionalObject.get().find();
    Optional<SystemUser> optionalUser = systemUserDao.get().find(666L);
    assertThat(optionalUser.isPresent()).isTrue();
    assertThat(optionalUser.get()).isEqualTo(systemUser);
    assertThat(optionalUser.get().getEmail()).isEqualTo(systemUser.getEmail());
    assertThat(optionalUser.get().getActive()).isEqualTo(systemUser.getActive());
    assertThat(optionalUser.get().getVersion()).isEqualTo(systemUser.getVersion());
  }

  @Test
  public void shouldFindWithVersion() throws DaoException {
    SystemUser systemUser = transactionalObject.get().findWithVersion();
    Optional<SystemUser> findUser = systemUserDao.get().find(999L, 1L);
    assertThat(findUser.isPresent()).isTrue();
    assertThat(findUser.get()).isEqualTo(systemUser);
    assertThat(findUser.get().getEmail()).isEqualTo(systemUser.getEmail());
    assertThat(findUser.get().getActive()).isEqualTo(systemUser.getActive());
    assertThat(findUser.get().getVersion()).isEqualTo(systemUser.getVersion());
  }

  @Test
  public void shouldFindRange() {
    transactionalObject.get().findRange();
    List<SystemUser> usersRange = systemUserDao.get().findRange(0, 5);
    assertThat(usersRange).hasSize(5);
  }

  @Test
  public void shouldHasVersionChangedNo() throws DaoException {
    SystemUser systemUser = transactionalObject.get().hasVersionChanged();
    assertThat(systemUserDao.get().hasVersionChanged(systemUser)).isFalse();
  }

  @Test
  public void shouldCount() {
    transactionalObject.get().insertCountActive();
    long counter = systemUserDao.get().count();
    assertThat(counter).isGreaterThan(0L);
  }

  @Test
  public void shouldCountInactive() {
    transactionalObject.get().insertCountInactive();
    long counter = systemUserDao.get().countInactive();
    assertThat(counter).isGreaterThan(0L);
  }

  static class TransactionalObject {

    @Inject private Provider<EntityManager> entityManager;

    @Transactional
    public SystemUser find() {
      SystemUser systemUser = new SystemUser();
      systemUser.setId(666L);
      systemUser.setEmail("user-666@gmail.com".toUpperCase());
      systemUser.setActive(Boolean.TRUE);
      entityManager.get().persist(systemUser);
      return systemUser;
    }

    @Transactional
    public SystemUser findWithVersion() {
      SystemUser systemUser = new SystemUser();
      systemUser.setId(999L);
      systemUser.setEmail("user-999@gmail.com".toUpperCase());
      systemUser.setActive(Boolean.TRUE);
      entityManager.get().persist(systemUser);
      return systemUser;
    }

    @Transactional
    public void findRange() {
      ImmutableList<String> users = ImmutableList.of("10", "20", "30", "40", "50");
      String email = "user-%s@gmail.com";
      for (String user : users) {
        SystemUser systemUser = new SystemUser();
        systemUser.setId(Long.valueOf(user));
        systemUser.setEmail(String.format(email, user));
        systemUser.setActive(Boolean.TRUE);
        entityManager.get().persist(systemUser);
      }
    }

    @Transactional
    public SystemUser hasVersionChanged() {
      SystemUser systemUser = new SystemUser();
      systemUser.setId(1000L);
      systemUser.setEmail("user-1000@gmail.com".toUpperCase());
      systemUser.setActive(Boolean.TRUE);
      entityManager.get().persist(systemUser);
      return systemUser;
    }

    @Transactional
    public SystemUser insert() {
      SystemUser systemUser = new SystemUser();
      systemUser.setId(222L);
      systemUser.setEmail("user-222@gmail.com".toUpperCase());
      systemUser.setActive(Boolean.TRUE);
      entityManager.get().persist(systemUser);
      return systemUser;
    }

    @Transactional
    public void insertCountActive() {
      SystemUser systemUser = new SystemUser();
      systemUser.setId(333L);
      systemUser.setEmail("user-333@gmail.com");
      systemUser.setActive(Boolean.TRUE);
      entityManager.get().persist(systemUser);
    }

    @Transactional
    public void insertCountInactive() {
      SystemUser systemUser = new SystemUser();
      systemUser.setId(111L);
      systemUser.setEmail("dare@devil.com");
      systemUser.setActive(Boolean.FALSE);
      entityManager.get().persist(systemUser);
    }

    @Transactional
    public SystemUser refresh() {
      SystemUser systemUser = new SystemUser();
      systemUser.setId(444L);
      systemUser.setEmail("user-444@gmail.com".toUpperCase());
      systemUser.setActive(Boolean.TRUE);
      entityManager.get().persist(systemUser);
      return systemUser;
    }

    @Transactional
    public SystemUser refreshWithLock() {
      SystemUser systemUser = new SystemUser();
      systemUser.setId(888L);
      systemUser.setEmail("user-888@gmail.com".toUpperCase());
      systemUser.setActive(Boolean.TRUE);
      entityManager.get().persist(systemUser);
      return systemUser;
    }

    @Transactional
    public SystemUser remove() {
      SystemUser systemUser = new SystemUser();
      systemUser.setId(555L);
      systemUser.setEmail("user-555@gmail.com".toUpperCase());
      systemUser.setActive(Boolean.TRUE);
      entityManager.get().persist(systemUser);
      return systemUser;
    }
  }
}
