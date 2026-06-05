package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import net.advancedplugins.items.Core;
import net.advancedplugins.items.objects.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

final class AdvancedItemsProvider implements Provider<ItemStack, Player> {
    public static final AdvancedItemsProvider INSTANCE = new AdvancedItemsProvider();

    private AdvancedItemsProvider() {}

    @Override
    public String plugin() {
        return "advanceditems";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        CustomItem customItem = Core.getItemsHandler().getCustomItem(id);
        if (customItem == null) {
            return Optional.empty();
        }
        ItemStack[] item = customItem.getItem(player, 1);
        if (item == null || item.length == 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(item[0]);
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        CustomItem customItem = Core.getItemsHandler().getCustomItem(id);
        if (customItem == null) {
            return null;
        }
        ItemStack[] item = customItem.getItem(player, 1);
        if (item == null || item.length == 0) {
            return null;
        }
        return item[0];
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        CustomItem customItem = Core.getItemsHandler().getCustomItem(item);
        if (customItem == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(customItem.getItemName());
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        CustomItem customItem = Core.getItemsHandler().getCustomItem(item);
        if (customItem == null) {
            return null;
        }
        return customItem.getItemName();
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return Core.getItemsHandler().getCustomItem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return Core.getItemsHandler().getCustomItem(id) != null;
    }

    final static class Check {
        static boolean conflictCheck(Plugin plugin) {
            return Utils.classExists("net{}advancedplugins{}items{}Core");
        }
    }
}
