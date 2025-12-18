package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.util.MiscUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.neige.neigeitems.manager.ItemManager;

import java.util.Optional;

public class NeigeItemsProvider implements Provider<ItemStack, Player> {
    public static final NeigeItemsProvider INSTANCE = new NeigeItemsProvider();

    private NeigeItemsProvider() {}

    @Override
    public String plugin() {
        return "neigeitems";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Optional.ofNullable(ItemManager.INSTANCE.getItemStack(id, player, MiscUtils.adaptString2String(context)));
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        return ItemManager.INSTANCE.getItemStack(id, player, MiscUtils.adaptString2String(context));
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(ItemManager.INSTANCE.getItemId(item));
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return ItemManager.INSTANCE.getItemId(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return ItemManager.INSTANCE.isNiItem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return ItemManager.INSTANCE.hasItem(id);
    }
}
