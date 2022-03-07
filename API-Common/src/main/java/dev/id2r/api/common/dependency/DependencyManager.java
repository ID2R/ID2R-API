package dev.id2r.api.common.dependency;

import dev.id2r.api.common.dependency.relocation.Relocation;
import dev.id2r.api.common.dependency.relocation.RelocationHelper;
import dev.id2r.api.common.plugin.logging.PluginLogger;
import dev.id2r.api.common.utils.ByteUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * inspired by @lucko's dependency manager
 */
public abstract class DependencyManager {

    private final Path saveDirectory;
    private final PluginLogger logger;

    private RelocationHelper relocator;

    public DependencyManager(PluginLogger logger, Path dataDirectory) {
        this.logger = logger;
        saveDirectory = requireNonNull(dataDirectory, "dataDirectory").toAbsolutePath()
                .resolve("lib");
    }

    /**
     * Adds a file to the plugin's classpath.
     *
     * @param file the file to add
     */
    protected abstract void addToClasspath(Path file);

    public Path downloadDependency(DynamicDependency dependency) {
        final Path file = saveDirectory.resolve(requireNonNull(dependency, "library").getPath());
        if (Files.exists(file))
            return file;

        if (dependency.getVendor() == null)
            throw new RuntimeException("dependency requested to be download doesn't have a vendor.");

        if (dependency.getCheckSum() == null)
            throw new RuntimeException("Prevented dependency download because it doesn't have an original check sum to check for imposter jars");

        final String url = dependency.url();
        final byte[] checksum = dependency.getCheckSum();

        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("failed to load SHA-256 algorithm.", e);
        }

        Path out = file.resolveSibling(file.getFileName() + ".tmp");
        out.toFile().deleteOnExit();

        try {
            Files.createDirectories(file.getParent());
            byte[] bytes = downloadRaw(url);

            if (digest != null) {
                byte[] downloaded_checksum = digest.digest(bytes);
                if (!Arrays.equals(downloaded_checksum, checksum)) {
                    logger.warn("*** INVALID CHECKSUM ***");
                    logger.warn(" Dependency :  " + dependency);
                    logger.warn(" URL :  " + url);
                    logger.warn(" Expected :  " + Base64.getEncoder().encodeToString(checksum));
                    logger.warn(" Actual :  " + Base64.getEncoder().encodeToString(downloaded_checksum));
                    return null;
                }
            }

            Files.write(out, bytes);
            Files.move(out, file);

            return file;

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            try {
                Files.deleteIfExists(out);
            } catch (IOException ignored) {}
        }
    }

    private byte[] downloadRaw(String url) {
        try {
            URLConnection connection = openConnection(url);
            try (InputStream in = connection.getInputStream()) {
                byte[] bytes = ByteUtil.toByteArray(in);
                if (bytes.length == 0) {
                    throw new RuntimeException("Empty stream");
                }
                return bytes;
            }
        } catch (Exception e) {
            throw new RuntimeException("failed to download");
        }
    }

    private URLConnection openConnection(String url) throws IOException {
        URL dependencyUrl = new URL(url);
        return dependencyUrl.openConnection();
    }

    /**
     * Processes the input jar and generates an output jar with the provided
     * relocation rules applied, then returns the path to the relocated jar.
     *
     * @param in          input jar
     * @param out         output jar
     * @param relocations relocations to apply
     * @return the relocated file
     * @see RelocationHelper#relocate(Path, Path, Collection)
     */
    private Path relocate(Path in, String out, Collection<Relocation> relocations) {
        requireNonNull(in, "in");
        requireNonNull(out, "out");
        requireNonNull(relocations, "relocations");

        Path file = saveDirectory.resolve(out);
        if (Files.exists(file)) {
            return file;
        }

        Path tmpOut = file.resolveSibling(file.getFileName() + ".tmp");
        tmpOut.toFile().deleteOnExit();

        synchronized (this) {
            if (relocator == null) {
                relocator = new RelocationHelper(this);
            }
        }

        try {
            relocator.relocate(in, tmpOut, relocations);
            Files.move(tmpOut, file);

            logger.info("Relocations applied to " + saveDirectory.getParent().relativize(in));

            return file;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (Exception e) {
            throw new RuntimeException("failed to relocate jar: " + in, e);
        } finally {
            try {
                Files.deleteIfExists(tmpOut);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Loads a library jar into the plugin's classpath. If the library jar
     * doesn't exist locally, it will be downloaded.
     * <p>
     * If the provided library has any relocations, they will be applied to
     * create a relocated jar and the relocated jar will be loaded instead.
     *
     * @param dependency the dependency to load
     * @see #downloadDependency(DynamicDependency) (dependency)
     */
    public void loadLibrary(DynamicDependency dependency) {
        Path file = downloadDependency(requireNonNull(dependency, "library"));
        if (dependency.hasRelocations()) {
            file = relocate(file, dependency.getRelocatedPath(), dependency.getRelocations());
        }

        addToClasspath(file);
    }

}
