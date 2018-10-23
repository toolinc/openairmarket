package com.openairmarket.common.persistence.model.history;

import com.google.common.base.Preconditions;
import com.openairmarket.common.DateUtil;
import com.openairmarket.common.model.history.History;
import com.openairmarket.common.persistence.model.AbstractModel;
import com.openairmarket.common.persistence.model.security.SystemUser;
import java.util.Date;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Specifies the revision for an {@link javax.persistence.Entity} that is required to keep tenancy.
 */
@Entity
@Table(name = "audit")
public class Audit extends AbstractModel<UUID> implements History<SystemUser> {

  @Id
  @Column(name = "idAudit")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "createDate", nullable = false)
  private Date createdDate;

  @JoinColumn(name = "idSystemUser", referencedColumnName = "idSystemUser")
  @ManyToOne(cascade = CascadeType.REFRESH)
  private SystemUser systemUser;

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public void setId(UUID id) {
    this.id = Preconditions.checkNotNull(id);
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
    return systemUser;
  }

  @Override
  public void setUser(SystemUser systemUser) {
    this.systemUser = Preconditions.checkNotNull(systemUser);
  }
}
