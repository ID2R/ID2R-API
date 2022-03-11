package dev.id2r.api.common.storage.implementation.sql.connection.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.id2r.api.common.plugin.ID2RPlugin;
import dev.id2r.api.common.storage.implementation.sql.connection.ConnectionFactory;
import dev.id2r.api.common.storage.misc.StorageCredentials;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class HikariConnectionFactory implements ConnectionFactory {

    private final StorageCredentials credentials;

    private HikariDataSource dataSource;

    public HikariConnectionFactory(StorageCredentials credentials) {
        this.credentials = credentials;
    }

    protected abstract String defaultPort();

    protected abstract void configureDatabase(HikariConfig config, String address, String port, String databaseName, String username, String password);

    protected void overrideProperties(Map<String, String> properties) {
        // https://github.com/brettwooldridge/HikariCP/wiki/Rapid-Recovery
        properties.putIfAbsent("socketTimeout",
                String.valueOf(TimeUnit.SECONDS.toMillis(30)));
    }

    protected void setProperties(HikariConfig config, Map<String, String> properties) {
        for (Map.Entry<String, String> property : properties.entrySet()) {
            config.addDataSourceProperty(property.getKey(), property.getValue());
        }
    }

    protected void postInitialize() {

    }

    @Override
    public void init(ID2RPlugin plugin) {
        HikariConfig config;
        try {
            config = new HikariConfig();
        } catch (LinkageError e) {
            e.printStackTrace();
            return;
        }

        config.setPoolName("VVKs-hikari");

        String[] addressSplit = this.credentials.getAddress().split(":");
        String address = addressSplit[0];
        String port = addressSplit.length > 1 ? addressSplit[1] : defaultPort();

        try {
            configureDatabase(config, address, port, this.credentials.getDatabase(), this.credentials.getUsername(),
                    this.credentials.getPassword());
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }

        Map<String, String> properties = new HashMap<>(this.credentials.getProperties());

        overrideProperties(properties);

        setProperties(config, properties);

        config.setMaximumPoolSize(this.credentials.getMaxPoolSize());
        config.setMinimumIdle(this.credentials.getMinIdleConnections());
        config.setMaxLifetime(this.credentials.getMaxLifetime());
        config.setKeepaliveTime(this.credentials.getKeepAliveTime());
        config.setConnectionTimeout(this.credentials.getConnectionTimeout());

        config.setInitializationFailTimeout(-1);

        this.dataSource = new HikariDataSource(config);

        postInitialize();
    }

    @Override
    public void shutdown() {
        if (this.dataSource != null)
            this.dataSource.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (this.dataSource == null) {
            throw new SQLException("Unable to get a connection from the pool. (hikari is null)");
        }

        Connection connection = this.dataSource.getConnection();
        if (connection == null) {
            throw new SQLException("Unable to get a connection from the pool. (getConnection returned null)");
        }

        return connection;
    }

}
