package com.openairmarket.pos.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.openairmarket.common.inject.Annotations.Args;
import com.openairmarket.pos.server.H2IdleServer;

/** H2 database embedded module. */
public final class H2DatabaseModule extends AbstractModule {

  protected void configure() {
    requireBinding(Key.get(new TypeLiteral<String[]>() {}, Args.class));
    bind(H2IdleServer.class);
  }
}
