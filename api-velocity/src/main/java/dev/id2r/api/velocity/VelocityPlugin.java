package dev.id2r.api.velocity;

import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.id2r.api.common.dependency.DependencyManager;
import dev.id2r.api.common.plugin.ID2RPlugin;
import dev.id2r.api.common.plugin.bootstrap.ID2RPluginBootstrap;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Optional;

public abstract class VelocityPlugin implements ID2RPlugin {

    private final VelocityBootstrap bootstrap;
    private VelocityDependencyManager dependencyManager;

    public VelocityPlugin(Logger logger, ProxyServer server, @DataDirectory Path data) {
        this.bootstrap = new VelocityBootstrap(server, logger, data, this);
    }

    public final void enableDependencyManager() {
        this.dependencyManager = new VelocityDependencyManager(this.bootstrap);
    }

    @Override
    public Optional<DependencyManager> getDependencyManager() {
        return Optional.ofNullable(dependencyManager);
    }

    @Override
    public VelocityBootstrap getBootstrap() {
        return bootstrap;
    }

}
