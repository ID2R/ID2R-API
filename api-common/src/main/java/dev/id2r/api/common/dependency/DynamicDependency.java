package dev.id2r.api.common.dependency;

import dev.id2r.api.common.dependency.relocation.Relocation;

import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static java.util.Objects.requireNonNull;

public class DynamicDependency {

    private final String artifactId, groupId, version, vendor, path, relocatedPath;
    private final Collection<Relocation> relocations;
    private final byte[] checkSum;

    private DynamicDependency(Builder builder) {
        artifactId = builder.artifactId;
        groupId = builder.groupId;
        version = builder.version;
        vendor = builder.vendor;
        checkSum = Base64.getDecoder().decode(builder.checkSum);
        relocations = !builder.relocations.isEmpty() ? Collections.unmodifiableList(new LinkedList<>(builder.relocations)) : Collections.emptyList();
        String path = this.groupId.replace('.', '/') + '/' + artifactId + '/' + version + '/' + artifactId + '-' + version;

        if (builder.classifier != null)
            path += '-' + builder.classifier;


        this.path = path + ".jar";
        relocatedPath = hasRelocations() ? path + "-relocated.jar" : null;
    }

    public String getVendor() {
        return vendor;
    }

    public boolean hasRelocations() {
        return !relocations.isEmpty();
    }

    public String getRelocatedPath() {
        return relocatedPath;
    }

    public byte[] getCheckSum() {
        return checkSum;
    }

    public String getPath() {
        return path;
    }

    public Collection<Relocation> getRelocations() {
        return relocations;
    }

    public String url() {
        return String.format("%s%s/%s/%s/%s-%s.jar",
                vendor.endsWith("/") ? vendor : vendor + "/",
                rewriteEscaping(groupId).replace(".", "/"),
                rewriteEscaping(artifactId),
                version,
                rewriteEscaping(artifactId),
                version
        );
    }

    private String rewriteEscaping(String s) {
        return s.replace("{}", ".");
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String artifactId, groupId, version, vendor, checkSum, classifier;

        private final Collection<Relocation> relocations = new LinkedList<>();

        private Builder() {
            vendor(Vendors.MAVEN);
        }

        public Builder info(String group, String artifact, String ver) {
            groupId = group;
            artifactId = artifact;
            version = ver;
            return this;
        }

        public Builder classifier(String val) {
            classifier = val;
            return this;
        }

        public Builder vendor(Vendors predefined) {
            vendor = predefined.getUrl();
            return this;
        }

        public Builder vendor(String url) {
            vendor = url;
            return this;
        }

        public Builder checkSum(String val) {
            checkSum = val;
            return this;
        }

        public Builder relocate(Relocation relocation) {
            relocations.add(requireNonNull(relocation, "relocation"));
            return this;
        }

        public Builder relocate(String pattern, String relocatedPattern) {
            return relocate(new Relocation(pattern, relocatedPattern));
        }

        public DynamicDependency create() {
            return new DynamicDependency(this);
        }
    }
}
