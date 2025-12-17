package cn.gtemc.itembridge.api.context;

import cn.gtemc.itembridge.api.Provider;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Item Build Context Interface.
 * <p>
 * Used to pass additional, optional, or mutable parameters during the item building process ({@link Provider#build(String, BuildContext)}).
 * <p>
 * Uses a Key-Value pattern to store data, where the Key is a type-safe {@link ContextKey}, ensuring data type safety.
 */
public interface BuildContext {

    /**
     * Creates a new context builder.
     *
     * @return A context builder {@link Builder} instance.
     */
    static Builder builder() {
        return new BuildContextImpl.BuilderImpl();
    }

    /**
     * Creates an empty build context instance containing no data.
     *
     * @return An empty {@code BuildContext} instance.
     */
    static BuildContext empty() {
        return BuildContext.builder().build();
    }

    /**
     * Retrieves the value corresponding to the specified Key, returning an {@code Optional} wrapped result.
     * <p>
     * This is the standard way to retrieve context data.
     *
     * @param key The unique key for the target data {@link ContextKey}.
     * @return An {@code Optional} containing the data, or {@code Optional.empty()} if the Key does not exist or the value is null.
     * @param <T> The type of the data.
     */
    <T> Optional<T> get(ContextKey<T> key);

    /**
     * Retrieves the value corresponding to the specified Key, returning null if the Key does not exist or the value is null.
     *
     * @param key The unique key for the target data {@link ContextKey}.
     * @return The corresponding value, or null if it does not exist.
     * @param <T> The type of the data.
     */
    <T> @Nullable T getOrNull(ContextKey<T> key);

    /**
     * Retrieves the value corresponding to the specified Key, throwing an exception if the Key does not exist or the value is null.
     * <p>
     * Suitable for scenarios where the Key must exist in the context.
     *
     * @param key The unique key for the target data {@link ContextKey}.
     * @return The corresponding value, guaranteed to be non-null.
     * @throws java.util.NoSuchElementException If the context does not contain a value for the Key.
     * @param <T> The type of the data.
     */
    <T> @NotNull T getOrThrow(ContextKey<T> key);

    /**
     * Retrieves the value corresponding to the specified Key, returning the provided default value if the Key does not exist or the value is null.
     *
     * @param key          The unique key for the target data {@link ContextKey}.
     * @param defaultValue The default value to return if the Key does not exist.
     * @return The corresponding value, or the default value.
     * @param <T> The type of the data.
     */
    <T> @Nullable T getOrDefault(ContextKey<T> key, T defaultValue);

    @ApiStatus.Internal
    Map<ContextKey<?>, Supplier<Object>> contextData();

    /**
     * Builder Interface.
     * <p>
     * Used to add Key-Value data pairs to the {@link BuildContext} and finally build an immutable {@link BuildContext} instance.
     */
    interface Builder {

        /**
         * Adds a non-null Key-Value pair to the builder.
         *
         * @param key   The data key.
         * @param value The non-null data value.
         * @return The current builder instance, supporting method chaining.
         * @param <T> The type of the data.
         */
        <T> Builder with(ContextKey<T> key, @NotNull T value);

        /**
         * Adds a non-null Key-Value pair to the builder, where the value is lazily provided by a {@link Supplier}.
         * <p>
         * This allows values to be calculated or retrieved only when needed.
         *
         * @param key   The data key.
         * @param value A {@link Supplier} that provides a non-null value when required.
         * @return The current builder instance, supporting method chaining.
         * @param <T> The type of the data.
         */
        <T> Builder with(ContextKey<T> key, Supplier<@NotNull T> value);

        /**
         * Attempts to add a potentially null value to the builder.
         * <p>
         * If {@code value} is {@code null}, it attempts to remove the key from the builder.
         * <p>
         * If {@code value} is non-null, the Key-Value pair is added.
         *
         * @param key   The data key.
         * @param value The potentially null data value.
         * @return The current builder instance, supporting method chaining.
         * @param <T> The type of the data.
         */
        <T> Builder withOptional(ContextKey<T> key, @Nullable T value);

        /**
         * Retrieves a value from the data currently added to the builder.
         * <p>
         * This method is called before {@code build()}, allowing checks on added values during the construction process.
         *
         * @param key The unique key for the target data {@link ContextKey}.
         * @return An {@code Optional} containing the data, or {@code Optional.empty()} if the Key does not exist or the value is null.
         * @param <T> The type of the data.
         */
        <T> Optional<T> get(ContextKey<T> key);

        /**
         * Retrieves a value from the data currently added to the builder, throwing an exception if the Key does not exist or the value is null.
         *
         * @param key The unique key for the target data {@link ContextKey}.
         * @return The corresponding value, guaranteed to be non-null.
         * @throws java.util.NoSuchElementException If the builder does not contain a value for the Key.
         * @param <T> The type of the data.
         */
        <T> @NotNull T getOrThrow(ContextKey<T> key);

        /**
         * Creates and returns an immutable {@link BuildContext} instance based on the data currently configured in the builder.
         *
         * @return The newly created {@code BuildContext} instance.
         */
        BuildContext build();
    }
}
