package com.graphaware.runtime.module;

import com.graphaware.tx.event.batch.api.TransactionSimulatingBatchInserter;

/**
 * {@link TxDrivenModule} that can be backed by a {@link TransactionSimulatingBatchInserter} (on top of {@link org.neo4j.graphdb.GraphDatabaseService}).
 */
public interface BatchSupportingTxDrivenModule extends TxDrivenModule {

    /**
     * Initialize this module. This method must bring the module to a state equivalent to a state of the same module that
     * has been registered at all times since the database was empty. It can perform global-graph operations to achieve
     * this.
     *
     * @param batchInserter to initialize this module for.
     */
    void initialize(TransactionSimulatingBatchInserter batchInserter);

    /**
     * Re-initialize this module. This method must remove all metadata written to the graph by this module and bring the
     * module to a state equivalent to a state of the same module that has been registered at all times since the
     * database was empty. It can perform global-graph operations to achieve this.
     *
     * @param batchInserter to initialize this module for.
     */
    void reinitialize(TransactionSimulatingBatchInserter batchInserter);
}