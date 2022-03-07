package dev.id2r.api.common.storage.implementation.sql;

import dev.id2r.api.common.storage.implementation.sql.connection.ConnectionFactory;
import dev.id2r.api.common.plugin.ID2RPlugin;
import dev.id2r.api.common.storage.Storage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SQLStorage implements Storage {

    private final ID2RPlugin plugin;
    private final ConnectionFactory connectionFactory;

    private final String tablePrefix;

    private SQLStorage(ID2RPlugin plugin, ConnectionFactory factory, String tablePrefix) {
        this.plugin = plugin;
        this.connectionFactory = factory;
        this.tablePrefix = tablePrefix;
    }

    public abstract void applySchema(String path);

    @Override
    public ID2RPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public String getStorageName() {
        return "SQL";
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public void init() {
        this.connectionFactory.init(this.plugin);
    }

    @Override
    public void shutdown() throws Exception {
        this.connectionFactory.shutdown();
    }

    protected final boolean tableExists(final Connection connection, final String table) throws SQLException{
        try (ResultSet rs = connection.getMetaData().getTables(connection.getCatalog(),
                null, "%", null)) {
            while (rs.next()) {
                if (rs.getString(3).equalsIgnoreCase(table))
                    return true;
            }
        }
        return false;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

}
