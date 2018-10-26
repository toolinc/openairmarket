package com.openairmarket.pos.persistence.model.business;

/** Define the different types of taxes. */
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.persistence.listener.Audit;
import com.openairmarket.common.persistence.listener.AuditListener;
import com.openairmarket.pos.persistence.model.audit.business.TaxCategoryAudit;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@EntityListeners(value = {AuditListener.class})
@Audit(builderClass = TaxCategoryAudit.Builder.class)
@Entity
@DiscriminatorValue("TAX_CATEGORY")
public final class TaxCategory extends RuleOrganization {

  /**
   * Creates a new {@link TaxCategory.Buider} instance.
   *
   * @return - new instance
   */
  public static Buider newBuilder() {
    return new TaxCategory.Buider();
  }

  /** Builder class that creates instances of {@link TaxCategory}. */
  public static class Buider {

    private String referenceId;
    private String name;
    private Organization organization;

    public Buider setReferenceId(String referenceId) {
      Preconditions.checkState(Strings.isNullOrEmpty(referenceId));
      this.referenceId = referenceId;
      return this;
    }

    public Buider setName(String name) {
      Preconditions.checkState(Strings.isNullOrEmpty(name));
      this.name = name;
      return this;
    }

    public Buider setOrganization(Organization organization) {
      this.organization = Preconditions.checkNotNull(organization);
      return this;
    }

    /**
     * Creates a new instance of {@link TaxCategory}.
     *
     * @return - new instance
     */
    public TaxCategory build() {
      TaxCategory taxType = new TaxCategory();
      taxType.setReferenceId(referenceId);
      taxType.setName(name);
      taxType.setOrganization(organization);
      return taxType;
    }
  }
}
