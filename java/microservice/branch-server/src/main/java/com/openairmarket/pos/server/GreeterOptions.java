package com.openairmarket.pos.server;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/** Specifies the command line options to start up the greeter server. */
public final class GreeterOptions extends OptionsBase {
  private static final String DELIMITER = "-";
  private static final String GREETER_PORT = DELIMITER + "greeterPort";

  @Option(
      name = GREETER_PORT,
      abbrev = 'p',
      help = "The greeter server port.",
      category = "startup",
      defaultValue = "50051")
  public int greeterPort;

  boolean valid() {
    boolean flag = false;
    if (greeterPort > 0) {
      flag = true;
    }
    return flag;
  }
}
