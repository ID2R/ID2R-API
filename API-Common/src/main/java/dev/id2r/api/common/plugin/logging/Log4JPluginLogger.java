package dev.id2r.api.common.plugin.logging;

import org.slf4j.Logger;

public class Log4JPluginLogger implements PluginLogger {

    private final Logger logger;

    public Log4JPluginLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String s) {
        this.logger.info(s);
    }

    @Override
    public void warn(String s) {
        this.logger.warn(s);
    }

    @Override
    public void warn(String s, Throwable t) {
        this.logger.warn(s, t);
    }

    @Override
    public void error(String s) {
        this.logger.error(s);
    }

    @Override
    public void error(String s, Throwable t) {
        this.logger.error(s, t);
    }

}
