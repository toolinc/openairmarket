package com.openairmarket.common.persistence.dao.inject;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.dao.ActiveDao;
import com.openairmarket.common.persistence.dao.DaoException;
import com.openairmarket.common.persistence.dao.security.SystemUserDao;
import com.openairmarket.common.persistence.model.security.SystemUser;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.LockModeType;

/** Data Access Object for {@code SystemUser}. */
final class SystemUserDaoImpl implements SystemUserDao {

  private final ActiveDao<Long, SystemUser> activeDao;

  @Inject
  public SystemUserDaoImpl(ActiveDao<Long, SystemUser> activeDao) {
    this.activeDao = Preconditions.checkNotNull(activeDao, "ActiveDao is missing.");
  }

  @Override
  public long countInactive() {
    return activeDao.countInactive();
  }

  @Override
  public void persist(SystemUser systemUser) throws DaoException {
    activeDao.persist(systemUser);
  }

  @Override
  public SystemUser merge(SystemUser systemUser) throws DaoException {
    return activeDao.merge(systemUser);
  }

  @Override
  public void remove(SystemUser systemUser) throws DaoException {
    activeDao.remove(systemUser);
  }

  @Override
  public void refresh(SystemUser systemUser) {
    activeDao.refresh(systemUser);
  }

  @Override
  public void refresh(SystemUser systemUser, LockModeType modeType) {
    activeDao.refresh(systemUser, modeType);
  }

  @Override
  public Optional<SystemUser> find(Long id) {
    return activeDao.find(id);
  }

  @Override
  public SystemUser find(Long id, long version) throws DaoException {
    return activeDao.find(id, version);
  }

  @Override
  public List<SystemUser> findRange(int start, int count) {
    return activeDao.findRange(start, count);
  }

  @Override
  public long count() {
    return activeDao.count();
  }

  @Override
  public void flush() {
    activeDao.flush();
  }

  @Override
  public boolean hasVersionChanged(SystemUser systemUser) throws DaoException {
    return activeDao.hasVersionChanged(systemUser);
  }
}
