package dev.id2r.api.bungee;

import dev.id2r.api.common.dependency.DependencyManager;
import dev.id2r.api.common.dependency.classloader.URLClassLoaderHelper;

import java.net.URLClassLoader;
import java.nio.file.Path;

public class BungeeDependencyManager extends DependencyManager {

    private final URLClassLoaderHelper classLoader;

    public BungeeDependencyManager(BungeeBootstrap bootstrap) {
        super(bootstrap.getLogger(), bootstrap.getDataDirectory());
        this.classLoader = new URLClassLoaderHelper((URLClassLoader) bootstrap.getLoader()
                .getClass().getClassLoader());
    }

    @Override
    protected void addToClasspath(Path file) {
        classLoader.addToClasspath(file);
    }



}
