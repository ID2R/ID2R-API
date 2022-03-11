package dev.id2r.api.bungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BungeeUtils {

    private BungeeUtils() {}

    public static String color(@NonNull String text, char character) {
        return ChatColor.translateAlternateColorCodes(character, text);
    }

    public static String color(@NonNull String text) {
        return color(text, '&');
    }

    public static boolean isConsole(@NonNull CommandSender sender) {
        return !(sender instanceof ProxiedPlayer);
    }

    public static ProxiedPlayer toPlayer(@NonNull CommandSender sender) {
        return (ProxiedPlayer) sender;
    }

}
