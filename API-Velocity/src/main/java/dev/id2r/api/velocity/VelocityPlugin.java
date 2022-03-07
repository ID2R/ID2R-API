package dev.id2r.api.velocity;

import dev.id2r.api.common.plugin.ID2RPlugin;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;

public abstract class VelocityPlugin implements ID2RPlugin {

    private final ID2RPluginBootstrap bootstrap;
    private final VelocityDependencyManager dependencyManager;

    public VelocityPlugin(VelocityBootstrap bootstrap) {
        this.bootstrap = bootstrap;
        this.dependencyManager = new VelocityDependencyManager(bootstrap);
    }

    @Override
    public VelocityDependencyManager getDependencyManager() {
        return dependencyManager;
    }

    @Override
    public ID2RPluginBootstrap getBootstrap() {
        return bootstrap;
    }

}
