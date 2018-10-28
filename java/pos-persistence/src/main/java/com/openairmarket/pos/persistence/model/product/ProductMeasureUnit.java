package com.openairmarket.pos.persistence.model.product;

import com.google.common.base.Preconditions;
import com.openairmarket.common.model.Domain;
import com.openairmarket.common.persistence.model.AbstractCatalogTenantModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** Define the different measure units of a {@link ProductType}. */
// @EntityListeners(value = {AuditListener.class})
// @Audit(builderClass = ProductMeasureUnitHistory.Builder.class)
@Entity
@Table(
    name = "productMeasureUnit",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "productMeasureUnitPK",
          columnNames = {"idTenant", "idReference"}),
      @UniqueConstraint(
          name = "productMeasureUnitUK",
          columnNames = {"idTenant", "name"})
    })
public final class ProductMeasureUnit extends AbstractCatalogTenantModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idProductMeasureUnit")
  private Long id;

  @Column(name = "countable", nullable = false)
  private Boolean countable;

  @Column(name = "expire", nullable = false)
  private Boolean expire;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = checkPositive(id);
  }

  public Boolean getCountable() {
    return countable;
  }

  public void setCountable(Boolean countable) {
    this.countable = Preconditions.checkNotNull(countable);
  }

  public Boolean getExpire() {
    return expire;
  }

  public void setExpire(Boolean expire) {
    this.expire = Preconditions.checkNotNull(expire);
  }

  /**
   * Creates a new {@link Builder} instance.
   *
   * @return - new instance
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /** Builder class that creates instances of {@link ProductMeasureUnit}. */
  public static final class Builder implements Domain {

    private String referenceId;
    private String name;
    private Boolean countable;
    private Boolean expire;

    public Builder setReferenceId(String referenceId) {
      this.referenceId = checkNotEmpty(referenceId);
      return this;
    }

    public Builder setName(String name) {
      this.name = checkNotEmpty(name);
      return this;
    }

    public Builder setCountable(Boolean countable) {
      this.countable = Preconditions.checkNotNull(countable);
      return this;
    }

    public Builder setExpire(Boolean expire) {
      this.expire = Preconditions.checkNotNull(expire);
      return this;
    }

    /**
     * Creates a new instance of {@link ProductMeasureUnit}.
     *
     * @return - new instance
     */
    public ProductMeasureUnit build() {
      ProductMeasureUnit paquete = new ProductMeasureUnit();
      paquete.setReferenceId(referenceId);
      paquete.setName(name);
      paquete.setCountable(countable);
      paquete.setExpire(expire);
      return paquete;
    }
  }
}
