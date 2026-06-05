package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import emanondev.itemedit.ItemEdit;
import emanondev.itemedit.storage.ServerStorage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

final class ItemEditProvider implements Provider<ItemStack, Player> {
    public static final ItemEditProvider INSTANCE = new ItemEditProvider();

    private ItemEditProvider() {}

    @Override
    public String plugin() {
        return "itemedit";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Optional.ofNullable(ItemEdit.get().getServerStorage().getItem(id));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        return ItemEdit.get().getServerStorage().getItem(id);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        ServerStorage storage = ItemEdit.get().getServerStorage();
        for (String key : storage.getIds()) {
            if (item.equals(storage.getItem(key))) return Optional.of(key);
        }
        return Optional.empty();
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        ServerStorage storage = ItemEdit.get().getServerStorage();
        for (String key : storage.getIds()) {
            if (item.equals(storage.getItem(key))) return key;
        }
        return null;
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        ServerStorage storage = ItemEdit.get().getServerStorage();
        for (String key : storage.getIds()) {
            if (item.equals(storage.getItem(key))) return true;
        }
        return false;
    }

    @Override
    public boolean has(@NotNull String id) {
        return ItemEdit.get().getServerStorage().getItem(id) != null;
    }

    static boolean conflictCheck(Plugin plugin) {
        return Utils.classExists("emanondev{}itemedit{}ItemEdit");
    }
}
