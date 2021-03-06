package dev.id2r.api.spigot.plugin;

import dev.id2r.api.common.dependency.DependencyManager;
import dev.id2r.api.common.plugin.ID2RPlugin;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public abstract class SpigotPlugin extends JavaPlugin implements ID2RPlugin {

    private final SpigotBootstrap bootstrap;
    private DependencyManager dependencyManager;

    public SpigotPlugin() {
        this.bootstrap = new SpigotBootstrap(this, this);
    }

    public final void enableDependencyManager() {
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
    public Optional<DependencyManager> getDependencyManager() {
        return Optional.ofNullable(this.dependencyManager);
    }
}
