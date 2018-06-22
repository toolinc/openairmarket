package com.openairmarket.pos.server;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;
import java.util.List;

/** Specifies the command line options to start up a h2 web and tcp server. */
public final class H2Options extends OptionsBase {

  private static final String DELIMITER = "-";
  private static final String TCP = DELIMITER + "tcp";
  private static final String TCP_PORT = "tcpPort";
  private static final String TCP_ALL = "tcpAllowOthers";
  private static final ImmutableList<String> SERVER = ImmutableList.of(TCP, DELIMITER + TCP_PORT);
  private static final String WEB = DELIMITER + "web";
  private static final String WEB_PORT = "webPort";
  private static final String WEB_ALL = "webAllowOthers";
  private static final ImmutableList<String> WEB_SERVER =
      ImmutableList.of(WEB, DELIMITER + WEB_PORT);

  @Option(
      name = TCP_PORT,
      abbrev = 'p',
      help = "The h2 server port.",
      category = "startup",
      defaultValue = "9092")
  public int tcpPort;

  @Option(
      name = TCP_ALL,
      abbrev = 'a',
      help = "Allow external tcp connections.",
      defaultValue = "true")
  public boolean tcpAllowOthers;

  @Option(
      name = WEB_PORT,
      abbrev = 'w',
      help = "The h2 web server port.",
      category = "startup",
      defaultValue = "9093")
  public int webPort;

  @Option(
      name = WEB_ALL,
      abbrev = 'c',
      help = "Allow external web connections.",
      defaultValue = "true")
  public boolean webAllowOthers;

  boolean valid() {
    boolean flag = false;
    if (tcpPort > 0 && webPort > 0) {
      flag = true;
    }
    return flag;
  }

  String[] getServerArgs() {
    List<String> args = Lists.newLinkedList(SERVER);
    checkArgument(tcpPort > 0, "Port is not properly set.");
    args.add("" + tcpPort);
    if (tcpAllowOthers) {
      args.add(DELIMITER + TCP_ALL);
    }
    return args.toArray(new String[0]);
  }

  String[] getWebServerArgs() {
    List<String> args = Lists.newLinkedList(WEB_SERVER);
    checkArgument(webPort > 0, "Web Port is not properly set.");
    args.add("" + webPort);
    if (webAllowOthers) {
      args.add(DELIMITER + WEB_ALL);
    }
    return args.toArray(new String[0]);
  }
}
