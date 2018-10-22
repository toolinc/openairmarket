package com.openairmarket.common.persistence.security;

import com.google.common.base.Preconditions;
import com.openairmarket.common.model.security.User;
import com.openairmarket.common.persistence.model.AbstractTenantModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** Defines a system user. */
@Entity
@Table(
    name = "systemUser",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "systemUserTenantPK",
          columnNames = {"idTenant", "email"})
    })
public class SystemUser extends AbstractTenantModel<Long> implements User {

  @Id
  @Column(name = "idSystemUser")
  private Long id;

  @Column(name = "email", length = 500, nullable = false)
  private String email;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = Preconditions.checkNotNull(id);
  }

  @Override
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = Preconditions.checkNotNull(email);
  }
}
