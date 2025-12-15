package cn.gtemc.itembridge.api.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ContextKey<T> {
    private final Class<T> type;
    private final String key;
    @Nullable
    private Integer hashCode;

    private ContextKey(@NotNull Class<T> type, @NotNull String key) {
        this.type = Objects.requireNonNull(type, "type");
        this.key = Objects.requireNonNull(key, "key");
    }

    public static <T> ContextKey<T> of(@NotNull Class<T> type, @NotNull String key) {
        return new ContextKey<>(type, key);
    }

    public Class<T> type() {
        return this.type;
    }

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
