package dev.id2r.api.common.placeholder.replacer;

import dev.id2r.api.common.placeholder.Placeholder;

import java.util.function.Function;

public interface Replacer {

    String apply(String text, Function<String, Placeholder> lookup);

}
