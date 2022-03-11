package dev.id2r.api.spigot.plugin;

import dev.id2r.api.common.dependency.DependencyManager;
import dev.id2r.api.common.plugin.ID2RPlugin;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;

public abstract class SpigotPlugin implements ID2RPlugin {

    private final SpigotBootstrap bootstrap;
    private final DependencyManager dependencyManager;

    public SpigotPlugin(SpigotBootstrap bootstrap) {
        this.bootstrap = bootstrap;
        this.dependencyManager = new SpigotDependencyManager(bootstrap);
    }

    @Override
    public ID2RPluginBootstrap getBootstrap() {
        return this.bootstrap;
    }

    @Override
    public DependencyManager getDependencyManager() {
        return this.dependencyManager;
    }
}
