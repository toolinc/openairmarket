package com.openairmarket.common.persistence.dao.tenant;

import com.openairmarket.common.persistence.dao.CatalogDao;
import com.openairmarket.common.persistence.model.tenant.Tenant;

/** Specifies the contract for the {@link Tenant} data access object. */
public interface TenantDao extends CatalogDao<Integer, Tenant> {}
