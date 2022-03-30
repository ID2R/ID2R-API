package dev.id2r.api.spigot.plugin;

import dev.id2r.api.common.platform.Platform;
import dev.id2r.api.common.plugin.bootstrap.LoaderBootstrap;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;
import dev.id2r.api.common.plugin.logging.JUtilPluginLogger;
import dev.id2r.api.common.plugin.logging.PluginLogger;
import dev.id2r.api.common.plugin.task.AbstractJavaTask;
import dev.id2r.api.common.plugin.task.TaskFactory;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

public class SpigotBootstrap implements ID2RPluginBootstrap, LoaderBootstrap {

    private final Plugin loader;

    private final SpigotPlugin plugin;

    private final AbstractJavaTask taskAdapter;

    private PluginLogger logger;

    private Instant startTime;

    public <T extends SpigotPlugin> SpigotBootstrap(Plugin loader, T plugin) {
        this.loader = loader;
        this.plugin = plugin;
        this.taskAdapter = new SpigotTaskAdapter(this);
    }

    public Plugin getLoader() {
        return loader;
    }

    public Server getServer() {
        return this.loader.getServer();
    }

    @Override
    public void load() {
        this.logger = new JUtilPluginLogger(loader.getLogger());
        this.startTime = Instant.now();

        this.plugin.onLoad();
    }

    @Override
    public void enable() {
        this.plugin.enable();
    }

    @Override
    public void disable() {
        this.plugin.onDisable();
    }

    @Override
    public PluginLogger getLogger() {
        return this.logger;
    }

    @Override
    public TaskFactory getTaskFactory() {
        return this.taskAdapter;
    }

    @Override
    public String getVersion() {
        return getServer().getVersion();
    }

    @Override
    public Instant getStartupTime() {
        return this.startTime;
    }

    @Override
    public Platform.Type getPlatformType() {
        return Platform.Type.BUKKIT;
    }

    @Override
    public Path getDataDirectory() {
        return loader.getDataFolder().toPath().toAbsolutePath();
    }

    @Override
    public int getPlayerCount() {
        return getServer().getOnlinePlayers().size();
    }

    @Override
    public Collection<String> getPlayerList() {
        final Collection<? extends Player> players = getServer().getOnlinePlayers();
        List<String> list = new ArrayList<>(players.size());
        for (Player player : players)
            list.add(player.getName());

        return list;
    }

    @Override
    public Collection<UUID> getOnlinePlayers() {
        final Collection<? extends Player> players = getServer().getOnlinePlayers();
        final List<UUID> list = new ArrayList<>(players.size());
        for (Player player : players)
            list.add(player.getUniqueId());

        return list;
    }

    @Override
    public boolean isPlayerOnline(UUID uniqueId) {
        final Player player = getServer().getPlayer(uniqueId);
        return player != null && player.isOnline();
    }

    @Override
    public Optional<Player> getPlayer(UUID uuid) {
        return Optional.ofNullable(getServer().getPlayer(uuid));
    }

}
