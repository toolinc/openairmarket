package com.openairmarket.pos.persistence.model.product;

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
public class ProductManufacturer extends AbstractCatalogTenantModel<Long> {

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
   * Creates a new {@link ProductManufacturer.Buider} instance.
   *
   * @return - new instance
   */
  public static ProductManufacturer.Buider newBuilder() {
    return new ProductManufacturer.Buider();
  }

  /** Builder class that creates instances of {@link ProductManufacturer}. */
  public static class Buider {

    private String referenceId;
    private String name;

    public Buider setReferenceId(String referenceId) {
      Preconditions.checkState(!Strings.isNullOrEmpty(referenceId));
      this.referenceId = referenceId;
      return this;
    }

    public Buider setName(String name) {
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
