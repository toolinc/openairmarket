package com.openairmarket.pos.persistence.model.audit.business;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.audit.AbstractAuditCatalogTenantModel;
import com.openairmarket.common.persistence.model.audit.AuditActiveModel;
import com.openairmarket.pos.persistence.model.business.Organization;
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

/** Define the revision for the {@link Organization} entities. */
@Entity
@Table(
    name = "organizationAudit",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "organizationAuditUK",
          columnNames = {"idOrganization", "createDate"})
    })
@UuidGenerator(name = "organizationAudit_gen")
public class OrganizationAudit extends AbstractAuditCatalogTenantModel {

  @Id
  @GeneratedValue(generator = "organizationAudit_gen")
  @Column(name = "idOrganizationAudit")
  private String id;

  @JoinColumn(name = "idOrganization", referencedColumnName = "idOrganization", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {

    this.id = id;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = Preconditions.checkNotNull(organization);
  }

  /** Factory class for the {@link OrganizationAudit} entities. */
  public static class Builder extends AuditActiveModel.Builder<Organization, OrganizationAudit> {

    /**
     * Create an instance of {@link OrganizationAudit}.
     *
     * @param organization the instance that will be used to create a new {@link Organization}.
     * @return a new instance
     */
    @Override
    public OrganizationAudit build(Organization organization) {
      OrganizationAudit organizationHistory = new OrganizationAudit();
      organizationHistory.setOrganization(organization);
      organizationHistory.setReferenceId(organization.getReferenceId());
      organizationHistory.setName(organization.getName());
      organizationHistory.setActive(organization.getActive());
      organizationHistory.setVersion(organization.getVersion());
      return organizationHistory;
    }
  }
}
