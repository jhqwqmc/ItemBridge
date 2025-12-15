package cn.gtemc.itembridge.api.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public interface BuildContext {

    static Builder builder() {
        return new BuildContextImpl.BuilderImpl();
    }

    static BuildContext empty() {
        return BuildContext.builder().build();
    }

    <T> Optional<T> get(ContextKey<T> key);

    <T> @Nullable T getOrNull(ContextKey<T> key);

    <T> T getOrThrow(ContextKey<T> key);

    <T> T getOrDefault(ContextKey<T> key, T defaultValue);

    interface Builder {

        <T> Builder with(ContextKey<T> key, @NotNull T value);

        <T> Builder with(ContextKey<T> key, Supplier<@NotNull T> value);

        <T> Builder withOptional(ContextKey<T> key, @Nullable T value);

        <T> Optional<T> get(ContextKey<T> key);

        <T> T getOrThrow(ContextKey<T> key);

        BuildContext build();
    }
}
