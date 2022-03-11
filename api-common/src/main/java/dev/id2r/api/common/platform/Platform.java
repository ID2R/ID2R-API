package dev.id2r.api.common.platform;

import java.time.Instant;

public interface Platform {

    /**
     * Represents the platform type
     * @return type
     */
    Type getType();

    /**
     * get the time when the plugin first started.
     * @return enable time
     */
    Instant getStartTime();

    /**
     * Supported platform types
     */
    enum Type {
        BUKKIT("Bukkit"),
        BUNGEECORD("BungeeCord"),
        VELOCITY("Velocity");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        /**
         * Get a readable name for the platform type.
         * @return platform name
         */
        public String getName() {
            return name;
        }
    }

}
