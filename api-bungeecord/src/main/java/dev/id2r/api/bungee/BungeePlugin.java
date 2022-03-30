package dev.id2r.api.bungee;

import dev.id2r.api.common.dependency.DependencyManager;
import dev.id2r.api.common.plugin.ID2RPlugin;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Class to extend to use load your needs.
 *
 * @see ID2RPlugin
 */
public abstract class BungeePlugin extends Plugin implements ID2RPlugin {

    private final BungeeBootstrap bootstrap;

    private final BungeeDependencyManager dependencyManager;

    public BungeePlugin() {
        this.bootstrap = new BungeeBootstrap(this, this);
        this.dependencyManager = new BungeeDependencyManager(bootstrap);
    }

    @Override
    public final void onLoad() {
        this.bootstrap.load();
    }

    @Override
    public final void onEnable() {
        this.bootstrap.enable();
    }

    @Override
    public final void onDisable() {
        this.bootstrap.disable();
    }

    @Override
    public BungeeBootstrap getBootstrap() {
        return bootstrap;
    }

    @Override
    public DependencyManager getDependencyManager() {
        return this.dependencyManager;
    }

}
