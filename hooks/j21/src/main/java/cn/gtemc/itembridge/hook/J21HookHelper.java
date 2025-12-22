package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.util.MiscUtils;
import cn.gtemc.itembridge.hook.provider.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static cn.gtemc.itembridge.hook.HookHelper.tryHook;

@SuppressWarnings("unused")
public final class J21HookHelper {

    static {
        HookHelper.j21ProvidersGetter = J21HookHelper::getSupportedPlugins;
    }

    private J21HookHelper() {}

    private static Map<String, Provider<ItemStack, Player>> getSupportedPlugins(Consumer<String> onSuccess, BiConsumer<String, Throwable> onFailure) {
        Map<String, Provider<ItemStack, Player>> providers = new HashMap<>();
        tryHook(() -> MiscUtils.addToMap(CraftEngineProvider.INSTANCE, providers), "CraftEngine", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(NexoProvider.INSTANCE, providers), "Nexo", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(OraxenProvider.INSTANCE, providers), "Oraxen", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(NovaProvider.INSTANCE, providers), "Nova", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(MythicMobsProvider.INSTANCE, providers), "MythicMobs", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecoarmor"), providers), "EcoArmor", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecocrates"), providers), "EcoCrates", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecoitems"), providers), "EcoItems", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecomobs"), providers), "EcoMobs", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecopets"), providers), "EcoPets", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("ecoscrolls"), providers), "EcoScrolls", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("reforges"), providers), "Reforges", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("stattrackers"), providers), "StatTrackers", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(new EcoProvider("talismans"), providers), "Talismans", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(HMCCosmeticsProvider.INSTANCE, providers), "HMCCosmetics", onSuccess, onFailure);
        tryHook(() -> MiscUtils.addToMap(SertralineProvider.INSTANCE, providers), "Sertraline", onSuccess, onFailure);
        return providers;
    }
}
