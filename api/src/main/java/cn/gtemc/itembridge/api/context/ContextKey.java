package cn.gtemc.itembridge.api.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a key within the context, used to uniquely identify and retrieve values of a specific type.
 * <p>
 * This class is immutable and guarantees uniqueness based on the type and the key string.
 *
 * @param <T> The type of the value associated with the key.
 */
public final class ContextKey<T> {
    private final Class<T> type;
    private final String key;
    @Nullable
    private Integer hashCode;

    private ContextKey(@NotNull Class<T> type, @NotNull String key) {
        this.type = Objects.requireNonNull(type, "type");
        this.key = Objects.requireNonNull(key, "key");
    }

    /**
     * Static factory method for creating a ContextKey instance.
     *
     * @param type The {@code Class} object of the value associated with the key.
     * @param key  The string identifier of the key.
     * @return A new ContextKey instance.
     * @param <T> The type of the value associated with the key.
     */
    public static <T> ContextKey<T> of(@NotNull Class<T> type, @NotNull String key) {
        return new ContextKey<>(type, key);
    }

    /**
     * Retrieves the type of the value associated with the key.
     *
     * @return The {@code Class} object of the value associated with the key.
     */
    public Class<T> type() {
        return this.type;
    }

    /**
     * Retrieves the string identifier of the key.
     *
     * @return The string identifier of the key.
     */
    public String key() {
        return this.key;
    }

    @Override
    public String toString() {
        return "ContextKey{" +
                "type=" + this.type +
                ", key=" + this.key +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof ContextKey<?> contextKey
                && this.type.equals(contextKey.type)
                && this.key.equals(contextKey.key);
    }

    @Override
    public int hashCode() {
        if (this.hashCode == null) {
            this.hashCode = this.generateHashCode();
        }
        return this.hashCode;
    }

    private int generateHashCode() {
        return 31 * this.type.hashCode() + this.key.hashCode();
    }
}
