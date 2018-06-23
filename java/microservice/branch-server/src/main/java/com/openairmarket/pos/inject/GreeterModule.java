package com.openairmarket.pos.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.openairmarket.common.inject.Annotations.Args;
import com.openairmarket.pos.server.GreeterIdleServer;
import com.openairmarket.pos.service.GreeterService;

/** Greeter server module. */
public class GreeterModule extends AbstractModule {

  protected void configure() {
    requireBinding(Key.get(new TypeLiteral<String[]>() {}, Args.class));
    bind(GreeterService.class);
    bind(GreeterIdleServer.class);
  }
}
