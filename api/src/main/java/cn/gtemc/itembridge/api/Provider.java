package cn.gtemc.itembridge.api;

import cn.gtemc.itembridge.api.context.BuildContext;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Item Provider Interface.
 * <p>
 * Defines how to retrieve, identify, and build items from specific plugins.
 * Each implementation class typically corresponds to a specific external plugin.
 *
 * @param <I> The type of the target item object.
 */
public interface Provider<I> {

    /**
     * Retrieves the lower-case name of the plugin corresponding to this provider.
     *
     * @return The lower-case plugin name.
     */
    String plugin();

    /**
     * Builds an item based on the item ID using an empty context.
     *
     * @param id The item ID.
     * @return An {@code Optional} containing the built item object, or {@code Optional.empty()} if the ID is invalid or building fails.
     */
    default Optional<I> build(@NotNull String id) {
        return build(id, BuildContext.empty());
    }

    /**
     * Builds an item based on the item ID and build context.
     * <p>
     * The context may contain additional build parameters, such as a player object.
     *
     * @param id      The item ID.
     * @param context The build context {@link BuildContext}.
     * @return An {@code Optional} containing the built item object, or {@code Optional.empty()} if the ID is invalid or building fails.
     */
    Optional<I> build(String id, @NotNull BuildContext context);

    /**
     * Resolves the corresponding ID from the item object.
     *
     * @param item The item object.
     * @return An {@code Optional} containing the ID if the item belongs to this provider, otherwise {@code Optional.empty()}.
     */
    Optional<String> id(@NotNull I item);

    /**
     * Determines whether the specified item object belongs to this provider.
     *
     * @param item The item object.
     * @return true if the item is managed by this provider's plugin.
     */
    boolean is(@NotNull I item);

    /**
     * Checks if the specified item ID exists within this provider's plugin.
     *
     * @param id The item ID.
     * @return true if the ID exists.
     */
    boolean has(@NotNull String id);
}
