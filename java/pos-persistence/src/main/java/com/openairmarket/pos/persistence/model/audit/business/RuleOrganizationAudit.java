package com.openairmarket.pos.persistence.model.audit.business;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.audit.AbstractAuditCatalogTenantModel;
import com.openairmarket.pos.persistence.model.business.Organization;
import com.openairmarket.pos.persistence.model.business.RuleOrganization;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** Define the revision for the {@link RuleOrganization} entities. */
@Entity
@Table(
    name = "ruleOrganizationAudit",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "ruleOrganizationAuditUK",
          columnNames = {"idRuleOrganization", "createDate"})
    })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ruleType", discriminatorType = DiscriminatorType.STRING, length = 100)
public abstract class RuleOrganizationAudit extends AbstractAuditCatalogTenantModel {

  @Id
  @Column(name = "idRuleOrganizationAudit")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(
      name = "idRuleOrganization",
      referencedColumnName = "idRuleOrganization",
      nullable = false)
  @ManyToOne(cascade = CascadeType.REFRESH)
  private RuleOrganization ruleOrganization;

  @JoinColumn(name = "idOrganization", referencedColumnName = "idOrganization", nullable = false)
  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private Organization organization;

  @JoinColumn(name = "idParentRuleOrganization", referencedColumnName = "idRuleOrganization")
  @ManyToOne(cascade = CascadeType.REFRESH)
  private RuleOrganization parentRule;

  @Column(name = "default")
  private Boolean defaulted;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = checkPositive(id);
  }

  public RuleOrganization getRuleOrganization() {
    return ruleOrganization;
  }

  public void setRuleOrganization(RuleOrganization ruleOrganization) {
    this.ruleOrganization = Preconditions.checkNotNull(ruleOrganization);
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = checkNotNull(organization);
  }

  public RuleOrganization getParentRule() {
    return parentRule;
  }

  public void setParentRule(RuleOrganization parentRule) {
    this.parentRule = parentRule;
  }

  public Boolean getDefaulted() {
    return defaulted;
  }

  public void setDefaulted(Boolean defaulted) {
    this.defaulted = defaulted;
  }
}
