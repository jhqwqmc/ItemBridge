package cn.gtemc.itembridge.api.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
final class BuildContextImpl implements BuildContext {
    private final Map<ContextKey<?>, Supplier<Object>> contextData;

    BuildContextImpl(Map<ContextKey<?>, Supplier<Object>> properties) {
        this.contextData = Collections.unmodifiableMap(properties);
    }

    @Override
    public <T> Optional<T> get(ContextKey<T> key) {
        return Optional.ofNullable(this.contextData.get(key)).map(property -> (T) property.get());
    }

    @Override
    public <T> @Nullable T getOrNull(ContextKey<T> key) {
        Supplier<T> supplier = (Supplier<T>) this.contextData.get(key);
        return supplier == null ? null : supplier.get();
    }

    @Override
    public <T> @NotNull T getOrThrow(ContextKey<T> key) {
        Supplier<T> supplier = (Supplier<T>) this.contextData.get(key);
        if (supplier == null) {
            throw new NoSuchElementException(key.key());
        }
        return supplier.get();
    }

    @Override
    public <T> T getOrDefault(ContextKey<T> key, T defaultValue) {
        Supplier<T> supplier = (Supplier<T>) this.contextData.get(key);
        return supplier == null ? defaultValue : supplier.get();
    }

    final static class BuilderImpl implements Builder {
        private final Map<ContextKey<?>, Supplier<Object>> contextData = new HashMap<>();

        @Override
        public <T> Builder with(ContextKey<T> key, @NotNull T value) {
            this.contextData.put(key, new SimpleSupplier<>(value));
            return this;
        }

        @Override
        public <T> Builder with(ContextKey<T> key, Supplier<@NotNull T> value) {
            this.contextData.put(key, (Supplier<Object>) value);
            return this;
        }

        @Override
        public <T> Builder withOptional(ContextKey<T> key, @Nullable T value) {
            if (value == null) {
                this.contextData.remove(key);
            } else {
                this.contextData.put(key, new SimpleSupplier<>(value));
            }
            return this;
        }

        @Override
        public <T> Optional<T> get(ContextKey<T> key) {
            return Optional.ofNullable(this.contextData.get(key)).map(property -> (T) property.get());
        }

        @Override
        public <T> @NotNull T getOrThrow(ContextKey<T> key) {
            Supplier<T> supplier = (Supplier<T>) this.contextData.get(key);
            if (supplier == null) {
                throw new NoSuchElementException(key.key());
            }
            return supplier.get();
        }

        @Override
        public BuildContext build() {
            if (this.contextData.isEmpty()) {
                return new BuildContextImpl(Collections.emptyMap());
            }
            return new BuildContextImpl(this.contextData);
        }
    }

    record SimpleSupplier<T>(T value) implements Supplier<T> {
        SimpleSupplier(@NotNull T value) {
            this.value = Objects.requireNonNull(value);
        }

        @Override
        public T get() {
            return this.value;
        }
    }

}
