package dev.id2r.api.spigot.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Set;

public class SpigotUtils {

    private SpigotUtils() {}

    public static String color(@NonNull String text, char character) {
        return ChatColor.translateAlternateColorCodes(character, text);
    }

    public static String color(@NonNull String text) {
        return color(text, '&');
    }

    public static boolean isConsole(@NonNull CommandSender sender) {
        return !(sender instanceof Player);
    }

    public static Player toPlayer(@NonNull CommandSender sender) {
        return (Player) sender;
    }

    public static Block getTargetBlock(@NonNull Player player, Set<Material> transparent, int range) {
        return player.getTargetBlock(transparent, range);
    }

    public static Block getTargetBlock(@NonNull Player player, int range) {
        return getTargetBlock(player, null, range);
    }

}
