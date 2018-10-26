package com.openairmarket.pos.persistence.model.business;

/** Define the different types of taxes. */
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// @EntityListeners(value = {AuditListener.class})
// @Revision(builder = TaxCategoryHistory.Builder.class)
@Entity
@DiscriminatorValue("TAX_CATEGORY")
public class TaxCategory extends RuleOrganization {

  /**
   * Creates a new {@code TaxCategory.Builder} instance.
   *
   * @return - new instance
   */
  public static Buider newBuilder() {
    return new TaxCategory.Buider();
  }

  /**
   * Builder class that creates instances of {@code TaxCategory}.
   *
   * @author Edgar Rico (edgar.martinez.rico@gmail.com)
   */
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
     * Creates a new instance of {@code TaxCategory}.
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