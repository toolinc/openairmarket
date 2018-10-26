package com.openairmarket.pos.persistence.model.price;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/** Purchase price list is a further refinement of the {@code PriceList}. */
@Entity
@DiscriminatorValue("PURCHASE")
public class PurchasePriceList extends PriceList {}
