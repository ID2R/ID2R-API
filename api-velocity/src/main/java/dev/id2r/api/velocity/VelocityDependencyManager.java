package dev.id2r.api.velocity;

import dev.id2r.api.common.dependency.DependencyManager;

import java.nio.file.Path;

public class VelocityDependencyManager extends DependencyManager {

    private final VelocityBootstrap bootstrap;

    public VelocityDependencyManager(VelocityBootstrap bootstrap) {
        super(bootstrap.getLogger(), bootstrap.getDataDirectory());
        this.bootstrap = bootstrap;
    }

    @Override
    protected void addToClasspath(Path file) {
        bootstrap.getLoader().getPluginManager().addToClasspath(this.bootstrap, file);
    }
}
