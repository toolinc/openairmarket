package com.openairmarket.etl.pipeline.execution;

/** Marker interface that specifies the behavior of a flow for the migration workflow. */
public interface Execution {

  /**
   * Kickoff the execution of a particular pipeline.
   *
   * @param id specifies the desired pipeline id that will be executed
   * @throws ExecutionException in case of a failure or interruption of a {@code Execution}.
   */
  void execute(String id) throws ExecutionException;
}
