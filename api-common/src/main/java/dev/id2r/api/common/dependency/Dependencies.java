package dev.id2r.api.common.dependency;

public class Dependencies {

    public static final DynamicDependency.Builder PROTOBUF_JAVA = DynamicDependency.newBuilder().
            info("com{}google{}protobuf", "protobuf-java", "3.11.4")
            .checkSum("QumPWPU9Gkn9c0wt0ZOIDy3+w0NqKZOgDQa4gAoio/I=");

    public static final DynamicDependency.Builder HIKARICP = DynamicDependency.newBuilder()
            .info("com{}zaxxer", "HikariCP", "4.0.3")
            .checkSum("fAJK7/HBBjV210RTUT+d5kR9jmJNF/jifzCi6XaIxsk=");

    public static final DynamicDependency.Builder MONGODB_DRIVER = DynamicDependency.newBuilder()
            .info("org{}mongodb", "mongodb-driver-sync", "4.4.0")
            .checkSum("9ojeQLr06NVwiAdj7kuOM+jvtY2h1uS3KZ/dn9joM3o=");

    public static final DynamicDependency.Builder H2_DRIVER = DynamicDependency.newBuilder()
            .info("com{}h2database", "h2", "1.4.199")
            .checkSum("MSWhZ0O8a0z7thq7p4MgPx+2gjCqD9yXiY95b5ml1C4=");

    public static final DynamicDependency.Builder SQLITE_DRIVER = DynamicDependency.newBuilder()
            .info("org{}xerial", "sqlite-jdbc", "3.28.0")
            .checkSum("k3hOVtv1RiXgbJks+D9w6cG93Vxq0dPwEwjIex2WG2A=");

    public static final DynamicDependency.Builder MYSQL_DRIVER = DynamicDependency.newBuilder()
            .info("mysql", "mysql-connector-java", "8.0.23")
            .checkSum("/31bQCr9OcEnh0cVBaM6MEEDsjjsG3pE6JNtMynadTU=");

    public static final DynamicDependency.Builder CONFIGME = DynamicDependency.newBuilder()
            .info("ch{}jalu", "configme", "1.3.0")
            .checkSum("5QQvcFmm0R5WlGZERM1FJjMLRnLxYiiS5jhknTYPgqc=");

    public static final DynamicDependency.Builder JEDIS = DynamicDependency.newBuilder()
            .info("redis{}clients", "jedis", "2.8.0")
            .checkSum("6BlLQsvb6ZD2zB7HqRprVT2QGrrxVd1sxZkTnrP11o0=");

    public static final DynamicDependency.Builder GSON = DynamicDependency.newBuilder()
            .info("com{}google{}code{}gson", "gson", "2.9.0")
            .checkSum("yW1gVRMxoZbaxUt0WqZCzQeO+JtvJnFGtwXywsvvBS0=");

    public static final DynamicDependency.Builder AGRONA = DynamicDependency.newBuilder()
            .info("org{}agrona", "agrona", "1.14.0")
            .checkSum("oHANWp/B7y3B7PQyKCiQg53dyYLY8T6OpL/66OdWZI4=");

    public static final DynamicDependency.Builder SLF4J = DynamicDependency.newBuilder()
            .info("org{}slf4j", "slf4j-api", "1.7.30")
            .checkSum("zboHlk0btAoHYUhcax6ML4/Z6x0ZxTkorA1/lRAQXFc=");

    public static final DynamicDependency.Builder YAML = DynamicDependency.newBuilder()
            .info("org{}yaml", "snakeyaml", "1.28")
            .checkSum("NURqFCFDXUXkxqwN47U3hSfVzCRGwHGD4kRHcwzh//o=");

}
