package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import net.momirealms.customfishing.api.BukkitCustomFishingPlugin;
import net.momirealms.customfishing.api.mechanic.context.Context;
import net.momirealms.customfishing.api.mechanic.context.ContextKeys;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CustomFishingProvider implements Provider<ItemStack, Player> {
    public static final CustomFishingProvider INSTANCE = new CustomFishingProvider();

    private CustomFishingProvider() {}


    @Override
    public String plugin() {
        return "customfishing";
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        Context<Player> ctx = Context.player(player).arg(ContextKeys.ID, id);
        return Optional.ofNullable(BukkitCustomFishingPlugin.getInstance().getItemManager().buildInternal(ctx, id));
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(BukkitCustomFishingPlugin.getInstance().getItemManager().getCustomFishingItemID(item));
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return BukkitCustomFishingPlugin.getInstance().getItemManager().getCustomFishingItemID(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return BukkitCustomFishingPlugin.getInstance().getItemManager().getItemIDs().contains(id);
    }
}
