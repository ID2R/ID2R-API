package dev.id2r.api.common.utils;

public class StringUtils {

    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();

        for (int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }

    public static String translateMessage(String message) {
        return translateAlternateColorCodes('&', message);
    }

    public static String translateTime(long timeMilli) {
        final TimeUnit unit = TimeUnit.MILLISECONDS;

        final long seconds = unit.toSeconds(timeMilli) % 60;
        final long minutes = unit.toMinutes(timeMilli) % 60;
        final long hours = unit.toHours(timeMilli) % 24;
        final long days = unit.toDays(timeMilli);

        StringBuilder builder = new StringBuilder();

        if (days > 0)
            builder.append(days).append(" day(s)").append(" ");
        if (hours > 0)
            builder.append(hours).append(" hour(s)").append(" ");
        if (minutes > 0)
            builder.append(minutes).append(" minute(s)").append(" ");

        builder.append(seconds).append(" second(s)").append(" ");

        return builder.toString();
    }

}
