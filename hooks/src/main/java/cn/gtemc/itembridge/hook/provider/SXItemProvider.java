package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import github.saukiya.sxitem.SXItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SXItemProvider implements Provider<ItemStack, Player> {
    public static final SXItemProvider INSTANCE = new SXItemProvider();

    private SXItemProvider() {}

    @Override
    public String plugin() {
        return "sxitem";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        return Optional.of(SXItem.getItemManager().getItem(id, player));
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(SXItem.getItemManager().getItemKey(item));
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return SXItem.getItemManager().getItemKey(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return SXItem.getItemManager().hasItem(id);
    }
}
