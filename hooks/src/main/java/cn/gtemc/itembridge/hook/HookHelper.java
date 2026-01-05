package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.util.Utils;
import cn.gtemc.itembridge.api.util.ThrowableRunnable;
import cn.gtemc.itembridge.hook.provider.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class HookHelper {
    static ProvidersGetter j21ProvidersGetter;
    static ProvidersGetter j17ProvidersGetter;
    static ProvidersGetter j11ProvidersGetter;

    static {
        try {
            Class.forName("cn.gtemc.itembridge.hook.J21HookHelper").getDeclaredMethod("init").invoke(null);
        } catch (Throwable e) {
            j21ProvidersGetter = (s, f, p) -> new HashMap<>();
        }
        try {
            Class.forName("cn.gtemc.itembridge.hook.J17HookHelper").getDeclaredMethod("init").invoke(null);
        } catch (Throwable e) {
            j17ProvidersGetter = (s, f, p) -> new HashMap<>();
        }
        try {
            Class.forName("cn.gtemc.itembridge.hook.J11HookHelper").getDeclaredMethod("init").invoke(null);
        } catch (Throwable e) {
            j11ProvidersGetter = (s, f, p) -> new HashMap<>();
        }
    }

    private HookHelper() {}

    static void tryHook(
            ThrowableRunnable runnable,
            String pluginName,
            Consumer<String> onSuccess,
            BiConsumer<String, Throwable> onFailure,
            Predicate<Plugin> filter
    ) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null || !filter.test(plugin)) {
            return;
        }
        try {
            runnable.run();
            if (onSuccess != null) {
                onSuccess.accept(pluginName);
            }
        } catch (Throwable e) {
            if (onFailure != null) {
                onFailure.accept(pluginName, e);
            }
        }
    }

    public static Map<String, Provider<ItemStack, Player>> getSupportedPlugins(
            Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure, Predicate<Plugin> filter
    ) {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>();
        providers.putAll(j17ProvidersGetter.get(onSuccess, onFailure, filter));
        providers.putAll(j21ProvidersGetter.get(onSuccess, onFailure, filter));
        tryHook(() -> Utils.addToMap(ItemsAdderProvider.INSTANCE, providers), "ItemsAdder", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(NeigeItemsProvider.INSTANCE, providers), "NeigeItems", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(SXItemProvider.INSTANCE, providers), "SX-Item", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(ZaphkielProvider.INSTANCE, providers), "Zaphkiel", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(SlimefunProvider.INSTANCE, providers), "Slimefun", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(HeadDatabaseProvider.INSTANCE, providers), "HeadDatabase", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(ExecutableItemsProvider.INSTANCE, providers), "ExecutableItems", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(AzureFlowProvider.INSTANCE, providers), "AzureFlow", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(MagicGemProvider.INSTANCE, providers), "MagicGem", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(PxRpgProvider.INSTANCE, providers), "PxRpg", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(RatzielProvider.INSTANCE, providers), "Ratziel", onSuccess, onFailure, filter);
        return providers;
    }

    @FunctionalInterface
    interface ProvidersGetter {
        Map<String, Provider<ItemStack, Player>> get(Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure, Predicate<Plugin> filter);
    }
}
