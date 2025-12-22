package cn.gtemc.itembridge.core;

import cn.gtemc.itembridge.api.ItemBridgeException;
import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.api.util.Pair;
import cn.gtemc.itembridge.hook.HookHelper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

final class BukkitItemBridgeImpl implements BukkitItemBridge {
    private final Map<String, Provider<ItemStack, Player>> providers;
    private final boolean immutable;

    private BukkitItemBridgeImpl(Map<String, Provider<ItemStack, Player>> providers, boolean immutable) {
        this.providers = immutable ? Collections.unmodifiableMap(providers) : providers;
        this.immutable = immutable;
    }

    @Override
    public Optional<Provider<ItemStack, Player>> provider(@NotNull String plugin) {
        return Optional.ofNullable(this.providers.get(plugin));
    }

    @Override
    public Collection<Provider<ItemStack, Player>> providers() {
        return this.providers.values();
    }

    @Override
    public Optional<ItemStack> build(@NotNull String plugin, @NotNull String id, @Nullable Player player, @NotNull BuildContext context) {
        Provider<ItemStack, Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return Optional.empty();
        }
        return provider.build(id, player, context);
    }

    @Override
    public @Nullable ItemStack buildOrNull(@NotNull String plugin, @NotNull String id, @Nullable Player player, @NotNull BuildContext context) {
        Provider<ItemStack, Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return null;
        }
        return provider.buildOrNull(id, player, context);
    }

    @Override
    public Optional<String> id(@NotNull String plugin, @NotNull ItemStack item) {
        Provider<ItemStack, Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return Optional.empty();
        }
        return provider.id(item);
    }

    @Override
    public @Nullable String idOrNull(@NotNull String plugin, @NotNull ItemStack item) {
        Provider<ItemStack, Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return null;
        }
        return provider.idOrNull(item);
    }

    @Override
    public boolean is(@NotNull String plugin, @NotNull ItemStack item) {
        Provider<ItemStack, Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return false;
        }
        return provider.is(item);
    }

    @Override
    public boolean has(@NotNull String plugin, @NotNull String id) {
        Provider<ItemStack, Player> provider = this.providers.get(plugin);
        if (provider == null) {
            return false;
        }
        return provider.has(id);
    }

    @Override
    public boolean hasProvider(@NotNull String plugin) {
        return this.providers.containsKey(plugin);
    }

    @Override
    public @Nullable ItemStack getFirstBuild(@NotNull String id, @Nullable Player player, @NotNull BuildContext context) {
        for (Provider<ItemStack, Player> provider : this.providers.values()) {
            ItemStack item = provider.buildOrNull(id, player, context);
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    @Override
    public @Nullable Pair<String, String> getFirstId(@NotNull ItemStack item) {
        for (Provider<ItemStack, Player> provider : this.providers.values()) {
            String id = provider.idOrNull(item);
            if (id != null) {
                return Pair.of(provider.plugin(), id);
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getIds(@NotNull ItemStack item) {
        Map<String, String> ids = new HashMap<>();
        for (Provider<ItemStack, Player> provider : this.providers.values()) {
            String id = provider.idOrNull(item);
            if (id == null) {
                continue;
            }
            ids.put(provider.plugin(), id);
        }
        return ids;
    }

    @Override
    public boolean immutable() {
        return this.immutable;
    }

    @Override
    public BukkitItemBridge register(@NotNull Provider<ItemStack, Player> provider) {
        if (this.providers.containsKey(provider.plugin())) {
            throw new ItemBridgeException("Item provider '" + provider.plugin() + "' already registered");
        }
        this.providers.put(provider.plugin(), provider);
        return this;
    }

    @Override
    public BukkitItemBridge removeById(@NotNull String id) {
        this.providers.remove(id);
        return this;
    }

    final static class BukkitBuilderImpl implements BukkitBuilder {
        private final Map<String, Provider<ItemStack, Player>> providers;
        private Consumer<String> onHookSuccess;
        private BiConsumer<String, Throwable> onHookFailure;
        private boolean immutable;

        BukkitBuilderImpl() {
            this.providers = new HashMap<>();
        }

        @Override
        public BukkitBuilder register(@NotNull Provider<ItemStack, Player> provider) {
            if (this.providers.containsKey(provider.plugin())) {
                throw new ItemBridgeException("Item provider '" + provider.plugin() + "' already registered");
            }
            this.providers.put(provider.plugin(), provider);
            return this;
        }

        @Override
        public BukkitBuilder removeById(@NotNull String id) {
            this.providers.remove(id);
            return this;
        }

        @Override
        public BukkitBuilder immutable(boolean immutable) {
            this.immutable = immutable;
            return this;
        }

        @Override
        public BukkitBuilder onHookSuccess(Consumer<String> onSuccess) {
            this.onHookSuccess = onSuccess;
            return this;
        }

        @Override
        public BukkitBuilder onHookFailure(BiConsumer<String, Throwable> onFailure) {
            this.onHookFailure = onFailure;
            return this;
        }

        @Override
        public Builder<ItemStack, Player> detectSupportedPlugins() {
            return detectSupportedPlugins(plugin -> true);
        }

        @Override
        public BukkitBuilder detectSupportedPlugins(@NotNull Predicate<Plugin> filter) {
            this.providers.putAll(HookHelper.getSupportedPlugins(this.onHookSuccess, this.onHookFailure, filter));
            return this;
        }

        @Override
        public BukkitItemBridge build() {
            return new BukkitItemBridgeImpl(this.providers, this.immutable);
        }
    }
}
