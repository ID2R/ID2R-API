package dev.id2r.api.common.storage;

import dev.id2r.api.common.plugin.ID2RPlugin;

public interface Storage {

    ID2RPlugin getPlugin();

    String getStorageName();

    void init();

    void shutdown() throws Exception;

}
