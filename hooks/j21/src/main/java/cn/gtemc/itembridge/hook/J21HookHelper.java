package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.util.Utils;
import cn.gtemc.itembridge.hook.provider.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static cn.gtemc.itembridge.hook.HookHelper.tryHook;

@SuppressWarnings("unused")
public final class J21HookHelper {

    static {
        HookHelper.j21ProvidersGetter = J21HookHelper::getSupportedPlugins;
    }

    private J21HookHelper() {}

    public static void init() {
    }

    private static Map<String, Provider<ItemStack, Player>> getSupportedPlugins(
            Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure, Predicate<Plugin> filter
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
        return providers;
    }
}
