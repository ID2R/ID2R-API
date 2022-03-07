package dev.id2r.api.bungee;

import dev.id2r.api.common.platform.Platform;
import dev.id2r.api.common.plugin.bootstrap.LoaderBootstrap;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;
import dev.id2r.api.common.plugin.logging.JUtilPluginLogger;
import dev.id2r.api.common.plugin.logging.PluginLogger;
import dev.id2r.api.common.plugin.task.TaskFactory;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

public class BungeeBootstrap implements ID2RPluginBootstrap, LoaderBootstrap {

    private final Plugin loader;

    private final BungeePlugin plugin;

    private final BungeeTaskAdapter taskAdapter;

    private PluginLogger logger;

    private Instant startTime;

    public BungeeBootstrap(Plugin loader, BungeePlugin plugin) {
        this.loader = loader;
        this.plugin = plugin;
        this.taskAdapter = new BungeeTaskAdapter(this);
    }

    public final Plugin getLoader() {
        return loader;
    }

    public final ProxyServer getProxy() {
        return this.loader.getProxy();
    }

    @Override
    public void load() {
        this.logger = new JUtilPluginLogger(this.loader.getLogger());

        this.startTime = Instant.now();
        this.plugin.onLoad();
    }

    @Override
    public void enable() {
        this.plugin.onEnable();
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
        return getProxy().getVersion();
    }

    @Override
    public Instant getStartupTime() {
        return this.startTime;
    }

    @Override
    public Platform.Type getPlatformType() {
        return Platform.Type.BUNGEECORD;
    }

    @Override
    public Path getDataDirectory() {
        return this.loader.getDataFolder().toPath().toAbsolutePath();
    }

    @Override
    public int getPlayerCount() {
        return this.loader.getProxy().getOnlineCount();
    }

    @Override
    public Collection<String> getPlayerList() {
        final Collection<ProxiedPlayer> players = getProxy().getPlayers();
        List<String> list = new ArrayList<>(players.size());
        for (ProxiedPlayer player : players)
            list.add(player.getName());

        return list;
    }

    @Override
    public Collection<UUID> getOnlinePlayers() {
        final Collection<ProxiedPlayer> players = getProxy().getPlayers();
        List<UUID> list = new ArrayList<>(players.size());
        for (ProxiedPlayer player : players)
            list.add(player.getUniqueId());
        return list;
    }

    @Override
    public boolean isPlayerOnline(UUID uniqueId) {
        return getProxy().getPlayer(uniqueId) != null;
    }

    @Override
    public Optional<ProxiedPlayer> getPlayer(UUID uuid) {
        return Optional.ofNullable(getProxy().getPlayer(uuid));
    }

}
