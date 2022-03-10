package dev.id2r.api.spigot.plugin;

import dev.id2r.api.common.plugin.task.AbstractJavaTask;
import dev.id2r.api.common.plugin.task.TaskFactory;

import java.util.concurrent.Executor;

public class SpigotTaskAdapter extends AbstractJavaTask implements TaskFactory {

    private final Executor sync;

    public SpigotTaskAdapter(SpigotBootstrap bootstrap) {
        this.sync = r -> bootstrap.getServer().getScheduler().scheduleSyncDelayedTask(bootstrap.getLoader(), r);
    }

    @Override
    public Executor sync() {
        return this.sync;
    }

}
