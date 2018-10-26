package com.openairmarket.pos.persistence.model.business;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.persistence.model.AbstractCatalogTenantModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Define the organizations that a {@code com.structureeng.persistence.model.tenant.Tenant} owns.
 */
// @EntityListeners(value = {AuditListener.class})
// @Revision(builder = OrganizationHistory.Builder.class)
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
public class Organization extends AbstractCatalogTenantModel<Long> {

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
  public static class Buider {

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
     * Creates a new instance of {@code Organization}.
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
