package com.openairmarket.common.model.history;

import com.openairmarket.common.model.security.User;
import java.util.Date;
import java.util.UUID;

/** Specifies the contract for a revision entity. */
public interface History<T extends User> {

  UUID getId();

  void setId(UUID id);

  Date getCreatedDate();

  public void setCreatedDate(Date createdDate);

  public T getUser();

  public void setUser(T user);
}
