package dev.id2r.api.spigot.items;

import dev.id2r.api.spigot.utils.SpigotUtils;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class VKItemBuilder implements Cloneable {

    private ItemStack stack;

    public VKItemBuilder(@NonNull ItemStack stack) {
        this.stack = stack;
    }

    public VKItemBuilder itemStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    public VKItemBuilder setMeta(ItemMeta meta) {
        this.stack.setItemMeta(meta);
        return this;
    }

    public VKItemBuilder addLore(Collection<String> lore) {
        return metaConsumer(meta -> {
            final List<String> list = (meta.hasLore()) ? meta.getLore() : new ArrayList<>();

            for (String s: lore)
                list.add(SpigotUtils.color(s));

            meta.setLore(list);
        });
    }

    public VKItemBuilder setLore(Collection<String> lore) {
        return metaConsumer(meta -> {
            final List<String> list = new ArrayList<>();

            for (String s: lore)
                list.add(SpigotUtils.color(s));

            meta.setLore(list);
        });
    }

    public VKItemBuilder setLore(int index, String text) {
        return metaConsumer(meta -> {
            if (!meta.hasLore()) {
                lore(text);
                return;
            }
            final List<String> list = meta.getLore();

            if ((index < 0 || index >= list.size())) {
                list.add(list.size() - 1, SpigotUtils.color(text));
            } else {
                list.set(index, SpigotUtils.color(text));
            }
            meta.setLore(list);
        });
    }

    public VKItemBuilder deleteLore(int index) {
        return metaConsumer(meta -> {
            if (!meta.hasLore())
                return;

            final List<String> list = meta.getLore();
            if ((index < 0 || index >= list.size()))
                return;

            list.remove(index);
            meta.setLore(list);
        });
    }

    public VKItemBuilder displayName(String text) {
        return metaConsumer(meta -> meta.setDisplayName(SpigotUtils.color(text)));
    }

    public VKItemBuilder lore(String... lore) {
        return this.setLore(Arrays.asList(lore));
    }

    public VKItemBuilder addLore(String... lore) {
        return this.addLore(Arrays.asList(lore));
    }

    public VKItemBuilder addFlag(ItemFlag... flags) {
        return metaConsumer(meta -> meta.addItemFlags(flags));
    }

    public VKItemBuilder deleteFlag(ItemFlag... flags) {
        return metaConsumer(meta -> meta.removeItemFlags(flags));
    }

    public VKItemBuilder clearLore() {
        return metaConsumer(meta -> meta.setLore(new ArrayList<>()));
    }

    public VKItemBuilder clearFlags() {
        return metaConsumer(meta -> meta.removeItemFlags(ItemFlag.values()));
    }

    public ItemStack build() {
        return this.stack;
    }

    @Override
    public VKItemBuilder clone() {
        try {
            VKItemBuilder clone = (VKItemBuilder) super.clone();
            return clone.itemStack(this.stack);
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private VKItemBuilder metaConsumer(Consumer<ItemMeta> consumer) {
        final ItemMeta meta = this.stack.getItemMeta();
        consumer.accept(meta);
        return setMeta(meta);
    }
}
