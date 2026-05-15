package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class HookHelper {
    private HookHelper() {}

    public static Map<String, Provider<ItemStack, Player>> getSupportedPlugins(
            @Nullable Consumer<String> onSuccess,
            @Nullable BiConsumer<String, Throwable> onFailure,
            @Nullable Predicate<Plugin> filter
    ) {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>();
        tryHook(() -> Utils.addToMap(CraftEngineProvider.INSTANCE, providers), "CraftEngine", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(NexoProvider.INSTANCE, providers), "Nexo", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(OraxenProvider.INSTANCE, providers), "Oraxen", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(NovaProvider.INSTANCE, providers), "Nova", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(MythicMobsProvider.INSTANCE, providers), "MythicMobs", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecoarmor"), providers), "EcoArmor", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecocrates"), providers), "EcoCrates", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecoitems"), providers), "EcoItems", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecomobs"), providers), "EcoMobs", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecopets"), providers), "EcoPets", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("ecoscrolls"), providers), "EcoScrolls", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("reforges"), providers), "Reforges", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("stattrackers"), providers), "StatTrackers", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(new EcoProvider("talismans"), providers), "Talismans", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(HMCCosmeticsProvider.INSTANCE, providers), "HMCCosmetics", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(SertralineProvider.INSTANCE, providers), "Sertraline", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(MMOItemsProvider.INSTANCE, providers), "MMOItems", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(CustomFishingProvider.INSTANCE, providers), "CustomFishing", onSuccess, onFailure, filter);
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
        tryHook(() -> Utils.addToMap(BaikirutoProvider.INSTANCE, providers), "Baikiruto", onSuccess, onFailure, filter);
        tryHook(() -> Utils.addToMap(DragonArmourersProvider.INSTANCE, providers), "DragonArmourers", onSuccess, onFailure, filter);
        return providers;
    }

    private static void tryHook(
            ThrowableRunnable runnable,
            String pluginName,
            @Nullable Consumer<String> onSuccess,
            @Nullable BiConsumer<String, Throwable> onFailure,
            @Nullable Predicate<Plugin> filter
    ) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null || (filter != null && !filter.test(plugin))) {
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

    @FunctionalInterface
    private interface ThrowableRunnable {

        void run() throws Throwable;
    }
}
