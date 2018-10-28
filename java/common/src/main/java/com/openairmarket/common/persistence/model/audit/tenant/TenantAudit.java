package com.openairmarket.common.persistence.model.audit.tenant;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.audit.AbstractAuditCatalogModel;
import com.openairmarket.common.persistence.model.audit.AuditActiveModel;
import com.openairmarket.common.persistence.model.tenant.Tenant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.eclipse.persistence.annotations.UuidGenerator;

/**
 * Define the revision for the {@code Tenant} entities.
 *
 * @author Edgar Rico (edgar.martinez.rico@gmail.com)
 */
@Entity
@Table(
    name = "tenantAudit",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "tenantAuditUK",
          columnNames = {"idTenant", "createDate"})
    })
@UuidGenerator(name = "tenantAudit_gen")
public class TenantAudit extends AbstractAuditCatalogModel {

  @Id
  @Column(name = "idTenantHistory")
  @GeneratedValue(generator = "tenantAudit_gen")
  private String id;

  @JoinColumn(name = "idTenant", referencedColumnName = "idTenant", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Tenant tenant;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = checkNotEmpty(id);
  }

  public Tenant getTenant() {
    return tenant;
  }

  public void setTenant(Tenant tenant) {
    this.tenant = Preconditions.checkNotNull(tenant);
  }

  /**
   * Factory class for the {@code TenantAudit} entities.
   *
   * @author Edgar Rico (edgar.martinez.rico@gmail.com)
   */
  public static class Builder extends AuditActiveModel.Builder<Tenant, TenantAudit> {

    /**
     * Create an instance of {@code TenantAudit}.
     *
     * @param tenant the instance that will be used to create a new {@code Tenant}.
     * @return a new instance
     */
    @Override
    public TenantAudit build(Tenant tenant) {
      TenantAudit tenantAudit = new TenantAudit();
      tenantAudit.setTenant(tenant);
      tenantAudit.setReferenceId(tenant.getReferenceId());
      tenantAudit.setName(tenant.getName());
      tenantAudit.setActive(tenant.getActive());
      tenantAudit.setVersion(tenant.getVersion());
      return tenantAudit;
    }
  }
}
