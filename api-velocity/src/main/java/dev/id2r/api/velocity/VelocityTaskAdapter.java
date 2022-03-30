package dev.id2r.api.velocity;

import com.velocitypowered.api.scheduler.ScheduledTask;
import dev.id2r.api.common.plugin.task.SchedularTask;
import dev.id2r.api.common.plugin.task.TaskFactory;
import dev.id2r.api.common.utils.Iterators;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class VelocityTaskAdapter implements TaskFactory {

    private final VelocityBootstrap bootstrap;
    private final Executor executor;
    private final Set<ScheduledTask> tasks = Collections.newSetFromMap(new WeakHashMap<>());

    public VelocityTaskAdapter(VelocityBootstrap bootstrap) {
        this.bootstrap = bootstrap;
        this.executor = r -> bootstrap.getServer().getScheduler().buildTask(bootstrap, r).schedule();
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
        ScheduledTask t = this.bootstrap.getServer().getScheduler().buildTask(this.bootstrap.getServer(), runnable)
                .delay(interval, unit)
                .repeat(interval, unit)
                .schedule();
        this.tasks.add(t);
        return t::cancel;
    }

    @Override
    public SchedularTask delayAsync(Runnable runnable, long delay, TimeUnit unit) {
        ScheduledTask t = this.bootstrap.getServer().getScheduler().buildTask(this.bootstrap.getServer(), runnable)
                .delay(delay, unit)
                .schedule();
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
