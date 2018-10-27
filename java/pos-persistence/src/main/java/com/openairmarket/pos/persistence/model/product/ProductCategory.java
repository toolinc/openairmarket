package com.openairmarket.pos.persistence.model.product;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.model.Domain;
import com.openairmarket.common.persistence.model.AbstractCatalogTenantModel;
import com.openairmarket.pos.persistence.model.business.Organization;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** Define the different categories in which a {@link ProductType} can be assigned. */
// @EntityListeners(value = {AuditListener.class})
// @Revision(builder = ProductCategoryHistory.Builder.class)
@Entity
@Table(
    name = "productCategory",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "productCategoryPK",
          columnNames = {"idTenant", "idReference"}),
      @UniqueConstraint(
          name = "productCategoryUK",
          columnNames = {"idTenant", "name"})
    })
public final class ProductCategory extends AbstractCatalogTenantModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idProductCategory")
  private Long id;

  @JoinColumn(name = "idOrganization", referencedColumnName = "idOrganization", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = checkPositive(id);
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = Preconditions.checkNotNull(organization);
  }

  /**
   * Creates a new {@code Builder} instance.
   *
   * @return - new instance
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /** Builder class that creates instances of {@link ProductCategory}. */
  public static final class Builder implements Domain {

    private String referenceId;
    private String name;
    private Organization organization;

    public Builder setReferenceId(String referenceId) {
      Preconditions.checkState(!Strings.isNullOrEmpty(referenceId));
      this.referenceId = referenceId;
      return this;
    }

    public Builder setName(String name) {
      this.name = checkNotEmpty(name);
      return this;
    }

    public Builder setOrganization(Organization organization) {
      this.organization = Preconditions.checkNotNull(organization);
      return this;
    }

    /**
     * Creates a new instance of {@code Division}.
     *
     * @return - new instance
     */
    public ProductCategory build() {
      ProductCategory division = new ProductCategory();
      division.setReferenceId(referenceId);
      division.setName(name);
      division.setOrganization(organization);
      return division;
    }
  }
}
