package dev.id2r.api.bungee;

import dev.id2r.api.common.plugin.task.SchedularTask;
import dev.id2r.api.common.plugin.task.TaskFactory;
import dev.id2r.api.common.utils.Iterators;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class BungeeTaskAdapter implements TaskFactory {

    private final BungeeBootstrap bootstrap;

    private final Executor executor;
    private final Set<ScheduledTask> tasks = Collections.newSetFromMap(new WeakHashMap<>());

    protected BungeeTaskAdapter(BungeeBootstrap bootstrap) {
        this.bootstrap = bootstrap;
        this.executor = r -> bootstrap.getProxy().getScheduler().runAsync(bootstrap.getLoader(), r);
    }

    @Override
    public Executor async() {
        return this.executor;
    }

    @Override
    public Executor sync() {
        return this.executor;
    }

    @Override
    public SchedularTask repeatAsync(Runnable runnable, long interval, TimeUnit unit) {
        ScheduledTask t = this.bootstrap.getProxy().getScheduler().schedule(this.bootstrap.getLoader(), runnable, interval, interval, unit);
        this.tasks.add(t);
        return t::cancel;
    }

    @Override
    public SchedularTask delayAsync(Runnable runnable, long delay, TimeUnit unit) {
        ScheduledTask t = this.bootstrap.getProxy().getScheduler().schedule(this.bootstrap.getLoader(), runnable,
                delay, unit);
        this.tasks.add(t);
        return t::cancel;
    }

    @Override
    public void shutdownTasks() {
        Iterators.tryIterate(this.tasks, ScheduledTask::cancel);
    }

    @Override
    public void shutdownExecutor() {}

}
