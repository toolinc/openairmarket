package com.openairmarket.common.persistence.model.history;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.DateUtil;
import com.openairmarket.common.model.history.History;
import com.openairmarket.common.persistence.model.AbstractModel;
import com.openairmarket.common.persistence.model.security.SystemUser;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.annotations.UuidGenerator;

/**
 * Specifies the revision for an {@link javax.persistence.Entity} that is required to keep tenancy.
 */
@Entity
@Table(name = "audit")
@UuidGenerator(name = "audit_id_gen")
public class Audit extends AbstractModel<String> implements History<SystemUser> {

  @Id
  @Column(name = "idAudit")
  @GeneratedValue(generator = "audit_id_gen")
  private String id;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "createDate", nullable = false)
  private Date createdDate;

  @JoinColumn(name = "idSystemUser", referencedColumnName = "idSystemUser")
  @ManyToOne(cascade = CascadeType.REFRESH)
  private SystemUser systemUser;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    Preconditions.checkState(!Strings.isNullOrEmpty(id));
    this.id = id;
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
