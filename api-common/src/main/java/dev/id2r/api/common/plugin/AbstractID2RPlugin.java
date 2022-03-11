package dev.id2r.api.common.plugin;

import dev.id2r.api.common.dependency.DynamicDependency;

import java.util.Set;

public abstract class AbstractID2RPlugin {

    private final ID2RPlugin plugin;

    public AbstractID2RPlugin(final ID2RPlugin plugin) {
        this.plugin = plugin;
    }

    public Set<DynamicDependency> getGlobalDependencies() {
        return null;
    }

}
