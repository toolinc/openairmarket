package com.openairmarket.common.model.audit;

import com.openairmarket.common.model.Domain;
import com.openairmarket.common.model.security.User;
import java.util.Date;

/**
 * Specifies the contract for a revision entity.
 *
 * @param <T> specifies the {@code User}.
 */
public interface AuditModel<T extends User> extends Domain {

  Date getCreatedDate();

  public void setCreatedDate(Date createdDate);

  public T getUser();

  public void setUser(T user);

  public AuditType getAuditType();

  public void setAuditType(AuditType auditType);
}
