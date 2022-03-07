package dev.id2r.api.common.plugin;

import dev.id2r.api.common.dependency.DependencyManager;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;

public interface ID2RPlugin {

    /**
     * Get the plugin bootstrap
     */
    ID2RPluginBootstrap getBootstrap();

    /**
     * method to be called on load
     */
    void onLoad();

    /**
     * method to be called when the plugin enables.
     */
    void onEnable();

    /**
     * method to be called when the plugin disables.
     */
    void onDisable();

    /**
     * Dynamic Dependency manager
     */
    DependencyManager getDependencyManager();

}
