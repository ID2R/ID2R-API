package dev.id2r.api.common.dependency;

public enum Vendors {

    MAVEN("https://repo1.maven.org/maven2/"), JITPACK("https://jitpack.io/"),
    SONA_TYPE("https://oss.sonatype.org/content/groups/public/");

    private final String url;

    Vendors(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
