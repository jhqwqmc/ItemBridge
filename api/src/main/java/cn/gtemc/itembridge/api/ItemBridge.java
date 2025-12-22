package cn.gtemc.itembridge.api;

import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * ItemBridge is a unified item bridging interface used to build and identify items across different plugins.
 * <p>
 * It supports item operations for various plugins by registering {@link Provider}.
 */
public interface ItemBridge<I, P> {

    /**
     * Retrieves the corresponding {@link Provider} for the specified plugin.
     *
     * @param plugin The lower-case name of the plugin.
     * @return An {@code Optional} containing the corresponding {@link Provider}, or {@code Optional.empty()} if it does not exist.
     */
    Optional<Provider<I, P>> provider(@NotNull String plugin);

    /**
     * Retrieves a collection of all registered {@link Provider}.
     *
     * @return An unmodifiable collection of all registered {@link Provider}.
     */
    Collection<Provider<I, P>> providers();

    /**
     * Builds an {@link I} based on the specified plugin and item ID using an empty context and a null player.
     *
     * @param plugin The lower-case name of the plugin.
     * @param id     The item ID.
     * @return An {@code Optional} containing the successfully built {@link I}, or {@code Optional.empty()} if building fails or the {@link Provider} does not exist.
     */
    default Optional<I> build(@NotNull String plugin, @NotNull String id) {
        return build(plugin, id, null, BuildContext.empty());
    }

    /**
     * Builds an {@link I} based on the specified plugin and item ID using an empty context.
     *
     * @param plugin The lower-case name of the plugin.
     * @param id     The item ID.
     * @param player  An optional {@link P} representing the player.
     * @return An {@code Optional} containing the successfully built {@link I}, or {@code Optional.empty()} if building fails or the {@link Provider} does not exist.
     */
    default Optional<I> build(@NotNull String plugin, @Nullable P player, @NotNull String id) {
        return build(plugin, id, player, BuildContext.empty());
    }

    /**
     * Builds an {@link I} based on the specified plugin, item ID, and {@link BuildContext}.
     *
     * @param plugin  The lower-case name of the plugin.
     * @param id      The item ID.
     * @param player  An optional {@link P} representing the player.
     * @param context The build context {@link BuildContext}.
     * @return An {@code Optional} containing the successfully built {@link I}, or {@code Optional.empty()} if building fails or the {@link Provider} does not exist.
     */
    Optional<I> build(@NotNull String plugin, @NotNull String id, @Nullable P player, @NotNull BuildContext context);

    /**
     * Builds an {@link I} based on the specified plugin, item ID, and {@link BuildContext}.
     *
     * @param plugin  The lower-case name of the plugin.
     * @param id      The item ID.
     * @param player  An optional {@link P} representing the player.
     * @param context The build context {@link BuildContext}.
     * @return The built item object, or null if the ID is invalid or building fails.
     */
    @Nullable I buildOrNull(@NotNull String plugin, @NotNull String id, @Nullable P player, @NotNull BuildContext context);

    /**
     * Retrieves the ID of a given {@link I} within the specified plugin.
     *
     * @param plugin The lower-case name of the plugin.
     * @param item   The item object {@link I}.
     * @return An {@code Optional} containing the item ID if the item belongs to the specified plugin or its Provider exists, otherwise {@code Optional.empty()}.
     */
    Optional<String> id(@NotNull String plugin, @NotNull I item);

    /**
     * Retrieves the ID of a given {@link I} within the specified plugin.
     *
     * @param plugin The lower-case name of the plugin.
     * @param item   The item object {@link I}.
     * @return The ID if the item belongs to this provider, otherwise null.
     */
    @Nullable String idOrNull(@NotNull String plugin, @NotNull I item);

    /**
     * Checks if the given {@link I} is an item provided by the specified plugin.
     *
     * @param plugin The lower-case name of the plugin.
     * @param item   The item object {@link I}.
     * @return {@code true} if it belongs to the specified plugin, {@code false} otherwise.
     */
    boolean is(@NotNull String plugin, @NotNull I item);

