package com.openairmarket.pos.server;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.devtools.common.options.OptionsParser;
import com.openairmarket.common.inject.Annotations.Args;
import java.sql.SQLException;
import java.util.Collections;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.h2.tools.Server;

/**
 * H2 servers that do not need a thread while "running" but may need one during startup and
 * shutdown.
 */
@Singleton
public final class H2IdleServer extends AbstractIdleService {

  private final OptionsParser parser = OptionsParser.newOptionsParser(H2Options.class);
  private final H2Options h2Options;
  private final Server server;
  private final Server webServer;

  @Inject
  H2IdleServer(@Args String[] args) {
    checkNotNull(args, "Missing H2 args.");
    parser.parseAndExitUponError(args);
    h2Options = parser.getOptions(H2Options.class);
    if (!h2Options.valid()) {
      printUsage();
    }
    try {
      server = Server.createTcpServer(h2Options.getServerArgs());
      webServer = Server.createWebServer(h2Options.getWebServerArgs());
    } catch (SQLException exc) {
      throw new IllegalStateException(exc);
    }
  }

  protected void startUp() throws Exception {
    server.start();
    webServer.start();
  }

  protected void shutDown() throws Exception {
    server.stop();
    webServer.stop();
  }

  private void printUsage() {
    String msg =
        parser.describeOptions(
            Collections.<String, String>emptyMap(), OptionsParser.HelpVerbosity.LONG);
    throw new IllegalArgumentException(msg);
  }
}
