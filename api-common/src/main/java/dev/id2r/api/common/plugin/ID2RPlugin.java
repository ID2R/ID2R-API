package dev.id2r.api.common.plugin;

import dev.id2r.api.common.dependency.DependencyManager;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;

import java.util.Optional;

public interface ID2RPlugin {

    /**
     * Get the plugin bootstrap
     */
    ID2RPluginBootstrap getBootstrap();

    /**
     * method to be called on load
     */
    default void load() {}

    /**
     * method to be called when the plugin enables.
     */
    void enable();

    /**
     * method to be called when the plugin disables.
     */
    void disable();

    /**
     * Dynamic Dependency manager
     */
    Optional<DependencyManager> getDependencyManager();

}