    /**
     * Checks if the specified plugin contains the given item ID.
     *
     * @param plugin The lower-case name of the plugin.
     * @param id     The item ID.
     * @return {@code true} if the ID exists, {@code false} otherwise.
     */
    boolean has(@NotNull String plugin, @NotNull String id);

    /**
     * Checks if a {@link Provider} has been registered for the specified plugin.
     *
     * @param plugin The lower-case name of the plugin.
     * @return {@code true} if registered, {@code false} otherwise.
     */
    boolean hasProvider(@NotNull String plugin);

    /**
     * Attempts to build an item by iterating through all registered providers.
     * <p>
     * Returns the result from the first provider that successfully creates the item.
     *
     * @param id      The item ID.
     * @param player  An optional {@link P} representing the player.
     * @param context The build context {@link BuildContext}.
     * @return The built item {@link I}, or {@code null} if no provider could build the ID.
     */
    @Nullable I getFirstBuild(@NotNull String id, @Nullable P player, @NotNull BuildContext context);

    /**
     * Retrieves both the plugin name and the item ID from the first provider that recognizes the given item.
     * <p>
     * This is useful for identifying the specific source plugin and the internal ID of an unknown item
     * in a single lookup.
     *
     * @param item The item object {@link I}.
     * @return A {@link Pair} where the first element is the plugin name and the second element
     * is the item ID, or {@code null} if no registered provider recognizes the item.
     */
    @Nullable Pair<String, String> getFirstId(@NotNull I item);

    /**
     * Gets all IDs associated with the given item across all registered providers.
     * <p>
     * This returns a complete mapping of every plugin that recognizes the item.
     *
     * @param item The item object {@link I}.
     * @return A map where keys are plugin names and values are the corresponding item IDs.
     * Returns an empty map if no providers recognize the item.
     */
    Map<String, String> getIds(@NotNull I item);

    /**
     * Determines whether the ItemBridge is immutable.
     *
     * @return Whether the ItemBridge is immutable.
     */
    boolean immutable();

    /**
     * Registers a {@link Provider} into the ItemBridge.
     *
     * @param provider The item provider to register.
     * @return The current instance, supporting method chaining.
     */
    ItemBridge<I, P> register(@NotNull Provider<I, P> provider);

    /**
     * Removes a registered provider based on the plugin name.
     *
     * @param id The lower-case name of the plugin.
     * @return The current instance, supporting method chaining.
     */
    ItemBridge<I, P> removeById(@NotNull String id);

    /**
     * Interface for building and configuring {@link ItemBridge} instances.
     */
    interface Builder<I, P> {

        /**
         * Registers a {@link Provider} into the ItemBridge.
         *
         * @param provider The item provider to register.
         * @return The current builder instance, supporting method chaining.
         */
        Builder<I, P> register(@NotNull Provider<I, P> provider);

        /**
         * Removes a registered provider based on the plugin name.
         *
         * @param id The lower-case name of the plugin.
         * @return The current builder instance, supporting method chaining.
         */
        Builder<I, P> removeById(@NotNull String id);

        /**
         * Sets whether the ItemBridge is immutable.
         *
         * @param immutable Whether the ItemBridge is immutable.
         * @return The current builder instance, supporting method chaining.
         */
        Builder<I, P> immutable(boolean immutable);

        /**
         * Sets the action to perform when a plugin is successfully hooked.
         *
         * @param onSuccess onSuccess a consumer receiving the name of the hooked plugin.
         * @return The current builder instance, supporting method chaining.
         */
        Builder<I, P> onHookSuccess(Consumer<String> onSuccess);

        /**
         * Sets the action to perform when a hook attempt fails.
         *
         * @param onFailure onFailure a bi-consumer receiving the plugin name and the error cause.
         * @return The current builder instance, supporting method chaining.
         */
        Builder<I, P> onHookFailure(BiConsumer<String, Throwable> onFailure);

        /**
         * Detects and registers all supported plugins.
         *
         * @return The current builder instance, supporting method chaining.
         */
        Builder<I, P> detectSupportedPlugins();

        /**
         * Builds and returns an immutable {@link ItemBridge} instance.
         *
         * @return The completed {@link ItemBridge} instance.
         */
        ItemBridge<I, P> build();
    }
}
