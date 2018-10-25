package com.openairmarket.common.persistence.dao.security;

import com.openairmarket.common.persistence.dao.ActiveDao;
import com.openairmarket.common.persistence.model.security.SystemUser;

/** Specifies the contract for the {@link SystemUser} data access object. */
public interface SystemUserDao extends ActiveDao<Long, SystemUser> {}
