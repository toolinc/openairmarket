package com.openairmarket.common.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

/** Package-protected annotations. */
public final class Annotations {

  private Annotations() {}

  /** Annotation for the command line args. */
  @Qualifier
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Args {}
}
