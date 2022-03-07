package dev.id2r.api.common.dependency.relocation;

import dev.id2r.api.common.dependency.classloader.IsolatedClassLoader;
import dev.id2r.api.common.dependency.DependencyManager;
import dev.id2r.api.common.dependency.DynamicDependency;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * A reflection-based helper for relocating library jars. It automatically
 * downloads and invokes Luck's Jar Relocator to perform jar relocations.
 *
 * @author Lucko, Byteflux
 * @see <a href="https://github.com/lucko/jar-relocator">Luck's Jar Relocator</a>
 */
public class RelocationHelper {


    /**
     * Reflected constructor for creating new jar relocator instances
     */
    private static final String JAR_RELOCATOR_CLASS = "me.lucko.jarrelocator.JarRelocator";
    private static final String JAR_RELOCATOR_RUN_METHOD = "run";

    private final Constructor<?> jarRelocatorConstructor;
    private final Method jarRelocatorRunMethod;

    public RelocationHelper(DependencyManager manager) {
        IsolatedClassLoader classLoader = new IsolatedClassLoader();

        // ObjectWeb ASM Commons
        classLoader.addPath(manager.downloadDependency(
                DynamicDependency.newBuilder()
                        .info("org.ow2.asm", "asm-commons", "9.1")
                        .checkSum("r8sm3B/BLAxKma2mcJCN2C4Y38SIyvXuklRplrRwwAw=")
                        .create()
        ));

        // ObjectWeb ASM
        classLoader.addPath(manager.downloadDependency(
                DynamicDependency.newBuilder()
                        .info("org.ow2.asm", "asm", "9.1")
                        .checkSum("zaTeRV+rSP8Ly3xItGOUR9TehZp6/DCglKmG8JNr66I=")
                        .create()
        ));

        // Luck's Jar Relocator
        classLoader.addPath(manager.downloadDependency(
                DynamicDependency.newBuilder()
                        .info("me.lucko", "jar-relocator", "1.4")
                        .checkSum("1RsiF3BiVztjlfTA+svDCuoDSGFuSpTZYHvUK8yBx8I=")
                        .create()
        ));

        try {
            Class<?> jarRelocatorClass = classLoader.loadClass(JAR_RELOCATOR_CLASS);
            this.jarRelocatorConstructor = jarRelocatorClass.getDeclaredConstructor(File.class, File.class, Map.class);
            this.jarRelocatorConstructor.setAccessible(true);

            this.jarRelocatorRunMethod = jarRelocatorClass.getDeclaredMethod(JAR_RELOCATOR_RUN_METHOD);
            this.jarRelocatorRunMethod.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invokes the jar relocator to process the input jar and generate an
     * output jar with the provided relocation rules applied.
     *
     * @param in          input jar
     * @param out         output jar
     * @param relocations relocations to apply
     */
    public void relocate(Path in, Path out, Collection<Relocation> relocations) throws Exception {
        requireNonNull(in, "in");
        requireNonNull(out, "out");
        requireNonNull(relocations, "relocations");

        Map<String, String> mappings = new HashMap<>();
        for (Relocation relocation : relocations) {
            mappings.put(relocation.getPattern(), relocation.getRelocatedPattern());
        }

        // create and invoke a new relocator
        Object relocator = this.jarRelocatorConstructor.newInstance(in.toFile(), out.toFile(), mappings);
        this.jarRelocatorRunMethod.invoke(relocator);
    }

}
