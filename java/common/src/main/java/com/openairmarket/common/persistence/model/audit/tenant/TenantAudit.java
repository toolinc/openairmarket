package com.openairmarket.common.persistence.model.audit.tenant;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.persistence.model.audit.AbstractAuditActiveModel;
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
          name = "tenantHistoryUK",
          columnNames = {"idTenant"})
    })
@UuidGenerator(name = "tenantAudit_gen")
public class TenantAudit extends AbstractAuditActiveModel {

  @Id
  @Column(name = "idTenantHistory")
  @GeneratedValue(generator = "tenantAudit_gen")
  private String id;

  @Column(name = "idReference", nullable = false)
  private String referenceId;

  @Column(name = "name", nullable = false)
  private String name;

  @JoinColumn(name = "idTenant", referencedColumnName = "idTenant", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Tenant tenant;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    Preconditions.checkState(!Strings.isNullOrEmpty(id));
    this.id = id;
  }

  public Tenant getTenant() {
    return tenant;
  }

  public void setTenant(Tenant tenant) {
    this.tenant = Preconditions.checkNotNull(tenant);
  }

  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    Preconditions.checkState(!Strings.isNullOrEmpty(referenceId));
    this.referenceId = referenceId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = checkNotEmpty(name);
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
