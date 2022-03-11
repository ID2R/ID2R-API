package dev.id2r.api.common.dependency.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public class IsolatedClassLoader extends URLClassLoader {
    static {
        ClassLoader.registerAsParallelCapable();
    }

    /**
     * Creates a new isolated class loader for the given URLs.
     *
     * @param urls the URLs to add to the classpath
     */
    public IsolatedClassLoader(URL... urls) {
        super(requireNonNull(urls, "urls"), ClassLoader.getSystemClassLoader().getParent());
    }

    /**
     * Adds a URL to the classpath.
     *
     * @param url the URL to add
     */
    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    /**
     * Adds a path to the classpath.
     *
     * @param path the path to add
     */
    public void addPath(Path path) {
        try {
            addURL(requireNonNull(path, "path").toUri().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}