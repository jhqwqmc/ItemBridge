package cn.gtemc.itembridge.api;

import cn.gtemc.itembridge.api.context.BuildContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

/**
 * ItemBridge is a unified item bridging interface used to build and identify items across different plugins.
 * <p>
 * It supports item operations for various plugins by registering {@link Provider}s.
 */
public interface ItemBridge<I> {

    /**
     * Retrieves the corresponding {@link Provider} for the specified plugin.
     *
     * @param plugin The lower-case name of the plugin.
     * @return An {@code Optional} containing the corresponding {@link Provider}, or {@code Optional.empty()} if it does not exist.
     */
    Optional<Provider<I>> provider(@NotNull String plugin);

    /**
     * Retrieves a collection of all registered {@link Provider}s.
     *
     * @return An unmodifiable collection of all registered {@link Provider}s.
     */
    Collection<Provider<I>> providers();

    /**
     * Builds an {@link I} based on the specified plugin and item ID using an empty context.
     *
     * @param plugin The lower-case name of the plugin.
     * @param id     The item ID.
     * @return An {@code Optional} containing the successfully built {@link I}, or {@code Optional.empty()} if building fails or the {@link Provider} does not exist.
     */
    default Optional<I> build(@NotNull String plugin, @NotNull String id) {
        return build(plugin, id, BuildContext.empty());
    }

    /**
     * Builds an {@link I} based on the specified plugin, item ID, and {@link BuildContext}.
     *
     * @param plugin  The lower-case name of the plugin.
     * @param id      The item ID.
     * @param context The build context {@link BuildContext}.
     * @return An {@code Optional} containing the successfully built {@link I}, or {@code Optional.empty()} if building fails or the {@link Provider} does not exist.
     */
    Optional<I> build(@NotNull String plugin, @NotNull String id, @NotNull BuildContext context);

    /**
     * Retrieves the ID of a given {@link I} within the specified plugin.
     *
     * @param plugin The lower-case name of the plugin.
     * @param item   The item object {@link I}.
     * @return An {@code Optional} containing the item ID if the item belongs to the specified plugin or its Provider exists, otherwise {@code Optional.empty()}.
     */
    Optional<String> id(@NotNull String plugin, @NotNull I item);

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
     * Interface for building and configuring {@link ItemBridge} instances.
     * <p>
     * All available built-in item providers are automatically loaded upon creation.
     * Custom providers can be registered or existing ones removed through this builder.
     */
    interface Builder<I> {

        /**
         * Registers a {@link Provider} into the ItemBridge.
         *
         * @param provider The item provider to register.
         * @return The current builder instance, supporting method chaining.
         */
        Builder<I> register(@NotNull Provider<I> provider);

        /**
         * Removes a registered provider based on the plugin name.
         *
         * @param id The lower-case name of the plugin.
         * @return The removed provider, or null if it did not exist.
         */
        @Nullable Provider<I> removeById(@NotNull String id);

        /**
         * Builds and returns an immutable {@link ItemBridge} instance.
         *
         * @return The completed {@link ItemBridge} instance.
         */
        ItemBridge<I> build();
    }
}
