package com.openairmarket.pos.server;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.devtools.common.options.OptionsParser;
import com.openairmarket.common.inject.Annotations.Args;
import com.openairmarket.pos.service.GreeterService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.util.Collections;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Greeter server that do not need a thread while "running" but may need one during startup and
 * shutdown.
 */
@Singleton
public class GreeterIdleServer extends AbstractIdleService {
  private final OptionsParser parser = OptionsParser.newOptionsParser(GreeterOptions.class);
  private final GreeterOptions greeterOptions;
  private final Server server;

  @Inject
  public GreeterIdleServer(@Args String[] args, GreeterService greeterService) {
    checkNotNull(args, "Missing Greeter args.");
    parser.parseAndExitUponError(args);
    greeterOptions = parser.getOptions(GreeterOptions.class);
    if (!greeterOptions.valid()) {
      printUsage();
    }
    server = ServerBuilder.forPort(greeterOptions.greeterPort).addService(greeterService).build();
  }

  protected void startUp() throws Exception {
    server.start();
  }

  protected void shutDown() throws Exception {
    server.shutdown();
  }

  private void printUsage() {
    String msg =
        parser.describeOptions(
            Collections.<String, String>emptyMap(), OptionsParser.HelpVerbosity.LONG);
    throw new IllegalArgumentException(msg);
  }
}
