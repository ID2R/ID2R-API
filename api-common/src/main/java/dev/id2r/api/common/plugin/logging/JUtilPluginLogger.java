package dev.id2r.api.common.plugin.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JUtilPluginLogger implements PluginLogger {

    private final Logger logger;

    public JUtilPluginLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String s) {
        this.logger.info(s);
    }

    @Override
    public void warn(String s) {
        this.logger.warning(s);
    }

    @Override
    public void warn(String s, Throwable t) {
        this.logger.log(Level.WARNING, s, t);
    }

    @Override
    public void error(String s) {
        this.logger.severe(s);
    }

    @Override
    public void error(String s, Throwable t) {
        this.logger.log(Level.SEVERE, s, t);
    }
}
