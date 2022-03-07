package dev.id2r.api.common.storage.implementation.sql.connection.hikari;

import com.zaxxer.hikari.HikariConfig;
import dev.id2r.api.common.storage.misc.StorageCredentials;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;

public class MySQLConnectionFactory extends HikariConnectionFactory {

    public MySQLConnectionFactory(StorageCredentials credentials) {
        super(credentials);
    }

    @Override
    public String getStorageName() {
        return "MySQL";
    }

    @Override
    protected String defaultPort() {
        return "3306";
    }

    @Override
    protected void configureDatabase(HikariConfig config, String address, String port, String databaseName, String username, String password) {
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://" + address + ":" + port + "/" + databaseName);
        config.setUsername(username);
        config.setPassword(password);
    }

    @Override
    protected void postInitialize() {
        super.postInitialize();

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getName().equals("com.mysql.cj.jdbc.Driver")) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    @Override
    protected void overrideProperties(Map<String, String> properties) {
        // https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
        properties.putIfAbsent("cachePrepStmts", "true");
        properties.putIfAbsent("prepStmtCacheSize", "250");
        properties.putIfAbsent("prepStmtCacheSqlLimit", "2048");
        properties.putIfAbsent("useServerPrepStmts", "true");
        properties.putIfAbsent("useLocalSessionState", "true");
        properties.putIfAbsent("rewriteBatchedStatements", "true");
        properties.putIfAbsent("cacheResultSetMetadata", "true");
        properties.putIfAbsent("cacheServerConfiguration", "true");
        properties.putIfAbsent("elideSetAutoCommits", "true");
        properties.putIfAbsent("maintainTimeStats", "false");
        properties.putIfAbsent("alwaysSendSetIsolation", "false");
        properties.putIfAbsent("cacheCallableStmts", "true");

        // https://stackoverflow.com/a/54256150
        // It's not super important which timezone we pick, because we don't use time-based
        // data types in any of our schemas/queries.
        properties.putIfAbsent("serverTimezone", "UTC");

        super.overrideProperties(properties);
    }

}
