package com.openairmarket.etl.pipeline.execution;

/** Marker interface that specifies the behavior of a flow for the migration workflow. */
public interface Execution {

  /**
   * Kickoff the execution of a particular flow.
   *
   * @param id specifies the desired workflow id that will be executed
   * @throws ExecutionException in case of a failure or interruption of a {@code Flow}.
   */
  void execute(String id) throws ExecutionException;
}
