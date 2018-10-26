package com.openairmarket.pos.persistence.model.business;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.AbstractCatalogTenantModel;
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

/** Defines the different types of business rules per {@code Organization}. */
@Entity
@Table(
    name = "ruleOrganization",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "ruleOrganizationPK",
          columnNames = {"idTenant", "type", "idReference"}),
      @UniqueConstraint(
          name = "ruleOrganizationUK",
          columnNames = {"idTenant", "type", "name"})
    })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 100)
public abstract class RuleOrganization extends AbstractCatalogTenantModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idRuleOrganization")
  private Long id;

  @JoinColumn(name = "idOrganization", referencedColumnName = "idOrganization", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  @Column(name = "default")
  private Boolean defaulted;

  @JoinColumn(name = "idParentRuleOrganization", referencedColumnName = "idRuleOrganization")
  @ManyToOne(fetch = FetchType.LAZY)
  private RuleOrganization parentRule;

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

  public Boolean getDefaulted() {
    return defaulted;
  }

  public void setDefaulted(Boolean defaulted) {
    this.defaulted = Preconditions.checkNotNull(defaulted);
  }

  public RuleOrganization getParentRule() {
    return parentRule;
  }

  public void setParentRule(RuleOrganization parentRule) {
    this.parentRule = parentRule;
  }
}
