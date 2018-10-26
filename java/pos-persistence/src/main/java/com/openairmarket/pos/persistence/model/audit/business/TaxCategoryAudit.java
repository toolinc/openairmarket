package com.openairmarket.pos.persistence.model.audit.business;

import com.openairmarket.common.persistence.model.audit.AuditActiveModel;
import com.openairmarket.pos.persistence.model.business.TaxCategory;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/** Define the revision for the {@link TaxCategory} entities. */
@Entity
@DiscriminatorValue("TAX_CATEGORY")
public final class TaxCategoryAudit extends RuleOrganizationAudit {

  /** Factory class for the {@link TaxCategoryAudit} entities. */
  public static class Builder extends AuditActiveModel.Builder<TaxCategory, TaxCategoryAudit> {

    /**
     * Create an instance of {@link TaxCategoryAudit}.
     *
     * @param taxCategory the instance that will be used to create a new {@link TaxCategory}.
     * @return a new instance
     */
    @Override
    public TaxCategoryAudit build(TaxCategory taxCategory) {
      TaxCategoryAudit taxCategoryHistory = new TaxCategoryAudit();
      taxCategoryHistory.setRuleOrganization(taxCategory);
      taxCategoryHistory.setReferenceId(taxCategory.getReferenceId());
      taxCategoryHistory.setName(taxCategory.getName());
      taxCategoryHistory.setOrganization(taxCategory.getOrganization());
      taxCategoryHistory.setActive(taxCategory.getActive());
      taxCategoryHistory.setVersion(taxCategory.getVersion());
      return taxCategoryHistory;
    }
  }
}
