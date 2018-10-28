package com.openairmarket.pos.persistence.model.product;

import com.openairmarket.common.model.Domain;
import com.openairmarket.common.persistence.model.AbstractCatalogTenantModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** Define the different companies of a {@link ProductType}. */
// @EntityListeners(value = {AuditListener.class})
// @Revision(builder = ProductManufacturerHistory.Builder.class)
@Entity
@Table(
    name = "productManufacturer",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "productManufacturerPK",
          columnNames = {"idTenant", "idReference"}),
      @UniqueConstraint(
          name = "productManufacturerUK",
          columnNames = {"idTenant", "name"})
    })
public final class ProductManufacturer extends AbstractCatalogTenantModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idProductManufacturer")
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
   * Creates a new {@link Builder} instance.
   *
   * @return - new instance
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /** Builder class that creates instances of {@link ProductManufacturer}. */
  public static final class Builder implements Domain {

    private String referenceId;
    private String name;

    public Builder setReferenceId(String referenceId) {
      this.referenceId = checkNotEmpty(referenceId);
      return this;
    }

    public Builder setName(String name) {
      this.name = checkNotEmpty(name);
      return this;
    }

    /**
     * Creates a new instance of {@link ProductManufacturer}.
     *
     * @return - new instance
     */
    public ProductManufacturer build() {
      ProductManufacturer company = new ProductManufacturer();
      company.setReferenceId(referenceId);
      company.setName(name);
      return company;
    }
  }
}
