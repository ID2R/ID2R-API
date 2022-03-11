package dev.id2r.api.bungee;

import dev.id2r.api.common.dependency.DependencyManager;
import dev.id2r.api.common.plugin.ID2RPlugin;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;

/**
 * Class to extend to use load your needs.
 *
 * @see ID2RPlugin
 */
public abstract class BungeePlugin implements ID2RPlugin {

    private final BungeeBootstrap bootstrap;

    private final BungeeDependencyManager dependencyManager;

    public BungeePlugin(BungeeBootstrap bootstrap) {
        this.bootstrap = bootstrap;
        this.dependencyManager = new BungeeDependencyManager(bootstrap);
    }

    @Override
    public ID2RPluginBootstrap getBootstrap() {
        return bootstrap;
    }

    @Override
    public DependencyManager getDependencyManager() {
        return this.dependencyManager;
    }

}
