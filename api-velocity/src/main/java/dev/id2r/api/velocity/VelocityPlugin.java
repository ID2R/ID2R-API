package dev.id2r.api.velocity;

import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.id2r.api.common.plugin.ID2RPlugin;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;
import org.slf4j.Logger;

import java.nio.file.Path;

public abstract class VelocityPlugin implements ID2RPlugin {

    private final VelocityBootstrap bootstrap;
    private final VelocityDependencyManager dependencyManager;

    public VelocityPlugin(Logger logger, ProxyServer server, @DataDirectory Path data) {
        this.bootstrap = new VelocityBootstrap(server, logger, data, this);
        this.dependencyManager = new VelocityDependencyManager(this.bootstrap);
    }

    @Override
    public VelocityDependencyManager getDependencyManager() {
        return dependencyManager;
    }

    @Override
    public VelocityBootstrap getBootstrap() {
        return bootstrap;
    }

}
