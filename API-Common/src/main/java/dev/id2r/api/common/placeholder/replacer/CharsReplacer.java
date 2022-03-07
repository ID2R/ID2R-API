package dev.id2r.api.common.placeholder.replacer;

import dev.id2r.api.common.placeholder.Placeholder;

import java.util.function.Function;

public class CharsReplacer implements Replacer {

    // https://github.com/PlaceholderAPI/PlaceholderAPI/blob/master/src/main/java/me/clip/placeholderapi/replacer/CharsReplacer.java
    @Override
    public String apply(String text, Function<String, Placeholder> lookup) {
        final char[] chars = text.toCharArray();

        final StringBuilder builder = new StringBuilder();
        final StringBuilder identifier = new StringBuilder();
        final StringBuilder parameter = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            char l = chars[i];
            if (l == '&' && ++i < chars.length) {
                final char color = chars[i];
                if ("0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(i) > -1)
                    builder.append('\u00A7').append(color);
                else
                    builder.append(l).append(color);
                continue;
            } else if (l == '%') {
                boolean detect = false;
                boolean hadSpace = false;
                boolean hasClosure = false;
                while (++i < chars.length) {
                    char n = chars[i];
                    if (n == '%') {
                        hasClosure = true;
                        break;
                    }
                    if (n == ' ' && !detect) {
                        hadSpace = true;
                        break;
                    }
                    if (n != '_') {
                        if (!detect)
                            identifier.append(n);
                        else
                            parameter.append(n);
                        continue;
                    }
                    detect = true;
                }

                if (!hasClosure) {
                    builder.append("%").append(identifier);

                    if (detect) {
                        builder.append('_').append(parameter);
                    }

                    if (hadSpace) {
                        builder.append(' ');
                    }
                } else {
                    final Placeholder placeholder = lookup.apply(identifier.toString());
                    if (placeholder != null) {
                        builder.append(placeholder.onRequest(parameter.toString()));
                    } else {
                        builder.append("%").append(identifier).append("_").append(parameter).append("%");
                    }
                }
                identifier.setLength(0);
                parameter.setLength(0);
                continue;
            }
            builder.append(l);
        }
        return builder.toString();
    }

}
