package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.ItemBridgeException;
import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.util.MiscUtils;
import cn.gtemc.itembridge.api.util.ThrowableRunnable;
import cn.gtemc.itembridge.hook.provider.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class HookHelper {
    private static final Logger log = LoggerFactory.getLogger(HookHelper.class);
    static Function<Boolean, Map<String, Provider<ItemStack, Player>>> j21ProvidersGetter;

    static {
        if (MiscUtils.isRunningOnJava21()) {
            try {
                MethodHandles.lookup().ensureInitialized(Class.forName("cn.gtemc.itembridge.hook.J21HookHelper"));
            } catch (ReflectiveOperationException e) {
                throw new ItemBridgeException("Failed to load builtin providers", e);
            }
        } else {
            j21ProvidersGetter = l -> Map.of();
        }
    }

    private HookHelper() {}

    static void tryHook(ThrowableRunnable runnable, String plugin, boolean loggingEnabled) {
        if (Bukkit.getPluginManager().getPlugin(plugin) == null) {
            return;
        }
        try {
            runnable.run();
            if (loggingEnabled) {
                log.info("[ItemBridge] {} hooked", plugin);
            }
        } catch (Throwable e) {
            if (loggingEnabled) {
                log.warn("[ItemBridge] Failed to hook {}", plugin, e);
            }
        }
    }

    public static Map<String, Provider<ItemStack, Player>> getSupportedPlugins(boolean loggingEnabled) {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>(j21ProvidersGetter.apply(loggingEnabled));
        tryHook(() -> MiscUtils.addToMap(CustomFishingProvider.INSTANCE, providers), "CustomFishing", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(ItemsAdderProvider.INSTANCE, providers), "ItemsAdder", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(MMOItemsProvider.INSTANCE, providers), "MMOItems", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(NeigeItemsProvider.INSTANCE, providers), "NeigeItems", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(SXItemProvider.INSTANCE, providers), "SX-Item", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(ZaphkielProvider.INSTANCE, providers), "Zaphkiel", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(SlimefunProvider.INSTANCE, providers), "Slimefun", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(HeadDatabaseProvider.INSTANCE, providers), "HeadDatabase", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(ExecutableItemsProvider.INSTANCE, providers), "ExecutableItems", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(AzureFlowProvider.INSTANCE, providers), "AzureFlow", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(MagicGemProvider.INSTANCE, providers), "MagicGem", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(PxRpgProvider.INSTANCE, providers), "PxRpg", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(RatzielProvider.INSTANCE, providers), "Ratziel", loggingEnabled);
        return providers;
    }
}
