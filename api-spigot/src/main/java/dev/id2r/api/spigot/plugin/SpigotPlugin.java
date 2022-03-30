package dev.id2r.api.spigot.plugin;

import dev.id2r.api.common.dependency.DependencyManager;
import dev.id2r.api.common.plugin.ID2RPlugin;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SpigotPlugin extends JavaPlugin implements ID2RPlugin {

    private final SpigotBootstrap bootstrap;
    private final DependencyManager dependencyManager;

    public SpigotPlugin() {
        this.bootstrap = new SpigotBootstrap(this, this);
        this.dependencyManager = new SpigotDependencyManager(this.bootstrap);
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
    public SpigotBootstrap getBootstrap() {
        return this.bootstrap;
    }

    @Override
    public DependencyManager getDependencyManager() {
        return this.dependencyManager;
    }
}
