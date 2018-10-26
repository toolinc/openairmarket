package com.openairmarket.pos.persistence.model.price;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/** Sale price list is a further refinement of the {@link PriceList}. */
@Entity
@DiscriminatorValue("SALE")
public class SalePriceList extends PriceList {}
