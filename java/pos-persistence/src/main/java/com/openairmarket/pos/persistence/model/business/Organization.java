package com.openairmarket.pos.persistence.model.business;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.model.Domain;
import com.openairmarket.common.persistence.listener.Audit;
import com.openairmarket.common.persistence.model.AbstractCatalogTenantModel;
import com.openairmarket.pos.persistence.model.audit.business.OrganizationAudit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Define the organizations that a {@link com.openairmarket.common.persistence.model.tenant.Tenant}
 * owns.
 */
// @EntityListeners(value = {AuditListener.class})
@Audit(builderClass = OrganizationAudit.Builder.class)
@Entity
@Table(
    name = "organization",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "organizationPK",
          columnNames = {"idTenant", "idReference"}),
      @UniqueConstraint(
          name = "organizationUK",
          columnNames = {"idTenant", "name"})
    })
public final class Organization extends AbstractCatalogTenantModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idOrganization")
  private Long id;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = checkPositive(id);
  }

  /**
   * Creates a new {@link Organization.Buider} instance.
   *
   * @return - new instance
   */
  public static Organization.Buider newBuilder() {
    return new Organization.Buider();
  }

  /** Builder class that creates instances of {@link Organization}. */
  public static final class Buider implements Domain {

    private String referenceId;
    private String name;

    public Organization.Buider setReferenceId(String referenceId) {
      Preconditions.checkState(!Strings.isNullOrEmpty(referenceId));
      this.referenceId = referenceId;
      return this;
    }

    public Organization.Buider setName(String name) {
      this.name = checkNotEmpty(name);
      return this;
    }

    /**
     * Creates a new instance of {@link Organization}.
     *
     * @return - new instance
     */
    public Organization build() {
      Organization store = new Organization();
      store.setReferenceId(referenceId);
      store.setName(name);
      return store;
    }
  }
}
