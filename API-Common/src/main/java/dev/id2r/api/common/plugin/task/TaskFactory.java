package dev.id2r.api.common.plugin.task;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public interface TaskFactory {

    /**
     * Async executor
     */
    Executor async();

    /**
     * sync executor
     */
    Executor sync();

    /**
     * Directly execute an async operation
     * @param runnable command
     */
    default void asyncExecution(Runnable runnable) {
        this.async().execute(runnable);
    }

    /**
     * Directly execute a sync operation
     * @param runnable command
     */
    default void syncExecution(Runnable runnable) {
        this.sync().execute(runnable);
    }

    /**
     * Execute async repeating task
     * @param runnable command
     * @param interval repeat interval
     * @param unit time unit for interval
     */
    SchedularTask repeatAsync(Runnable runnable, long interval, TimeUnit unit);

    /**
     * Execute async delayed task
     * @param runnable command
     * @param delay delay before execution
     * @param unit time unit for interval
     */
    SchedularTask delayAsync(Runnable runnable, long delay, TimeUnit unit);

    /**
     * Shutdown all active tasks
     */
    void shutdownTasks();

    /**
     * Shutdown executor handler
     */
    void shutdownExecutor();

}
