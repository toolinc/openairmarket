package com.openairmarket.common.model.history;

import com.openairmarket.common.model.security.User;
import java.util.Date;

/** Specifies the contract for a revision entity. */
public interface History<T extends User> {

  Long getId();

  void setId(Long id);

  Date getCreatedDate();

  public void setCreatedDate(Date createdDate);

  public T getUser();

  public void setUser(T user);
}
