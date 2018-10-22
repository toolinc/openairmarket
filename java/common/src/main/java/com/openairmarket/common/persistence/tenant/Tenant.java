package com.openairmarket.common.persistence.tenant;

import com.openairmarket.common.persistence.history.tenant.TenantHistory;
import com.openairmarket.common.persistence.model.AbstractCatalogModel;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Define the characteristics of a tenant.
 *
 * @author Edgar Rico (edgar.martinez.rico@gmail.com)
 */
// @EntityListeners(value = {HistoryListener.class})
// @Revision(builder = TenantHistory.Builder.class)
@Entity
@Table(
    name = "tenant",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "tenantPK",
          columnNames = {"idReference"}),
      @UniqueConstraint(
          name = "tenantUK",
          columnNames = {"name"})
    })
public class Tenant extends AbstractCatalogModel<Integer, Integer> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idTenant")
  private Integer id;

  @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
  private Set<TenantHistory> tenantHistories;

  public Tenant() {}

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = checkPositive(id);
  }

  public Set<TenantHistory> getTenantHistories() {
    return tenantHistories;
  }

  public void setTenantHistories(Set<TenantHistory> tenantHistories) {
    this.tenantHistories = tenantHistories;
  }

  @Override
  public String toString() {
    return "Tenant {" + "id=" + id + '}';
  }

  /**
   * Creates a new {@code Builder} instance.
   *
   * @return - new instance
   */
  public static Buider newBuilder() {
    return new Buider();
  }

  /**
   * Builder class that creates instances of {@code Tenant}.
   *
   * @author Edgar Rico (edgar.martinez.rico@gmail.com)
   */
  public static class Buider {

    private Integer referenceId;
    private String name;

    public Buider setReferenceId(Integer referenceId) {
      this.referenceId = checkPositive(referenceId);
      return this;
    }

    public Buider setName(String name) {
      this.name = checkNotEmpty(name);
      return this;
    }

    /**
     * Creates a new instance of {@code Tenant}.
     *
     * @return - new instance
     */
    public Tenant build() {
      Tenant tenant = new Tenant();
      tenant.setReferenceId(referenceId);
      tenant.setName(name);
      return tenant;
    }
  }
}
