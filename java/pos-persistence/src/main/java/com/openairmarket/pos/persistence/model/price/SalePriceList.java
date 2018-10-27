package com.openairmarket.pos.persistence.model.price;

import com.openairmarket.common.persistence.listener.Audit;
import com.openairmarket.common.persistence.listener.AuditListener;
import com.openairmarket.pos.persistence.model.audit.price.SalePriceListAudit;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/** Sale price list is a further refinement of the {@link PriceList}. */
@EntityListeners(value = AuditListener.class)
@Audit(builderClass = SalePriceListAudit.Builder.class)
@Entity
@DiscriminatorValue("SALE")
public class SalePriceList extends PriceList {}
