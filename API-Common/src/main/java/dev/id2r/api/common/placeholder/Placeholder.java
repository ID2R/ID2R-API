package dev.id2r.api.common.placeholder;

public abstract class Placeholder {

    private final String identifier;

    public Placeholder(String identifier) {
        this.identifier = identifier;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public final void register(PlaceholderManager manager) {
        manager.getReplacementMap().putIfAbsent(getIdentifier(), this);
    }

    public abstract String onRequest(String parameter);

}
