package com.openairmarket.common.persistence.model.audit;

import com.google.common.base.Preconditions;
import com.openairmarket.common.DateUtil;
import com.openairmarket.common.model.audit.AuditModel;
import com.openairmarket.common.model.audit.AuditType;
import com.openairmarket.common.persistence.model.security.SystemUser;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Specifies the revision for an {@link javax.persistence.Entity} that is required to keep an audit.
 */
@Embeddable
public class Auditable implements AuditModel<SystemUser> {

  @Enumerated(EnumType.STRING)
  @Column(name = "auditType", length = 25, nullable = false)
  private AuditType auditType;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "createDate", nullable = false)
  private Date createdDate;

  @OneToOne private SystemUser user;

  @Override
  public AuditType getAuditType() {
    return auditType;
  }

  @Override
  public void setAuditType(AuditType auditType) {
    this.auditType = Preconditions.checkNotNull(auditType);
  }

  @Override
  public Date getCreatedDate() {
    return DateUtil.clone(createdDate);
  }

  @Override
  public void setCreatedDate(Date createdDate) {
    this.createdDate = DateUtil.clone((Date) Preconditions.checkNotNull(createdDate.clone()));
  }

  @Override
  public SystemUser getUser() {
    return user;
  }

  @Override
  public void setUser(SystemUser systemUser) {
    this.user = Preconditions.checkNotNull(systemUser);
  }
}
