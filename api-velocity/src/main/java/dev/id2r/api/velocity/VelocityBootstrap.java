package dev.id2r.api.velocity;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.id2r.api.common.platform.Platform;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;
import dev.id2r.api.common.plugin.logging.PluginLogger;
import dev.id2r.api.common.plugin.logging.SLF4JPluginLogger;
import dev.id2r.api.common.plugin.task.TaskFactory;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

public class VelocityBootstrap implements ID2RPluginBootstrap {

    private final PluginLogger logger;

    private final TaskFactory taskAdapter;

    private final VelocityPlugin plugin;

    private Instant startTime;

    private final ProxyServer server;
    private final Path configDirectory;

    public VelocityBootstrap(ProxyServer server, Logger logger, Path configDirectory, VelocityPlugin plugin) {
        this.server = server;
        this.configDirectory = configDirectory;
        this.logger = new SLF4JPluginLogger(logger);
        this.taskAdapter = new VelocityTaskAdapter(this);
        this.plugin = plugin;
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onEnable(ProxyInitializeEvent e) {
        this.startTime = Instant.now();
        this.plugin.load();
        this.plugin.enable();
    }

    @Subscribe(order = PostOrder.LAST)
    public void onDisable(ProxyShutdownEvent e) {
        this.plugin.disable();
    }

    public ProxyServer getServer() {
        return server;
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
        return getServer().getVersion().getVersion();
    }

    @Override
    public Instant getStartupTime() {
        return this.startTime;
    }

    @Override
    public Platform.Type getPlatformType() {
        return Platform.Type.VELOCITY;
    }

    @Override
    public Path getDataDirectory() {
        return this.configDirectory.toAbsolutePath();
    }

    @Override
    public int getPlayerCount() {
        return this.getServer().getPlayerCount();
    }

    @Override
    public Collection<String> getPlayerList() {
        Collection<Player> players = this.getServer().getAllPlayers();
        List<String> list = new ArrayList<>(players.size());
        for (Player player : players)
            list.add(player.getUsername());
        return list;
    }

    @Override
    public Collection<UUID> getOnlinePlayers() {
        Collection<Player> players = this.getServer().getAllPlayers();
        List<UUID> list = new ArrayList<>(players.size());
        for (Player player : players)
            list.add(player.getUniqueId());
        return list;
    }

    @Override
    public boolean isPlayerOnline(UUID uniqueId) {
        Player player = this.getServer().getPlayer(uniqueId).orElse(null);
        return player != null && player.isActive();
    }

    @Override
    public Optional<?> getPlayer(UUID uuid) {
        return Optional.empty();
    }

}
