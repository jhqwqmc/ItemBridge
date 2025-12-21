package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.util.MiscUtils;
import cn.gtemc.itembridge.hook.provider.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static cn.gtemc.itembridge.hook.HookHelper.tryHook;

@SuppressWarnings("unused")
public final class J21HookHelper {

    static {
        HookHelper.j21ProvidersGetter = J21HookHelper::getSupportedPlugins;
    }

    private J21HookHelper() {}

    private static Map<String, Provider<ItemStack, Player>> getSupportedPlugins(boolean loggingEnabled) {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>();
        tryHook(() -> MiscUtils.addToMap(CraftEngineProvider.INSTANCE, providers), "CraftEngine", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(NexoProvider.INSTANCE, providers), "Nexo", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(OraxenProvider.INSTANCE, providers), "Oraxen", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(NovaProvider.INSTANCE, providers), "Nova", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(MythicMobsProvider.INSTANCE, providers), "MythicMobs", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecoarmor"), providers), "EcoArmor", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecocrates"), providers), "EcoCrates", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecoitems"), providers), "EcoItems", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecomobs"), providers), "EcoMobs", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecopets"), providers), "EcoPets", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecoscrolls"), providers), "EcoScrolls", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("reforges"), providers), "Reforges", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("stattrackers"), providers), "StatTrackers", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("talismans"), providers), "Talismans", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(HMCCosmeticsProvider.INSTANCE, providers), "HMCCosmetics", loggingEnabled);
        tryHook(() -> MiscUtils.addToMap(SertralineProvider.INSTANCE, providers), "Sertraline", loggingEnabled);
        return providers;
    }
}
