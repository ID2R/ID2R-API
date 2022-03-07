package dev.id2r.api.common.placeholder;

import dev.id2r.api.common.placeholder.replacer.CharsReplacer;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderManager {

    private final Map<String, Placeholder> replacementMap = new HashMap<>();

    public Map<String, Placeholder> getReplacementMap() {
        return replacementMap;
    }

    @Nullable
    public Placeholder getReplacement(String identifier) {
        return replacementMap.get(identifier);
    }

    public String replace(String text) {
        return new CharsReplacer().apply(text, this::getReplacement);
    }

}
