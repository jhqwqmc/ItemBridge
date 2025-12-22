package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.ItemBridgeException;
import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.util.MiscUtils;
import cn.gtemc.itembridge.api.util.ThrowableRunnable;
import cn.gtemc.itembridge.hook.provider.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public final class HookHelper {
    static BiFunction<Consumer<String>, BiConsumer<String, Throwable>, Map<String, Provider<ItemStack, Player>>> j21ProvidersGetter;

    static {
        if (MiscUtils.isRunningOnJava21()) {
            try {
                MethodHandles.lookup().ensureInitialized(Class.forName("cn.gtemc.itembridge.hook.J21HookHelper"));
            } catch (ReflectiveOperationException e) {
                throw new ItemBridgeException("Failed to load builtin providers", e);
            }
        } else {
            j21ProvidersGetter = (s, f) -> Map.of();
        }
    }

    private HookHelper() {}

    static void tryHook(ThrowableRunnable runnable, String plugin, Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure) {
        if (Bukkit.getPluginManager().getPlugin(plugin) == null) {
            return;
        }
        try {
            runnable.run();
            if (onSuccess != null) {
                onSuccess.accept(plugin);
            }
        } catch (Throwable e) {
            if (onFailure != null) {
                onFailure.accept(plugin, e);
            }
        }
    }

    public static Map<String, Provider<ItemStack, Player>> getSupportedPlugins(Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure) {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>(j21ProvidersGetter.apply(onSuccess, onFailure));
        tryHook(() -> MiscUtils.addToMap(CustomFishingProvider.INSTANCE, providers), "CustomFishing", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(ItemsAdderProvider.INSTANCE, providers), "ItemsAdder", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(MMOItemsProvider.INSTANCE, providers), "MMOItems", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(NeigeItemsProvider.INSTANCE, providers), "NeigeItems", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(SXItemProvider.INSTANCE, providers), "SX-Item", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(ZaphkielProvider.INSTANCE, providers), "Zaphkiel", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(SlimefunProvider.INSTANCE, providers), "Slimefun", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(HeadDatabaseProvider.INSTANCE, providers), "HeadDatabase", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(ExecutableItemsProvider.INSTANCE, providers), "ExecutableItems", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(AzureFlowProvider.INSTANCE, providers), "AzureFlow", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(MagicGemProvider.INSTANCE, providers), "MagicGem", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(PxRpgProvider.INSTANCE, providers), "PxRpg", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(RatzielProvider.INSTANCE, providers), "Ratziel", onSuccess, onFailure);
        return providers;
    }
}
