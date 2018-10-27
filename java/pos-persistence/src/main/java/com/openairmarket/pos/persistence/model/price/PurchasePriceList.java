package com.openairmarket.pos.persistence.model.price;

import com.openairmarket.common.persistence.listener.Audit;
import com.openairmarket.common.persistence.listener.AuditListener;
import com.openairmarket.pos.persistence.model.audit.price.PurchasePriceListAudit;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/** Purchase price list is a further refinement of the {@code PriceList}. */
@EntityListeners(value = AuditListener.class)
@Audit(builderClass = PurchasePriceListAudit.Builder.class)
@Entity
@DiscriminatorValue("PURCHASE")
public class PurchasePriceList extends PriceList {}
