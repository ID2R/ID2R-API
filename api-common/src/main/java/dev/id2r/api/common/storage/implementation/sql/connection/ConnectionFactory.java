package dev.id2r.api.common.storage.implementation.sql.connection;

import dev.id2r.api.common.plugin.ID2RPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

    String getStorageName();

    void init(ID2RPlugin plugin);

    void shutdown() throws Exception;

    Connection getConnection() throws SQLException;

}
