package com.openairmarket.common.model.security;

import com.openairmarket.common.model.Domain;

/** Marker interface to identify a user. */
public interface User extends Domain {

  String getEmail();
}
