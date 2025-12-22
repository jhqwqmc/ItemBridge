package cn.gtemc.itembridge.core;

import cn.gtemc.itembridge.api.ItemBridge;
import cn.gtemc.itembridge.api.Provider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;

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
     * Registers a {@link Provider} into the ItemBridge.
     *
     * @param provider The item provider to register.
     * @return The current instance, supporting method chaining.
     */
    BukkitItemBridge register(@NotNull Provider<ItemStack, Player> provider);

    /**
     * Removes a registered provider based on the plugin name.
     *
     * @param id The lower-case name of the plugin.
     * @return The current instance, supporting method chaining.
     */
    BukkitItemBridge removeById(@NotNull String id);

    /**
     * Interface for building and configuring {@link BukkitItemBridge} instances.
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
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder removeById(@NotNull String id);

        /**
         * Sets whether the ItemBridge is immutable.
         *
         * @param immutable Whether the ItemBridge is immutable.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder immutable(boolean immutable);

        /**
         * Sets the action to perform when a plugin is successfully hooked.
         *
         * @param onSuccess onSuccess a consumer receiving the name of the hooked plugin.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder onHookSuccess(Consumer<String> onSuccess);

        /**
         * Sets the action to perform when a hook attempt fails.
         *
         * @param onFailure onFailure a bi-consumer receiving the plugin name and the error cause.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder onHookFailure(BiConsumer<String, Throwable> onFailure);

        /**
         * Detects and registers all supported plugins.
         *
         * @return The current builder instance, supporting method chaining.
         */
        Builder<ItemStack, Player> detectSupportedPlugins();

        /**
         * Detects and registers all supported plugins.
         *
         * @param predicate The predicate to filter plugins.
         * @return The current builder instance, supporting method chaining.
         */
        BukkitBuilder detectSupportedPlugins(@NotNull Predicate<Plugin> predicate);

        /**
         * Builds and returns an immutable {@link BukkitItemBridge} instance.
         *
         * @return The completed {@link BukkitItemBridge} instance.
         */
        BukkitItemBridge build();
    }
}
