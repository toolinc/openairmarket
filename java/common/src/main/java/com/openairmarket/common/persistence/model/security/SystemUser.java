package com.openairmarket.common.persistence.model.security;

import com.openairmarket.common.model.Domain;
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
public final class SystemUser extends AbstractTenantModel<Long> implements User {

  @Id
  @Column(name = "idSystemUser")
  private Long id;

  @Column(name = "email", length = 500, nullable = false)
  private String email;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = checkPositive(id);
  }

  @Override
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = checkNotEmpty(email);
  }

  /** Creates a new {@link SystemUser.Builder} instance. */
  public static final Builder newBuilder() {
    return new SystemUser.Builder().setActive(true);
  }

  /** Builder factory to creates instances of {@link SystemUser}. */
  public static final class Builder implements Domain {
    private Long id;
    private String email;
    private boolean active;

    public Builder setId(Long id) {
      this.id = checkPositive(id);
      return this;
    }

    public Builder setEmail(String email) {
      this.email = checkNotEmpty(email);
      return this;
    }

    public Builder setActive(boolean active) {
      this.active = active;
      return this;
    }

    public SystemUser build() {
      SystemUser systemUser = new SystemUser();
      systemUser.setId(id);
      systemUser.setEmail(email);
      systemUser.setActive(active);
      return systemUser;
    }
  }
}
