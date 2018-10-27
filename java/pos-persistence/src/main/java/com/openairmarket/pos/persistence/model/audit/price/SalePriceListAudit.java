package com.openairmarket.pos.persistence.model.audit.price;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Sale price list is a further refinement of the {@link
 * com.openairmarket.pos.persistence.model.price.SalePriceList}.
 */
@Entity
@DiscriminatorValue("SALE")
public final class SalePriceListAudit extends PriceListAudit {}
