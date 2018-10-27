package com.openairmarket.pos.persistence.model.audit.price;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Purchase price list is a further refinement of the {@link
 * com.openairmarket.pos.persistence.model.price.PurchasePriceList}.
 */
@Entity
@DiscriminatorValue("PURCHASE")
public final class PurchasePriceListAudit extends PriceListAudit {}
