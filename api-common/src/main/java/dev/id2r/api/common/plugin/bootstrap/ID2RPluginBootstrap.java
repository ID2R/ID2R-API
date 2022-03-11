package dev.id2r.api.common.plugin.bootstrap;

import com.google.common.collect.ImmutableSet;
import dev.id2r.api.common.platform.Platform;
import dev.id2r.api.common.plugin.logging.PluginLogger;
import dev.id2r.api.common.plugin.task.TaskFactory;
import dev.id2r.api.common.dependency.DynamicDependency;
import dev.id2r.api.common.dependency.Dependencies;

import java.io.InputStream;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ID2RPluginBootstrap {

    /**
     * Logger adapter for all platforms
     *
     * @return plugin logger
     */
    PluginLogger getLogger();

    /**
     * Task adapter for platform
     *
     * @return task adapter
     */
    TaskFactory getTaskFactory();

    /**
     * Set of required dependencies for this plugin to run.
     *
     * @return immutable set of required dependencies
     */
     default ImmutableSet<DynamicDependency> getRequiredDependencies() {
         return ImmutableSet.<DynamicDependency>builder()
                 .add(Dependencies.CONFIGME.create())
                 .add(Dependencies.SLF4J.create())
                 .add(Dependencies.HIKARICP.create())
                 .add(Dependencies.GSON.create())
                 .add(Dependencies.AGRONA.create())
                 .add(Dependencies.MONGODB_DRIVER.create())
                 .add(Dependencies.HIKARICP.create())
                 .build();
     }

    /**
     * get plugin version
     *
     * @return version in string
     */
    String getVersion();

    /**
     * Gets the time when the plugin first started in millis.
     *
     * @return enable time
     */
    Instant getStartupTime();

    /**
     * Gets the platform type this instance is running on.
     *
     * @return the platform type
     */
    Platform.Type getPlatformType();

    /**
     * Gets the plugins main data storage directory
     *
     * @return the platforms' data folder
     */
    Path getDataDirectory();

    /**
     * Gets a bundled resource file from the jar
     *
     * @param path the path of the file
     * @return the file as an input stream
     */
    default InputStream getResourceStream(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }

    /**
     * Gets the number of users online on the platform
     *
     * @return the number of users
     */
    int getPlayerCount();

    /**
     * Gets the usernames of the users online on the platform
     *
     * @return a {@link java.util.List} of usernames
     */
    Collection<String> getPlayerList();

    /**
     * Gets the UUIDs of the users online on the platform
     *
     * @return a {@link java.util.Set} of UUIDs
     */
    Collection<UUID> getOnlinePlayers();

    /**
     * Checks if a user is online
     *
     * @param uniqueId the users external uuid
     * @return true if the user is online
     */
    boolean isPlayerOnline(UUID uniqueId);


    /**
     * Get a player object
     *
     * @param uuid of player
     * @return return if the player is present, empty if not
     */
    Optional<?> getPlayer(UUID uuid);

}
