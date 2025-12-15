package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.hook.context.ItemContextKeys;
import net.momirealms.customfishing.api.BukkitCustomFishingPlugin;
import net.momirealms.customfishing.api.mechanic.context.Context;
import net.momirealms.customfishing.api.mechanic.context.ContextKeys;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class CustomFishingProvider implements Provider<ItemStack> {
    public static final CustomFishingProvider INSTANCE = new CustomFishingProvider();

    private CustomFishingProvider() {}


    @Override
    public String plugin() {
        return "customfishing";
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public Optional<ItemStack> build(String id, BuildContext context) {
        Context<Player> ctx = Context.player(context.getOrNull(ItemContextKeys.PLAYER)).arg(ContextKeys.ID, id);
        return Optional.ofNullable(BukkitCustomFishingPlugin.getInstance().getItemManager().buildInternal(ctx, id));
    }

    @Override
    public Optional<String> id(ItemStack item) {
        return Optional.ofNullable(BukkitCustomFishingPlugin.getInstance().getItemManager().getCustomFishingItemID(item));
    }

    @Override
    public boolean is(ItemStack item) {
        return BukkitCustomFishingPlugin.getInstance().getItemManager().getCustomFishingItemID(item) != null;
    }

    @Override
    public boolean has(String id) {
        return BukkitCustomFishingPlugin.getInstance().getItemManager().getItemIDs().contains(id);
    }
}
