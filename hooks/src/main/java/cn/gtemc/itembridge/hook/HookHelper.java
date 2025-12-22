package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.ItemBridgeException;
import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.util.MiscUtils;
import cn.gtemc.itembridge.api.util.ThrowableRunnable;
import cn.gtemc.itembridge.hook.provider.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class HookHelper {
    static J21ProvidersGetter j21ProvidersGetter;

    static {
        if (MiscUtils.isRunningOnJava21()) {
            try {
                MethodHandles.lookup().ensureInitialized(Class.forName("cn.gtemc.itembridge.hook.J21HookHelper"));
            } catch (ReflectiveOperationException e) {
                throw new ItemBridgeException("Failed to load builtin providers", e);
            }
        } else {
            j21ProvidersGetter = (s, f, p) -> Map.of();
        }
    }

    private HookHelper() {}

    static void tryHook(
            ThrowableRunnable runnable,
            String pluginName,
            Consumer<String> onSuccess,
            BiConsumer<String, Throwable> onFailure,
            Predicate<Plugin> predicate
    ) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null || !predicate.test(plugin)) {
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
            Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure, Predicate<Plugin> predicate
    ) {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>(j21ProvidersGetter.get(onSuccess, onFailure, predicate));
        tryHook(() -> MiscUtils.addToMap(CustomFishingProvider.INSTANCE, providers), "CustomFishing", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(ItemsAdderProvider.INSTANCE, providers), "ItemsAdder", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(MMOItemsProvider.INSTANCE, providers), "MMOItems", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(NeigeItemsProvider.INSTANCE, providers), "NeigeItems", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(SXItemProvider.INSTANCE, providers), "SX-Item", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(ZaphkielProvider.INSTANCE, providers), "Zaphkiel", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(SlimefunProvider.INSTANCE, providers), "Slimefun", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(HeadDatabaseProvider.INSTANCE, providers), "HeadDatabase", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(ExecutableItemsProvider.INSTANCE, providers), "ExecutableItems", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(AzureFlowProvider.INSTANCE, providers), "AzureFlow", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(MagicGemProvider.INSTANCE, providers), "MagicGem", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(PxRpgProvider.INSTANCE, providers), "PxRpg", onSuccess, onFailure, predicate);
        tryHook(() -> MiscUtils.addToMap(RatzielProvider.INSTANCE, providers), "Ratziel", onSuccess, onFailure, predicate);
        return providers;
    }

    @FunctionalInterface
    interface J21ProvidersGetter {
        Map<String, Provider<ItemStack, Player>> get(Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure, Predicate<Plugin> predicate);
    }
}
