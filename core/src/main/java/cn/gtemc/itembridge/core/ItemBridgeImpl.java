package cn.gtemc.itembridge.core;

import cn.gtemc.itembridge.api.ItemBridgeException;
import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import cn.gtemc.itembridge.hook.HookHelper;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

final class ItemBridgeImpl implements ItemBridge {
    private final Map<String, Provider<ItemStack>> providers;

    private ItemBridgeImpl(Map<String, Provider<ItemStack>> providers) {
        this.providers = Collections.unmodifiableMap(providers);
    }

    @Override
    public Optional<Provider<ItemStack>> provider(@NotNull String plugin) {
        return Optional.empty();
    }

    @Override
    public Collection<Provider<ItemStack>> providers() {
        return this.providers.values();
    }

    @Override
    public Optional<ItemStack> build(@NotNull String plugin, @NotNull String id, @NotNull BuildContext context) {
        Provider<ItemStack> provider = this.providers.get(plugin);
        if (provider == null) {
            return Optional.empty();
        }
        return provider.build(id, context);
    }

    @Override
    public Optional<String> id(@NotNull String plugin, @NotNull ItemStack item) {
        Provider<ItemStack> provider = this.providers.get(plugin);
        if (provider == null) {
            return Optional.empty();
        }
        return provider.id(item);
    }

    @Override
    public boolean is(@NotNull String plugin, @NotNull ItemStack item) {
        Provider<ItemStack> provider = this.providers.get(plugin);
        if (provider == null) {
            return false;
        }
        return provider.is(item);
    }

    @Override
    public boolean has(@NotNull String plugin, @NotNull String id) {
        Provider<ItemStack> provider = this.providers.get(plugin);
        if (provider == null) {
            return false;
        }
        return provider.has(id);
    }

    @Override
    public boolean hasProvider(@NotNull String plugin) {
        return this.providers.containsKey(plugin);
    }

    final static class BuilderImpl implements Builder {
        private final Map<String, Provider<ItemStack>> providers;

        BuilderImpl() {
            this.providers = new HashMap<>();
            try {
                this.providers.putAll(HookHelper.getSupportedPlugins());
            } catch (Throwable e) {
                throw new ItemBridgeException("Failed to load builtin providers", e);
            }
        }

        @Override
        public Builder register(@NotNull Provider<ItemStack> provider) {
            if (this.providers.containsKey(provider.plugin())) {
                throw new ItemBridgeException("Item provider '" + provider.plugin() + "' already registered");
            }
            this.providers.put(provider.plugin(), provider);
            return this;
        }

        @Nullable
        public Provider<ItemStack> removeById(@NotNull String id) {
            return this.providers.remove(id);
        }

        @Override
        public ItemBridge build() {
            return new ItemBridgeImpl(this.providers);
        }
    }
}
