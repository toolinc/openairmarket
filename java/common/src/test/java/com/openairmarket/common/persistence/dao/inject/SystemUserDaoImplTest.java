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
import java.util.ResourceBundle;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Test cases for the class {@link SystemUserDaoImpl}. */
public final class SystemUserDaoImplTest {

  private static final String RESOURCE = "DaoResourceBundle";
  @Inject private static PersistService persistService;
  @Inject private static Provider<EntityManager> entityManager;
  @Inject private static Provider<SystemUserDao> systemUserDao;
  @Inject private static Provider<TransactionalObject> transactionalObject;

  @BeforeAll
  public static void setUpTests() {
    Guice.createInjector(
        binder -> binder.bind(ResourceBundle.class).toInstance(ResourceBundle.getBundle(RESOURCE)),
        binder -> binder.requestStaticInjection(DaoException.Builder.class),
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
  public void shouldPersist() {
    SystemUser systemUser = transactionalObject.get().persist();
    // Asserting the system user
    SystemUser user = entityManager.get().find(SystemUser.class, 777L);
    assertThat(user).isEqualTo(systemUser);
    assertThat(user.getEmail()).isEqualTo(systemUser.getEmail());
    assertThat(user.getActive()).isEqualTo(systemUser.getActive());
    assertThat(user.getVersion()).isEqualTo(1);
  }

  @Test
  public void shouldMerge() {
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
  public void shouldNotMergeOptimisticLocking() {
    SystemUser.Builder builder =
        SystemUser.newBuilder().setId(1234L).setEmail("user-1234@hotmail.com");
    SystemUser oldUser = builder.build();
    SystemUser recentUser = transactionalObject.get().merge(builder.build());
    oldUser.setVersion(recentUser.getVersion());
    recentUser.setEmail("usuario-1234@gmail.com");
    recentUser = transactionalObject.get().update(recentUser);
    assertThat(recentUser.getVersion()).isEqualTo(2);

    entityManager.get().getTransaction().begin();
    oldUser.setEmail("ewewew@jdsds.com");
    DaoException daoException =
        Assertions.assertThrows(
            DaoException.class,
            () -> {
              systemUserDao.get().merge(oldUser);
            });
    entityManager.get().getTransaction().rollback();
    assertThat(daoException.getErrorCode().code()).isEqualTo(110);
  }

  @Test
  public void shouldRefresh() {
    SystemUser systemUser = transactionalObject.get().refresh();
    systemUserDao.get().refresh(systemUser);
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
  public void shouldRemove() {
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
  public void shouldNotFindIt() {
    Optional<SystemUser> optionalUser = systemUserDao.get().find(5678L);
    assertThat(optionalUser.isPresent()).isFalse();
  }

  @Test
  public void shouldNotFindSinceIsInactive() {
    transactionalObject.get().findInactive();
    Optional<SystemUser> optionalUser = systemUserDao.get().find(1001L);
    assertThat(optionalUser.isPresent()).isFalse();
  }

  @Test
  public void shouldFindWithVersion() {
    SystemUser systemUser = transactionalObject.get().findWithVersion();
    Optional<SystemUser> findUser = systemUserDao.get().find(999L, 1L);
    assertThat(findUser.isPresent()).isTrue();
    assertThat(findUser.get()).isEqualTo(systemUser);
    assertThat(findUser.get().getEmail()).isEqualTo(systemUser.getEmail());
    assertThat(findUser.get().getActive()).isEqualTo(systemUser.getActive());
    assertThat(findUser.get().getVersion()).isEqualTo(systemUser.getVersion());
  }

  @Test
  public void shouldNotFindWithVersion() {
    Optional<SystemUser> optionalUser = systemUserDao.get().find(86756L, 1L);
    assertThat(optionalUser.isPresent()).isFalse();
  }

  @Test
  public void shouldNotFindWithVersionSinceIsInactive() {
    transactionalObject.get().findWithVersionInactive();
    Optional<SystemUser> optionalUser = systemUserDao.get().find(1002L);
    assertThat(optionalUser.isPresent()).isFalse();
  }

  @Test
  public void shouldFindRange() {
    transactionalObject.get().findRange();
    List<SystemUser> usersRange = systemUserDao.get().findRange(0, 5);
    assertThat(usersRange).hasSize(5);
  }

  @Test
  public void shouldHasVersionChangedYes() {
    SystemUser.Builder builder =
        SystemUser.newBuilder().setId(4567L).setEmail("user-4567@gmail.com");
    SystemUser oldUser = builder.build();
    SystemUser recentUser = transactionalObject.get().merge(builder.build());
    oldUser.setVersion(recentUser.getVersion());
    recentUser.setEmail("user-4567@hotmail.com");
    recentUser = transactionalObject.get().update(recentUser);
    assertThat(recentUser.getVersion()).isEqualTo(2);

    assertThat(systemUserDao.get().hasVersionChanged(oldUser)).isTrue();
  }

  @Test
  public void shouldHasVersionChangedNo() {
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

    @Inject private EntityManager entityManager;
    @Inject private SystemUserDao systemUserDao;

    @Transactional
    public SystemUser persist() {
      SystemUser systemUser =
          SystemUser.newBuilder().setId(777L).setEmail("god@heaven.com").build();
      systemUserDao.persist(systemUser);
      return systemUser;
    }

    @Transactional
    public SystemUser merge(SystemUser systemUser) {
      return entityManager.merge(systemUser);
    }

    @Transactional
    public SystemUser update(SystemUser systemUser) {
      return entityManager.merge(systemUser);
    }

    @Transactional
    public SystemUser find() {
      SystemUser systemUser =
          SystemUser.newBuilder().setId(666L).setEmail("user-666@gmail.com".toUpperCase()).build();
      entityManager.persist(systemUser);
      return systemUser;
    }

    @Transactional
    public void findInactive() {
      SystemUser systemUser =
          SystemUser.newBuilder()
              .setActive(false)
              .setId(1001L)
              .setEmail("user-1001@gmail.com")
              .build();
      entityManager.persist(systemUser);
    }

    @Transactional
    public SystemUser findWithVersion() {
      SystemUser systemUser =
          SystemUser.newBuilder().setId(999L).setEmail("user-999@gmail.com".toUpperCase()).build();
      entityManager.persist(systemUser);
      return systemUser;
    }

    @Transactional
    public void findWithVersionInactive() {
      SystemUser systemUser =
          SystemUser.newBuilder()
              .setActive(false)
              .setId(1002L)
              .setEmail("user-1002@gmail.com")
              .build();
      entityManager.persist(systemUser);
    }

    @Transactional
    public void findRange() {
      ImmutableList<String> users = ImmutableList.of("10", "20", "30", "40", "50");
      String email = "user-%s@gmail.com";
      for (String user : users) {
        SystemUser systemUser =
            SystemUser.newBuilder()
                .setId(Long.valueOf(user))
                .setEmail(String.format(email, user))
                .build();
        entityManager.persist(systemUser);
      }
    }

    @Transactional
    public SystemUser hasVersionChanged() {
      SystemUser systemUser =
          SystemUser.newBuilder()
              .setId(1000L)
              .setEmail("user-1000@gmail.com".toUpperCase())
              .build();
      entityManager.persist(systemUser);
      return systemUser;
    }

    @Transactional
    public SystemUser insert() {
      SystemUser systemUser =
          SystemUser.newBuilder().setId(222L).setEmail("user-222@gmail.com".toUpperCase()).build();
      entityManager.persist(systemUser);
      return systemUser;
    }

    @Transactional
    public void insertCountActive() {
      SystemUser systemUser =
          SystemUser.newBuilder().setId(333L).setEmail("user-333@gmail.com").build();
      entityManager.persist(systemUser);
    }

    @Transactional
    public void insertCountInactive() {
      SystemUser systemUser =
          SystemUser.newBuilder()
              .setActive(Boolean.FALSE)
              .setId(111L)
              .setEmail("dare@devil.com")
              .build();
      entityManager.persist(systemUser);
    }

    @Transactional
    public SystemUser refresh() {
      SystemUser systemUser =
          SystemUser.newBuilder().setId(444L).setEmail("user-444@gmail.com".toUpperCase()).build();
      entityManager.persist(systemUser);
      return systemUser;
    }

    @Transactional
    public SystemUser refreshWithLock() {
      SystemUser systemUser =
          SystemUser.newBuilder().setId(888L).setEmail("user-888@gmail.com".toUpperCase()).build();
      entityManager.persist(systemUser);
      return systemUser;
    }

    @Transactional
    public SystemUser remove() {
      SystemUser systemUser =
          SystemUser.newBuilder().setId(555L).setEmail("user-555@gmail.com".toUpperCase()).build();
      entityManager.persist(systemUser);
      return systemUser;
    }
  }
}
