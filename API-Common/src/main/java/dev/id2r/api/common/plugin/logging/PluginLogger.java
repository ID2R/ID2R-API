package dev.id2r.api.common.plugin.logging;

public interface PluginLogger {

    void info(String s);

    void warn(String s);

    void warn(String s, Throwable t);

    void error(String s);

    void error(String s, Throwable t);

}
