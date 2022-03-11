package dev.id2r.api.common.storage.implementation.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import dev.id2r.api.common.storage.misc.StorageCredentials;
import dev.id2r.api.common.plugin.ID2RPlugin;
import dev.id2r.api.common.storage.Storage;

public class MongoDBStorage implements Storage {

    private final ID2RPlugin plugin;
    private final StorageCredentials credentials;

    private final String prefix, connectionUrl;

    protected MongoClient client;
    protected MongoDatabase database;

    public MongoDBStorage(ID2RPlugin plugin, StorageCredentials credentials, String prefix, String connectionUrl) {
        this.plugin = plugin;
        this.credentials = credentials;
        this.prefix = prefix;
        this.connectionUrl = connectionUrl;
    }

    @Override
    public ID2RPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public String getStorageName() {
        return "MongoDB";
    }

    @Override
    public void init() {

    }

    @Override
    public void shutdown() {
        if (this.client != null) {
            this.client.close();
        }
    }
}
