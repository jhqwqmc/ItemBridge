package cn.gtemc.itembridge.core;

import cn.gtemc.itembridge.api.ItemBridge;
import cn.gtemc.itembridge.api.Provider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * BukkitItemBridge is a unified item bridging interface used to build and identify items across different plugins.
 * <p>
 * It supports item operations for various plugins by registering {@link Provider}.
 */
public interface BukkitItemBridge extends ItemBridge<ItemStack, Player> {

    /**
     * Retrieves a {@code BukkitBuilder} used to construct and configure an {@code BukkitItemBridge} instance.
     *
     * @return An {@code BukkitItemBridge} {@code BukkitBuilder} instance.
     */
    static BukkitBuilder builder() {
        return new BukkitItemBridgeImpl.BukkitBuilderImpl();
    }

    /**
     * Interface for building and configuring {@link BukkitItemBridge} instances.
     * <p>
     * All available built-in item providers are automatically loaded upon creation.
     * Custom providers can be registered or existing ones removed through this builder.
     */
    interface BukkitBuilder extends Builder<ItemStack, Player> {

        /**
         * Registers a {@link Provider} into the ItemBridge.
         *
         * @param provider The item provider to register.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder register(@NotNull Provider<ItemStack, Player> provider);

        /**
         * Removes a registered provider based on the plugin name.
         *
         * @param id The lower-case name of the plugin.
         * @return The removed provider, or null if it did not exist.
         */
        @Nullable Provider<ItemStack, Player> removeById(@NotNull String id);

        /**
         * Builds and returns an immutable {@link BukkitItemBridge} instance.
         *
         * @return The completed {@link BukkitItemBridge} instance.
         */
        BukkitItemBridge build();
    }
}
