package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.hook.context.ItemContextKeys;
import github.saukiya.sxitem.SXItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class SXItemProvider implements Provider<ItemStack> {
    public static final SXItemProvider INSTANCE = new SXItemProvider();

    private SXItemProvider() {}

    @Override
    public String plugin() {
        return "sxitem";
    }

    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
        Player player = context.getOrNull(ItemContextKeys.PLAYER);
        return Optional.of(SXItem.getItemManager().getItem(id, player));
    }

    @Override
    public Optional<String> id(ItemStack item) {
        return Optional.ofNullable(SXItem.getItemManager().getItemKey(item));
    }

    @Override
    public boolean is(ItemStack item) {
        return SXItem.getItemManager().getItemKey(item) != null;
    }

    @Override
    public boolean has(String id) {
        return SXItem.getItemManager().hasItem(id);
    }
}
