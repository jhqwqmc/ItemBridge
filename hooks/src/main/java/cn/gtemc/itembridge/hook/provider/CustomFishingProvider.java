package cn.gtemc.itembridge.hook.provider;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.context.ContextKey;
import net.momirealms.customfishing.api.BukkitCustomFishingPlugin;
import net.momirealms.customfishing.api.mechanic.context.Context;
import net.momirealms.customfishing.api.mechanic.context.ContextKeys;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

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
        return Optional.ofNullable(BukkitCustomFishingPlugin.getInstance().getItemManager().buildInternal(adapt(ctx, context), id));
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        Context<Player> ctx = Context.player(player).arg(ContextKeys.ID, id);
        return BukkitCustomFishingPlugin.getInstance().getItemManager().buildInternal(adapt(ctx, context), id);
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        return Optional.ofNullable(BukkitCustomFishingPlugin.getInstance().getItemManager().getCustomFishingItemID(item));
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        return BukkitCustomFishingPlugin.getInstance().getItemManager().getCustomFishingItemID(item);
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return BukkitCustomFishingPlugin.getInstance().getItemManager().getCustomFishingItemID(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return BukkitCustomFishingPlugin.getInstance().getItemManager().getItemIDs().contains(id);
    }

    private static Context<Player> adapt(Context<Player> playerContext, BuildContext context) {
        for (Map.Entry<cn.gtemc.itembridge.api.context.ContextKey<?>, Supplier<Object>> entry : context.contextData().entrySet()) {
            ContextKey<?> key = entry.getKey();
            arg(playerContext, ContextKeys.of(key.key(), key.type()), entry.getValue().get());
        }
        return playerContext;
    }

    @SuppressWarnings("unchecked")
    private static <T, C> void arg(Context<Player> builder, ContextKeys<T> key, C value) {
        builder.arg(key, (T) value);
    }
}
