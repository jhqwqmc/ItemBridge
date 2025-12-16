package cn.gtemc.itembridge.core;

import cn.gtemc.itembridge.api.ItemBridge;
import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.hook.context.ItemContextKeys;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;


public interface BukkitItemBridge extends ItemBridge<ItemStack> {

    /**
     * Retrieves a {@code Builder} used to construct and configure an {@code ItemBridge} instance.
     *
     * @return An {@code ItemBridge} {@code Builder} instance.
     */
    static BukkitBuilder builder() {
        return new BukkitItemBridgeImpl.BuilderImpl();
    }

    /**
     * Builds an {@link ItemStack} based on the specified plugin, item ID, and {@link Player}.
     * <p>
     * The {@link Player} will be added to the build context.
     *
     * @param plugin The lower-case name of the plugin.
     * @param id     The item ID.
     * @param player An optional {@link Player} to be used for context construction.
     * @return An {@code Optional} containing the successfully built {@link ItemStack}, or {@code Optional.empty()} if building fails or the {@link Provider} does not exist.
     */
    default Optional<ItemStack> build(@NotNull String plugin, @NotNull String id, @Nullable Player player) {
        return build(plugin, id, BuildContext.builder().withOptional(ItemContextKeys.PLAYER, player).build());
    }

    interface BukkitBuilder extends Builder<ItemStack> {

        /**
         * Registers a {@link Provider} into the ItemBridge.
         *
         * @param provider The item provider to register.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder register(@NotNull Provider<ItemStack> provider);

        /**
         * Removes a registered provider based on the plugin name.
         *
         * @param id The lower-case name of the plugin.
         * @return The removed provider, or null if it did not exist.
         */
        @Nullable Provider<ItemStack> removeById(@NotNull String id);

        /**
         * Builds and returns an immutable {@link ItemBridge} instance.
         *
         * @return The completed {@link ItemBridge} instance.
         */
        BukkitItemBridge build();
    }
}
