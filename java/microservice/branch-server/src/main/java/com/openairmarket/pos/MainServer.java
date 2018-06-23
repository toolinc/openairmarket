package com.openairmarket.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.openairmarket.common.inject.CommandLineModule;
import com.openairmarket.pos.inject.GreeterModule;
import com.openairmarket.pos.inject.H2DatabaseModule;
import com.openairmarket.pos.server.GreeterIdleServer;
import com.openairmarket.pos.server.H2IdleServer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Main server manager that launches the {@link H2IdleServer} and {@link GreeterIdleServer} from the
 * command line.
 */
public class MainServer {
  public static void main(String[] args) {
    Injector injector =
        Guice.createInjector(
            new CommandLineModule(args), new H2DatabaseModule(), new GreeterModule());
    H2IdleServer h2Server = injector.getInstance(H2IdleServer.class);
    GreeterIdleServer greeterServer = injector.getInstance(GreeterIdleServer.class);
    final ServiceManager serviceManager =
        new ServiceManager(ImmutableList.of(greeterServer, h2Server));
    serviceManager.addListener(
        new ServiceManager.Listener() {
          public void stopped() {
            System.out.println("Servers have been stopped.");
          }

          public void healthy() {
            System.out.println("Servers are up and running.");
          }

          public void failure(Service service) {
            System.err.println("Unable to start the server: " + service.toString());
            System.exit(1);
          }
        },
        MoreExecutors.directExecutor());

    Runtime.getRuntime()
        .addShutdownHook(
            new Thread() {
              public void run() {
                try {
                  serviceManager.stopAsync().awaitStopped(5, TimeUnit.SECONDS);
                } catch (TimeoutException timeout) {
                  System.err.println("Unable to stop the server.\n" + timeout.getMessage());
                }
              }
            });

    serviceManager.startAsync();
  }
}
