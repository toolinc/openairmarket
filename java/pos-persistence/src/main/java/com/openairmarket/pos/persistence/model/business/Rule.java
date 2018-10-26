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

/** Defines the different types of business rules. */
@Entity
@Table(
    name = "rule",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "ruleTenantPK",
          columnNames = {"idTenant", "ruleType", "idReference"}),
      @UniqueConstraint(
          name = "ruleUK",
          columnNames = {"idTenant", "ruleType", "name"})
    })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 100)
public abstract class Rule extends AbstractCatalogTenantModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idRule")
  private Long id;

  @Column(name = "description", nullable = false)
  private String description;

  @JoinColumn(name = "idParentRule", referencedColumnName = "idRule")
  @ManyToOne(fetch = FetchType.LAZY)
  private Rule parentRule;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = Preconditions.checkNotNull(id);
  }

  public Rule getParentRule() {
    return parentRule;
  }

  public void setParentRule(Rule parentRule) {
    this.parentRule = parentRule;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = checkNotEmpty(description);
  }
}
