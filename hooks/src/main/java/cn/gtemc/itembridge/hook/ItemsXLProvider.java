package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import de.erethon.caliburn.CaliburnAPI;
import de.erethon.caliburn.item.ExItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

final class ItemsXLProvider implements Provider<ItemStack, Player> {
    public static final ItemsXLProvider INSTANCE = new ItemsXLProvider();

    private ItemsXLProvider() {}

    @Override
    public String plugin() {
        return "itemsxl";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        ExItem item = CaliburnAPI.getInstance().getExItem(id);
        if (item == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(item.toItemStack());
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        ExItem item = CaliburnAPI.getInstance().getExItem(id);
        if (item == null) {
            return null;
        }
        return item.toItemStack();
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        ExItem exItem = CaliburnAPI.getInstance().getExItem(item);
        if (exItem == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(exItem.getId());
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        ExItem exItem = CaliburnAPI.getInstance().getExItem(item);
        if (exItem == null) {
            return null;
        }
        return exItem.getId();
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return CaliburnAPI.getInstance().getExItem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return CaliburnAPI.getInstance().getExItem(id) != null;
    }

    final static class Check {
        static boolean conflictCheck(Plugin plugin) {
            return Utils.classExists("de{}erethon{}caliburn{}CaliburnAPI");
        }
    }
}
