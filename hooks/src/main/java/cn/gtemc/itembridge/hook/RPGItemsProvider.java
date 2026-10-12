package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;

import java.util.Optional;

final class RPGItemsProvider implements Provider<ItemStack, Player> {
    public static final RPGItemsProvider INSTANCE = new RPGItemsProvider();

    private RPGItemsProvider() {}


    @Override
    public String plugin() {
        return "rpgitems";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return ItemManager.getItem(id).map(RPGItem::toItemStack);
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        RPGItem rpgItem = ItemManager.getItem(id).orElse(null);
        return rpgItem == null ? null : rpgItem.toItemStack();
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return ItemManager.toRPGItem(item).map(RPGItem::getName);
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        RPGItem rpgItem = ItemManager.toRPGItem(item).orElse(null);
        return rpgItem == null ? null : rpgItem.getName();
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return ItemManager.toRPGItem(item).isPresent();
    }

    @Override
    public boolean has(@NotNull String id) {
        return ItemManager.hasName(id);
    }

    final static class Check {
        static boolean conflictCheck(Plugin plugin) {
            return Utils.classExists("think{}rpgitems{}item{}ItemManager");
        }
    }
}
