package com.openairmarket.common.inject;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.openairmarket.common.inject.Annotations.Args;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/** Command line module. */
public final class CommandLineModule extends AbstractModule {

  private final String[] args;

  public CommandLineModule(String[] args) {
    this.args = checkNotNull(args, "Missing args.");
  }

  protected void configure() {
    bind(Key.get(new TypeLiteral<String[]>() {}, Args.class)).toInstance(args);
  }
}
