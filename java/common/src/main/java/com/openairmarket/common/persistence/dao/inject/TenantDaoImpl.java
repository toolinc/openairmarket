package com.openairmarket.common.persistence.dao.inject;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.dao.CatalogDao;
import com.openairmarket.common.persistence.dao.tenant.TenantDao;
import com.openairmarket.common.persistence.model.tenant.Tenant;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.LockModeType;

/** Data Access Object for {@code Tenant}. */
final class TenantDaoImpl implements TenantDao {

  private final CatalogDao<Integer, Tenant> catalogDao;

  @Inject
  public TenantDaoImpl(CatalogDao<Integer, Tenant> catalogDao) {
    this.catalogDao = Preconditions.checkNotNull(catalogDao);
  }

  @Override
  public void persist(Tenant entity) {
    catalogDao.persist(entity);
  }

  @Override
  public Tenant merge(Tenant entity) {
    return catalogDao.merge(entity);
  }

  // TODO (edgarrico: needs to throw a DAOException)
  @Override
  public void remove(Tenant entity) {
    catalogDao.remove(entity);
  }

  @Override
  public void refresh(Tenant entity) {
    catalogDao.refresh(entity);
  }

  @Override
  public void refresh(Tenant entity, LockModeType modeType) {
    catalogDao.refresh(entity, modeType);
  }

  @Override
  public Optional<Tenant> find(Integer id) {
    return catalogDao.find(id);
  }

  @Override
  public Optional<Tenant> find(Integer id, long version) {
    return catalogDao.find(id, version);
  }

  @Override
  public Tenant findByReferenceId(String referenceId) {
    return catalogDao.findByReferenceId(referenceId);
  }

  @Override
  public Tenant findInactiveByReferenceId(String referenceId) {
    return catalogDao.findInactiveByReferenceId(referenceId);
  }

  @Override
  public List<Tenant> findRange(int start, int count) {
    return catalogDao.findRange(start, count);
  }

  @Override
  public long count() {
    return catalogDao.count();
  }

  @Override
  public long countInactive() {
    return catalogDao.countInactive();
  }

  @Override
  public void flush() {
    catalogDao.flush();
  }

  @Override
  public boolean hasVersionChanged(Tenant entity) {
    return catalogDao.hasVersionChanged(entity);
  }
}
