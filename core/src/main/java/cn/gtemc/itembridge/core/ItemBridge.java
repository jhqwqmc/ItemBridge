package cn.gtemc.itembridge.core;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.hook.context.ItemContextKeys;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

public interface ItemBridge {

    static Builder builder() {
        return new ItemBridgeImpl.BuilderImpl();
    }

    Optional<Provider<ItemStack>> provider(@NotNull String plugin);

    Collection<Provider<ItemStack>> providers();

    default Optional<ItemStack> build(@NotNull String plugin, @NotNull String id) {
        return build(plugin, id, BuildContext.empty());
    }

    default Optional<ItemStack> build(@NotNull String plugin, @NotNull String id, @Nullable Player player) {
        return build(plugin, id, BuildContext.builder().withOptional(ItemContextKeys.PLAYER, player).build());
    }

    Optional<ItemStack> build(@NotNull String plugin, @NotNull String id, @NotNull BuildContext context);

    Optional<String> id(@NotNull String plugin, @NotNull ItemStack item);

    boolean is(@NotNull String plugin, @NotNull ItemStack item);

    boolean has(@NotNull String plugin, @NotNull String id);

    boolean hasProvider(@NotNull String plugin);

    interface Builder {

        Builder register(@NotNull Provider<ItemStack> provider);

        Provider<ItemStack> removeById(@NotNull String id);

        ItemBridge build();
    }
}
