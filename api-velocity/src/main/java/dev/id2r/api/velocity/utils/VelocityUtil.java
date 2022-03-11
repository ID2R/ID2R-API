package dev.id2r.api.velocity.utils;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class VelocityUtil {

    private VelocityUtil() {}

    public static TextComponent color(String text, char colorCode) {
        return LegacyComponentSerializer.builder()
                .character(colorCode).extractUrls().build().deserialize(text);
    }

    public static TextComponent color(String text) {
        return color(text, '&');
    }

    public static TextComponent hexColor(String text) {
        return LegacyComponentSerializer.builder()
                .hexColors().build().deserialize(text);
    }

    public static boolean isConsole(CommandSource source) {
        return (source instanceof ConsoleCommandSource);
    }

    public static Player toPlayer(CommandSource source) {
        return (Player) source;
    }

}
